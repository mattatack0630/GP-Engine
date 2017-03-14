package gui.Transition;

import gui.Component.Panel.Panel;
import gui.GuiScene;
import utils.math.linear.vector.Vector2f;

/**
 * The Move Transition Class
 * Move a guiScene to a new Position at a speed
 */
public class MoveTransition extends Transition
{
	// The speed to move at
	public float speed;
	public Vector2f oldPosition;
	// The new Position to move to
	public Vector2f newPosition;

	public MoveTransition(Vector2f newPosition, float speed)
	{
		this.newPosition = new Vector2f(newPosition);
		this.speed = speed;
	}

	/**
	 * Reset the move to position
	 * @param newPosition the Vector to setElements the newPosition to
	 * */
	public void setToNewPosition(Vector2f newPosition)
	{
		this.newPosition = new Vector2f(newPosition);
		// Turn the finished variable to false
		this.finishedTransition = false;
	}

	/**
	 * Move a guiScene by a step
	 * @param scene the guiScene to effect
	 * */
	public void doTransition(GuiScene scene)
	{
		// The guiScenes root element
		Panel root = scene.getRoot();

		// Vector to add to the current position (A-B)*speed
		Vector2f addPos = Vector2f.sub(newPosition, root.absolutePos, null);
		// Scale to the speed, adds a slowdown affect when closer to target
		addPos.scale(speed);

		scene.setPosition(Vector2f.add(root.absolutePos, addPos, null));

		// If the vector is close enough the desired position, then the animation is done
		if (Math.abs(root.absolutePos.x() - newPosition.x()) < .001f
				&& Math.abs(root.absolutePos.y() - newPosition.y()) < .001f)
			finishedTransition = true;
	}
}
