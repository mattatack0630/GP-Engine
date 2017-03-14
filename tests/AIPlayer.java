package tests;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 2/16/2017.
 */
public class AIPlayer extends Player
{
	public AIPlayer(Vector3f color)
	{
		super("AI", color);
	}

	@Override
	public boolean constructTurn(Turn turn)
	{
		return false;
	}
}
