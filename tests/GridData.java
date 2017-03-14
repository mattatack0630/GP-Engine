package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 3/1/2017.
 */
public class GridData
{
	private Map<Cell, CellEnvironmentData> cellEnvironmentMap;
	private Map<Player, List<Cell>> playerOwnershipMap;
	private Player globMajorityPlayer;

	public GridData()
	{
		this.cellEnvironmentMap = new HashMap<>();
		this.playerOwnershipMap = new HashMap<>();
		this.globMajorityPlayer = Player.NO_PLAYER;
	}

	public void surveyGrid(CellGrid grid)
	{
		globMajorityPlayer = Player.NO_PLAYER;
		cellEnvironmentMap.clear();
		playerOwnershipMap.clear();

		for (int i = 0; i < grid.getWidth(); i++)
		{
			for (int j = 0; j < grid.getHeight(); j++)
			{
				Cell cell = grid.getCell(i, j);
				Player owner = cell.getOwner();
				CellEnvironmentData cellData = getCellData(grid, i, j);

				List<Cell> playerCells = playerOwnershipMap.containsKey(owner) ? playerOwnershipMap.get(owner) : new ArrayList<>();
				playerCells.add(cell);

				cellEnvironmentMap.put(cell, cellData);
				playerOwnershipMap.put(owner, playerCells);

				if (globMajorityPlayer == Player.NO_PLAYER || playerCells.size() > playerOwnershipMap.get(globMajorityPlayer).size())
					if (owner != Player.NO_PLAYER)
						globMajorityPlayer = owner;
			}
		}
	}

	private CellEnvironmentData getCellData(CellGrid grid, int x, int y)
	{
		Map<Player, Integer> players = new HashMap<>();
		Player locMajorityPlayer = null;
		int aliveCount = 0;
		int majAmount = 0;

		for (int i = x - 1; i <= x + 1; i++)
		{
			for (int j = y - 1; j <= y + 1; j++)
			{
				if (!(i == x && j == y) && (i >= 0 && j >= 0) && (i < grid.getWidth() && j < grid.getHeight()))
				{
					Cell c = grid.getCell(i, j);
					if (c.isAlive())
					{
						aliveCount++;
						Player owner = c.getOwner();
						int amount = players.containsKey(owner) ? players.get(owner) + 1 : 1;
						if (amount > majAmount)
						{
							majAmount = amount;
							locMajorityPlayer = owner;
						}

						players.put(owner, amount);
					}
				}
			}
		}

		CellEnvironmentData data = new CellEnvironmentData();
		data.setCellCount(aliveCount);
		data.setMajPlayer(locMajorityPlayer);

		return data;
	}

	public Map<Cell, CellEnvironmentData> getCellEnvironmentMap()
	{
		return cellEnvironmentMap;
	}

	public Map<Player, List<Cell>> getPlayerOwnershipMap()
	{
		return playerOwnershipMap;
	}

	public Player getMajorityPlayer()
	{
		return globMajorityPlayer;
	}
}
