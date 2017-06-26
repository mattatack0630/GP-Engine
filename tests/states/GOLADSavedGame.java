package tests.states;

/**
 * Created by mjmcc on 4/24/2017.
 */
public class GOLADSavedGame
{
	private int gameId;
	private String gameName;
	private String savePath;

	public GOLADSavedGame(int gameId, String gameName, String savePath)
	{
		this.gameId = gameId;
		this.gameName = gameName;
		this.savePath = savePath;
	}

	public int getGameId()
	{
		return gameId;
	}

	public void setGameId(int gameId)
	{
		this.gameId = gameId;
	}

	public String getGameName()
	{
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}

	public String getSavePath()
	{
		return savePath;
	}

	public void setSavePath(String savePath)
	{
		this.savePath = savePath;
	}
}
