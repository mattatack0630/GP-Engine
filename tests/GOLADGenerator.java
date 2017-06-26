package tests;

import engine.Engine;
import serialization.SerialDatabase;
import utils.math.linear.vector.Vector3f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by mjmcc on 4/14/2017.
 */
public class GOLADGenerator
{
	public static GOLADPlayInstance buildGame(GOLADGame vars, GOLADGameParams gameParams)
	{
		GameGui gameGui = new GameGui();
		CellGrid cellGrid = new CellGrid(gameParams.getWidth(), gameParams.getHeight());
		InputEvaluator inEvaluator = new InputEvaluator(Engine.getInputManager(), cellGrid, gameGui);
		GOLADGameSerializer serializer = new GOLADGameSerializer();
		GameRenderer gameRenderer = new GameRenderer();
		List<Player> players = new ArrayList<>();

		cellGrid.initGrid();

		for (GOLADGameParams.PlayerStruct ps : gameParams.getPlayerList())
		{
			Player p = new UserPlayer("", new Vector3f(), inEvaluator);
			p.setBiomass(ps.biomass);
			p.setColor(ps.color);
			p.setName(ps.name);
			players.add(p);
		}

		gameGui.generateGui(players, vars);

		if (gameParams.getGridGenerationMode() == GOLADGameParams.GEN_ROT_SYM)
		{
			GridGenerator.populateRotGrid(cellGrid, players, 0.3f, 1);
		}

		if (gameParams.getGridGenerationMode() == GOLADGameParams.GEN_FROM_LIST)
		{
			GOLADGameParams.CellStruct[][] gridLayout = gameParams.getGridLayout();

			Map<Integer, Player> playerMap = new HashMap<>();

			for (Player player : players)
			{
				Vector3f pCol = player.getColor();
				int hash = Objects.hash(player.getName(), pCol.x(), pCol.y(), pCol.z());
				playerMap.put(hash, player);
			}

			for (int i = 0; i < gameParams.getWidth(); i++)
			{
				for (int j = 0; j < gameParams.getWidth(); j++)
				{
					GOLADGameParams.CellStruct cellStruct = gridLayout[i][j];

					Player owner = playerMap.get(cellStruct.ownerHash);
					if (owner == null) owner = Player.NO_PLAYER;

					cellGrid.setCellState(i, j, cellStruct.alive, owner, owner.getColor());
				}
			}
		}

		GOLADPlayInstance game = new GOLADPlayInstance(vars, inEvaluator, gameRenderer, serializer, gameGui, cellGrid, players);

		if(gameParams.getGameId().compareTo(GOLADGameParams.NO_ID) != 0)
			game.setGameId(gameParams.getGameId());

		return game;
	}

	public static GOLADPlayInstance buildGame(GOLADGame vars, String gameLoc)
	{
		SerialDatabase database = new SerialDatabase(gameLoc);
		ByteBuffer byteBuffer = null;

		try
		{
			byte[] byteArray = Files.readAllBytes(Paths.get(gameLoc));
			byteBuffer = ByteBuffer.wrap(byteArray);
			byteBuffer.position(byteBuffer.limit());
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		database.deserialize(byteBuffer);
		GOLADGameSerializer serializer = new GOLADGameSerializer();
		GOLADGameParams params = serializer.genGameParams(database);
		GOLADPlayInstance game = buildGame(vars, params);

		return game;
	}
}
