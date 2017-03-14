package tests;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 2/15/2017.
 */
public class Turn
{
	private static final int BIOMASS_MULT = 2;
	private static final int MAX_REMOVALS = 2;
	private static final int MAX_ADDITIONS = 2;

	private Player player;
	private CellGrid grid;
	private int turnBiomass;
	private boolean turnOver;
	private List<Pair<Integer, Integer>> additions;
	private List<Pair<Integer, Integer>> removals;

	public Turn(Player player, CellGrid grid)
	{
		this.player = player;
		this.grid = grid;
		this.turnBiomass = 0;
		this.additions = new ArrayList<>();
		this.removals = new ArrayList<>();
	}

	public void reset(Player newPlayer)
	{
		additions.clear();
		removals.clear();
		turnOver = false;
		turnBiomass = 0;
		player = newPlayer;
	}

	public boolean addCellAddition(Integer i, Integer j)
	{
		Pair p = new Pair(i, j);
		Cell c = grid.getCell(i, j);

		boolean valid = (i >= 0 && j >= 0 && i < grid.getWidth() && j < grid.getHeight()) &&
				(player.getBiomass() + turnBiomass >= (additions.size() + 1) * BIOMASS_MULT) &&
				(!additions.contains(p)) &&
				(!c.isAlive());

		if (valid)
		{
			additions.add(new Pair(i, j));
		}

		return valid;
	}

	public boolean addCellRemoval(Integer i, Integer j)
	{
		Pair p = new Pair(i, j);
		Cell c = grid.getCell(i, j);

		boolean valid = (i >= 0 && j >= 0 && i < grid.getWidth() && j < grid.getHeight()) &&
				(!removals.contains(p)) &&
				(removals.size() < MAX_REMOVALS) &&
				(c.isAlive());

		if (valid)
		{
			removals.add(p);
			if (c.getOwner().equals(player))
				turnBiomass += 1;
		}

		return valid;
	}

	public List<Pair<Integer, Integer>> getCellRemovals()
	{
		return removals;
	}

	public List<Pair<Integer, Integer>> getCellAdditions()
	{
		return additions;
	}

	public int getBiomass()
	{
		return turnBiomass;
	}

	public boolean isOver()
	{
		return turnOver;
	}

	public void setIsOver(boolean isOver)
	{
		this.turnOver = isOver;
	}
}
