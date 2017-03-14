package tests;

import enitities.Entity;
import rendering.Color;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 2/13/2017.
 */
public class Cell extends Entity
{
	public static Vector3f DEAD_COLOR = new Vector3f(0.1f);

	// Used to display cells color, and interpolate colors
	private Vector3f oldColor;
	private Vector3f newColor;
	private float deltaC;

	// Used to keep track of cell state
	private Player cellOwner;
	private boolean alive;

	public Cell()
	{
		this.cellOwner = Player.NO_PLAYER;
		this.alive = false;

		this.oldColor = Color.NONE.rgb();
		this.newColor = Color.NONE.rgb();
		this.deltaC = 1.0f;
	}

	public void setVisualColor(Vector3f color)
	{
		if (newColor != color)
		{
			this.newColor = color;
			this.deltaC = 0.0f;
		}
	}

	public void setState(boolean state)
	{
		this.alive = state;
	}

	public void setOwner(Player p)
	{
		this.cellOwner = p;
	}

	public Player getOwner()
	{
		return cellOwner;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public float getDeltaC()
	{
		return deltaC;
	}

	public Vector3f getOldColor()
	{
		return oldColor;
	}

	public Vector3f getNewColor()
	{
		return newColor;
	}

	public void setOldColor(Vector3f oldColor)
	{
		this.oldColor = oldColor;
	}

	public void setNewColor(Vector3f newColor)
	{
		this.newColor = newColor;
	}

	public void setDeltaC(float deltaC)
	{
		this.deltaC = deltaC;
	}
}
