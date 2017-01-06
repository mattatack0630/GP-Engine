package gui.Transition;

import gui.GuiScene;

/**
 * Transition Class
 * A class that can effect a GuiScene in some way. Like move, fade, or spin
 */
public abstract class Transition
{
	// Stores whether or no the transition has completed
	protected boolean finishedTransition;

	/**
	 * Do a transition step on a scene
	 * Implemented per Transition
	 * @param scene the GuiScene to effect
	 * */
	protected abstract void doTransition(GuiScene scene);

	/**
	 * Apply an entire transition to the scene
	 * @param scene the GuiScene to effect
	 * */
	public void applyTransition(GuiScene scene)
	{
		if (!finishedTransition)
			doTransition(scene);
	}
}
