package tests;

/**
 * Created by mjmcc on 2/14/2017.
 */
public class CellEnvironmentData
{
	private Player majPlayer;
	private int cellCount;

	public void setMajPlayer(Player p)
	{
		this.majPlayer = p;
	}

	public Player getMajPlayer()
	{
		return majPlayer;
	}

	public int getCellCount()
	{
		return cellCount;
	}

	public void setCellCount(int cellCount)
	{
		this.cellCount = cellCount;
	}

}
