package gui.interactive;

import org.lwjgl.input.Mouse;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

public class ClickChecker
{
	private Clickable clickedObject;
	private Vector2f clickPosition;
	private Vector2f clickSize;
	private boolean lastClickState;
	private boolean lastHoverState;
	private boolean lastHoldState;
	private boolean lastButtonState;

	public ClickChecker(float x, float y, float sx, float sy, Clickable clickedObject)
	{
		setClickDimensions(x, y, sx, sy);
		this.clickedObject = clickedObject;
	}

	public void setClickDimensions(float x, float y, float sx, float sy)
	{
		clickPosition = new Vector2f(x, y);
		clickSize = new Vector2f(sx, sy);
	}

	public void checkClick()
	{
		Vector2f cursor = Maths.glCoordsFromPixle(new Vector2f(Mouse.getX(), Mouse.getY()));
		boolean mouseButton = Mouse.isButtonDown(0);
		boolean newClickState = false;
		boolean newHoverState = false;

		if (cursor.x() > clickPosition.x() - clickSize.x() && cursor.y() > clickPosition.y() - clickSize.y() &&
				cursor.x() < clickPosition.x() + clickSize.x() && cursor.y() < clickPosition.y() + clickSize.y())
		{
			if (mouseButton && !lastButtonState)
			{
				newClickState = true;
				lastHoldState = true;
				lastButtonState = false;
			} else
			{
				newHoverState = true;
			}
		}

		if (mouseButton && lastHoldState)
			clickedObject.onHold();

		if (!lastClickState && newClickState)
			clickedObject.onClick();

		if (lastHoldState && !mouseButton)
			clickedObject.onRelease();

		if (!lastHoverState && newHoverState)
			clickedObject.onBeginHover();

		if(newHoverState)
			clickedObject.onHover();

		if (lastHoverState && !newHoverState)
			clickedObject.onEndHover();

		if(!mouseButton)
			lastHoldState = false;

		lastClickState = newClickState;
		lastHoverState = newHoverState;
		lastButtonState = mouseButton;
	}
}
