package tests;

import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 2/16/2017.
 */
public class UserPlayer extends Player
{
	private InputEvaluator inputEvaluator;

	public UserPlayer(String name, Vector3f color, InputEvaluator inputEvaluator)
	{
		super(name, color);
		this.inputEvaluator = inputEvaluator;
	}

	@Override
	public boolean constructTurn(Turn t)
	{
		Vector2f inCoord = inputEvaluator.getInputCoord();
		boolean remClick = inputEvaluator.pressingRemove();
		boolean addClick = inputEvaluator.pressingAdditon();

		if (inCoord.x() != -1 & inCoord.y() != -1)
		{
			if (remClick)
				t.addCellRemoval((int) inCoord.x(), (int) inCoord.y());
			if (addClick)
				t.addCellAddition((int) inCoord.x(), (int) inCoord.y());
		}

		if (inputEvaluator.pressingFinishTurn())
			t.setIsOver(true);

		return false;
	}
}
