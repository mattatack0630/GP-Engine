package gui.Transition;

import gui.Component.Panel.Panel;
import gui.GuiScene;

/**
 * Created by mjmcc on 10/19/2016.
 */
public class OpacityTransition extends Transition
{

	private float newOpacity;
	private float speed;

	public OpacityTransition(float newOpacity, float speed)
	{
		this.newOpacity = newOpacity;
		this.speed = speed;
	}

	public void setNewOpacity(float newOpacity)
	{
		this.newOpacity = newOpacity;
		finishedTransition = false;
	}

	public void doTransition(GuiScene scene)
	{
		Panel root = scene.getRoot();

		float op = root.opacity+((newOpacity - root.opacity)*speed);
		scene.setOpacity(op);

		if(Math.abs(newOpacity-root.opacity) < 0.01f)
			finishedTransition = true;
	}

}
