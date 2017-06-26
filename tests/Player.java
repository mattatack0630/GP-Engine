package tests;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 2/15/2017.
 */
public abstract class Player
{
	public static Player NO_PLAYER = new Player("NONE", new Vector3f(0.1f))
	{
		@Override
		public boolean constructTurn(Turn turn)
		{
			return false;
		}
	};

	private Vector3f color;
	private String name;
	private int biomass;
	private boolean isEliminated;

	public Player(String name, Vector3f color)
	{
		this.name = name;
		this.biomass = 0;
		this.color = color;
		this.isEliminated = false;
	}

	public abstract boolean constructTurn(Turn turn);

	public void setBiomass(int biomass)
	{
		this.biomass = biomass;
	}

	public Vector3f getColor()
	{
		return color;
	}

	public int getBiomass()
	{
		return biomass;
	}

	public String getName()
	{
		return name;
	}

	public void setEliminated(boolean eliminated)
	{
		this.isEliminated = eliminated;
	}

	public boolean isEliminated()
	{
		return isEliminated;
	}

	public void setColor(Vector3f color)
	{
		this.color.set(color.x(), color.y(), color.z());
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
