package tests;

/**
 * Created by mjmcc on 3/3/2017.
 */
public class ConwayRules
{
	public static final int BORN = 0;
	public static final int LIVED = 1;
	public static final int DIED = 2;

	public static int resolveConwayRuling(Cell cell, CellEnvironmentData cellData)
	{
		int cellCount = cellData.getCellCount();

		// Death
		if (cell.isAlive() && (cellCount > 3 || cellCount < 2))
			return DIED;
		// Birth
		if (!cell.isAlive() && cellCount == 3)
			return BORN;

		return LIVED;
	}
}
