package tests;

import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 4/14/2017.
 */
public class GOLADGameParams
{
	public static final String NO_ID = "-231";
	public static final int GEN_ROT_SYM = 0;
	public static final int GEN_FROM_LIST = 1;

	private List<PlayerStruct> playerList = new ArrayList<>();
	private int gridGenerationMode = GEN_ROT_SYM;
	private String gameId = NO_ID;
	private int height = 0;
	private int width = 0;

	private CellStruct[][] gridLayout;

	public void addPlayer(String name, Vector3f col, int biomass)
	{
		PlayerStruct ps = new PlayerStruct();
		ps.color = new Vector3f(col);
		ps.biomass = biomass;
		ps.name = name;
		playerList.add(ps);
	}

	public void setGridGenerationMode(int gridGenerationMode)
	{
		this.gridGenerationMode = gridGenerationMode;
	}

	public List<PlayerStruct> getPlayerList()
	{
		return playerList;
	}

	public void setPlayerList(List<PlayerStruct> playerList)
	{
		this.playerList = playerList;
	}

	public int getGridGenerationMode()
	{
		return gridGenerationMode;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void initGridLayout(int w, int h)
	{
		this.gridLayout = new CellStruct[w][h];
	}

	public void setCellInGridLayout(int i, int j, int ownerHash, boolean alive)
	{
		CellStruct cs = new CellStruct();
		cs.ownerHash = ownerHash;
		cs.alive = alive;
		gridLayout[i][j] = cs;
	}

	public CellStruct[][] getGridLayout()
	{
		return gridLayout;
	}

	public void setGameId(String gameId)
	{
		this.gameId = gameId;
	}

	public String getGameId()
	{
		return gameId;
	}

	public class PlayerStruct
	{
		public String name;
		public Vector3f color;
		public int biomass;
	}

	public class CellStruct
	{
		public int ownerHash;
		public boolean alive;
	}
}
