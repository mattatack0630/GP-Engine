package gui_m4;

import engine.Engine;
import gui_m4.events.Clickable;
import org.lwjgl.input.Mouse;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

public class ClickChecker
{
	private Clickable clickedObject;
	private Vector2f clickPosition;
	private Vector2f clickSize;
	private boolean lastHoverState;
	private boolean heldState;

	public ClickChecker(float x, float y, float sx, float sy, Clickable clickedObject)
	{
		this.clickSize = new Vector2f();
		this.clickPosition = new Vector2f();
		this.clickedObject = clickedObject;
		setClickDimensions(x, y, sx, sy);
	}

	public ClickChecker(Vector2f pos, Vector2f size, Clickable clickedObject)
	{
		this.clickSize = new Vector2f();
		this.clickPosition = new Vector2f();
		this.clickedObject = clickedObject;
		setClickDimensions(pos, size);
	}

	public void setClickDimensions(Vector2f position, Vector2f size)
	{
		clickPosition.set(position);
		clickSize.set(size);
		clickSize.scale(0.5f);
	}

	// Change this to sx/2, sy/2
	public void setClickDimensions(float x, float y, float sx, float sy)
	{
		clickPosition.setX(x);
		clickPosition.setY(y);
		clickSize.setX(sx / 2.0f);
		clickSize.setY(sy / 2.0f);
	}

	public void checkClick()
	{
		Vector2f cursor = Maths.glCoordsFromPixle(new Vector2f(Mouse.getX(), Mouse.getY()));
		boolean mouseButtonDown = Engine.getInputManager().isMouseButtonDown(0);
		boolean mouseButtonClicked = Engine.getInputManager().isMouseButtonClicked(0);
		boolean mouseButtonReleased = Engine.getInputManager().isMouseButtonReleased(0);

		boolean newHoverState = false;
		boolean clickedState = false;

		if (cursor.x() > clickPosition.x() - clickSize.x() && cursor.y() > clickPosition.y() - clickSize.y() &&
				cursor.x() < clickPosition.x() + clickSize.x() && cursor.y() < clickPosition.y() + clickSize.y())
		{
			newHoverState = true;

			if(mouseButtonClicked)
				clickedState = true;
		}

		if (clickedState)
		{
			clickedObject.onClick();
			heldState = true;
		}

		// TODO fix the error where you can release after starting away from element
		if (!mouseButtonDown && heldState)
		{
			clickedObject.onRelease();
			heldState = false;
		}

		if (heldState)
			clickedObject.onHold();

		if (!lastHoverState && newHoverState)
			clickedObject.onBeginHover();

		if(newHoverState)
			clickedObject.onHover();

		if (lastHoverState && !newHoverState)
			clickedObject.onEndHover();

		lastHoverState = newHoverState;
	}
}
