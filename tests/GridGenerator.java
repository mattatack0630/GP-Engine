package tests;

import utils.math.Maths;

import java.util.List;

/**
 * Created by mjmcc on 3/1/2017.
 */
public class GridGenerator
{
	public static void populateGrid(CellGrid grid, List<Player> players, float pop, int seed)
	{
		for (int i = 0; i < grid.getWidth(); i++)
		{
			for (int j = 0; j < grid.getHeight(); j++)
			{
				boolean alive = Maths.random(0.0f, 1.0f) > 1.0f - pop;
				int playerIndex = Maths.randomi(0, players.size());
				Player player = players.get(playerIndex);
				grid.setCellState(i, j, alive, alive ? player : Player.NO_PLAYER, alive ? player.getColor() : Cell.DEAD_COLOR);
			}
		}
	}

	public static void populateArmyGrid(CellGrid grid, List<Player> players)
	{
		if (players.size() == 2)
		{
			for (int i = 0; i < grid.getWidth(); i++)
			{
				for (int j = 0; j < grid.getHeight(); j++)
				{
					float f = Math.abs((float) i - (grid.getWidth() / 2.0f)) / ((float) grid.getWidth() / 2.0f);
					boolean alive = Maths.random(0.0f, 1.0f) > 1.0f - f;
					Player player = players.get((int) (i / (grid.getWidth() / 2.0f)));
					grid.setCellState(i, j, alive, alive ? player : Player.NO_PLAYER, alive ? player.getColor() : Cell.DEAD_COLOR);
				}
			}
		}
	}

	public static void populateRotGrid(CellGrid grid, List<Player> players, float pop, int seed)
	{
		if (players.size() == 2)
		{
			for (int i = 0; i < grid.getWidth(); i++)
			{
				for (int j = 0; j < grid.getHeight() / 2; j++)
				{
					boolean alive = Maths.random(0.0f, 1.0f) > 1.0f - pop;
					int playerIndex = Maths.randomi(0, players.size());
					Player player0 = players.get(playerIndex);
					Player player1 = players.get(1 - playerIndex);
					grid.setCellState(i, j, alive, alive ? player0 : Player.NO_PLAYER, alive ? player0.getColor() : Cell.DEAD_COLOR);
					grid.setCellState(grid.getWidth() - i - 1, grid.getHeight() - j - 1, alive, alive ? player1 : Player.NO_PLAYER, alive ? player1.getColor() : Cell.DEAD_COLOR);
				}
			}
		}
	}
}
