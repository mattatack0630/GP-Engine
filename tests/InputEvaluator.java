package tests;

import input.InputManager;
import rendering.DisplayManager;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 2/26/2017.
 */
public class InputEvaluator
{
	private InputManager inputManager;
	private CellGrid inputGrid;
	private GameGui gui;

	public InputEvaluator(InputManager inputManager, CellGrid inputGrid, GameGui gui)
	{
		this.inputManager = inputManager;
		this.inputGrid = inputGrid;
		this.gui = gui;
	}

	public Vector2f getInputCoord()
	{
		Vector2f mouseCoords = new Vector2f(inputManager.getGLCursorCoords());
		mouseCoords.add(new Vector2f(1.0f));
		mouseCoords.multElements(new Vector2f(0.5f / DisplayManager.getAspect(), 0.5f));
		mouseCoords.multElements(new Vector2f(inputGrid.getWidth(), inputGrid.getHeight()));
		mouseCoords.set((int) mouseCoords.x(), (int) mouseCoords.y());

		if (mouseCoords.x() > inputGrid.getWidth() || mouseCoords.y() > inputGrid.getHeight() ||
				mouseCoords.x() < 0 || mouseCoords.y() < 0)
			mouseCoords.set(-1, -1);

		return mouseCoords;
	}

	public boolean pressingRemove()
	{
		return inputManager.isMouseButtonClicked(1);
	}

	public boolean pressingAdditon()
	{
		return inputManager.isMouseButtonClicked(0);
	}

	public boolean pressingFinishTurn()
	{
		return gui.isFinishTurnClicked();
	}
}
