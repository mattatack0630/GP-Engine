package engine;

import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 3/11/2017.
 * <p>
 * The State manager is used by the engine to control and run game states,
 * like pause, play, winning/ end screen
 */
public class GameStateManager
{
	// The active game state, currently running
	private GameState activeState;
	// A list of all game states, to init and clean
	private List<GameState> states;

	public GameStateManager()
	{
		this.states = new ArrayList<>();
		this.activeState = null;
	}

	/**
	 * Init all of the game states in the current list.
	 * It is important that you run this command only after you have added
	 * all of the games states.
	 */
	public void initStates()
	{
		for (GameState state : states)
			state.init();
	}

	/**
	 * Tick the active state, to run that states logic
	 */
	public void tick()
	{
		activeState.tick(this);
	}

	/**
	 * Render/ Draw the active state
	 */
	public void render(MasterRenderer renderer)
	{
		activeState.render(renderer);
	}

	/**
	 * Clean up all of the states in the managers list.
	 * Again, it is important that this is called after all game states
	 * are added. Called when engine closes in Engine class.
	 */
	public void cleanUp()
	{
		for (GameState state : states)
			state.cleanUp();
	}

	/**
	 * Set the active state, this calls the currently active state's pause method (if not null),
	 * then sets the active state, then calls the new state's resume method.
	 */
	public void setState(GameState state)
	{
		if (activeState != null)
			activeState.pause();

		activeState = state;

		if (activeState != null)
			activeState.resume();
	}

	/**
	 * Add a list of states to the managers list of states.
	 * Call this before initializing each state (Before game begins).
	 */
	public void addStates(List<GameState> states)
	{
		this.states.addAll(states);
	}
}
