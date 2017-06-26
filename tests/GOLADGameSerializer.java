package tests;

import serialization.SerialArray;
import serialization.SerialDatabase;
import serialization.SerialField;
import serialization.SerialObject;
import tests.states.GOLADSavedGame;
import utils.math.linear.vector.Vector3f;

import java.util.List;
import java.util.Objects;

/**
 * Created by mjmcc on 4/21/2017.
 */
public class GOLADGameSerializer
{
	private static final String META_STR = "GOLAD_META";
	private static final String GAME_ID = "GAME_ID";
	private static final String PLAYERS_AMT = "PLAYERS_AMT";
	private static final String GRID_WIDTH = "G_W";
	private static final String GRID_HEIGHT = "G_H";

	private static final String PLAYER_STR = "PLAYERS";
	private static final String PLAYER_BIOMASS = "P_BM";
	private static final String PLAYER_NAME = "P_NAME";
	private static final String PLAYER_COL_R = "P_COL_R";
	private static final String PLAYER_COL_G = "P_COL_G";
	private static final String PLAYER_COL_B = "P_COL_B";
	private static final String PLAYER_EL = "P_EL";

	private static final String CELL_GRID = "CELL_GRID";
	private static final String CELL_ARRAY_OWN = "CELL_OWN_ARRAY";
	private static final String CELL_ARRAY_ALIVE = "CELL_AL_ARRAY";

	public SerialDatabase genDataBase(GOLADPlayInstance game)
	{
		SerialDatabase database = new SerialDatabase(game.getGameId());

		// Game meta data
		SerialObject gameMeta = new SerialObject(META_STR);
		gameMeta.addArray(SerialArray.String(GAME_ID, game.getGameId()));
		gameMeta.addField(SerialField.Integer(GRID_WIDTH, game.getGrid().getWidth()));
		gameMeta.addField(SerialField.Integer(GRID_HEIGHT, game.getGrid().getHeight()));
		gameMeta.addField(SerialField.Integer(PLAYERS_AMT, game.getPlayers().size()));
		database.addObject(gameMeta);

		// Players data
		List<Player> players = game.getPlayers();

		for (int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			SerialObject playerSerial = new SerialObject(PLAYER_STR + "_" + i);
			playerSerial.addArray(SerialArray.String(PLAYER_NAME, player.getName()));
			playerSerial.addField(SerialField.Integer(PLAYER_BIOMASS, player.getBiomass()));
			playerSerial.addField(SerialField.Float(PLAYER_COL_R, player.getColor().x()));
			playerSerial.addField(SerialField.Float(PLAYER_COL_G, player.getColor().y()));
			playerSerial.addField(SerialField.Float(PLAYER_COL_B, player.getColor().z()));
			playerSerial.addField(SerialField.Boolean(PLAYER_EL, player.isEliminated()));
			database.addObject(playerSerial);
		}

		// Cell Grid
		CellGrid cellGrid = game.getGrid();
		Cell[][] cells = cellGrid.getCells();

		SerialObject cellGridSerial = new SerialObject(CELL_GRID);
		int[] oArray = new int[cellGrid.getWidth() * cellGrid.getHeight()];
		boolean[] aArray = new boolean[cellGrid.getWidth() * cellGrid.getHeight()];

		for (int i = 0; i < cellGrid.getWidth(); i++)
		{
			for (int j = 0; j < cellGrid.getHeight(); j++)
			{
				int index = j * cellGrid.getWidth() + i;
				Player owner = cells[i][j].getOwner();
				Vector3f ownerCol = owner.getColor();
				oArray[index] = Objects.hash(owner.getName(), ownerCol.x(), ownerCol.y(), ownerCol.z());
				aArray[index] = cells[i][j].isAlive();
			}
		}

		cellGridSerial.addArray(SerialArray.Integer(CELL_ARRAY_OWN, oArray));
		cellGridSerial.addArray(SerialArray.Boolean(CELL_ARRAY_ALIVE, aArray));
		database.addObject(cellGridSerial);

		return database;
	}

	public GOLADGameParams genGameParams(SerialDatabase database)
	{
		GOLADGameParams gameParams = new GOLADGameParams();
		SerialObject metaObj = database.getObject(META_STR);

		String gameId = metaObj.getArray(GAME_ID).asString();
		int playersSize = metaObj.getField(PLAYERS_AMT).asInteger();

		for (int i = 0; i < playersSize; i++)
		{
			SerialObject playerSerial = database.getObject(PLAYER_STR + "_" + i);
			String name = playerSerial.getArray(PLAYER_NAME).asString();
			int biomass = playerSerial.getField(PLAYER_BIOMASS).asInteger();
			float r = playerSerial.getField(PLAYER_COL_R).asFloat();
			float g = playerSerial.getField(PLAYER_COL_G).asFloat();
			float b = playerSerial.getField(PLAYER_COL_B).asFloat();
			gameParams.addPlayer(name, new Vector3f(r, g, b), biomass);
		}

		int width = metaObj.getField(GRID_WIDTH).asInteger();
		int height = metaObj.getField(GRID_HEIGHT).asInteger();
		gameParams.setGridGenerationMode(GOLADGameParams.GEN_FROM_LIST);
		gameParams.setWidth(width);
		gameParams.setHeight(height);
		gameParams.setGameId(gameId);

		SerialObject gridObj = database.getObject(CELL_GRID);
		int[] nameHashArray = gridObj.getArray(CELL_ARRAY_OWN).asInteger();
		boolean[] aliveArray = gridObj.getArray(CELL_ARRAY_ALIVE).asBoolean();

		gameParams.initGridLayout(width, height);

		for (int i = 0; i < (width * height); i++)
		{
			boolean alive = aliveArray[i];
			int ownerHash = nameHashArray[i];
			gameParams.setCellInGridLayout(i % width, i / width, ownerHash, alive);
		}

		return gameParams;
	}

	public GOLADSavedGame writeToFile(GOLADPlayInstance goladGame, GOLADGame vars, String gamePath)
	{
		int gameIdHash = Objects.hash(goladGame.getGameId());
		GOLADSavedGame savedGame = new GOLADSavedGame(gameIdHash, goladGame.getGameId(), gamePath);

		SerialDatabase db = genDataBase(goladGame);
		SerialDatabase.toFile(gamePath, db);

		return savedGame;
	}

	public GOLADGameParams readFromFile(String filePath)
	{
		SerialDatabase dataBase = SerialDatabase.fromFile(filePath, new SerialDatabase(filePath));
		return genGameParams(dataBase);
	}

}
