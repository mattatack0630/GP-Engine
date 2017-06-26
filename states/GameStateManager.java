package states;

import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by mjmcc on 3/11/2017.
 * <p>
 * The State manager is used by the engine to control and run game states,
 * like pause, play, winning/ end screen
 */
public class GameStateManager
{
	// Use stack??? so that only top state is rendered and updated
	// This will allow the game states not to worry which state to switch back to

	// The active game state, currently running
	private GameState pastState;
	// A list of all game states, to init and clean
	private List<GameState> states;
	private Stack<GameState> statesStack;

	public GameStateManager()
	{
		this.states = new ArrayList<>();
		this.statesStack = new Stack<>();
		this.pastState = null;
	}

	/**
	 * Init all of the game states in the current list.
	 * It is important that you run this command only after you have added
	 * all of the games states.
	 */
	public void initStates()
	{
		for (GameState state : states)
			if(state != null)
				state.init();
	}

	/**
	 * Tick the active state, to run that states logic
	 */
	public void update()
	{
		if(!statesStack.isEmpty())
		{
			GameState topState = statesStack.peek();

			if (pastState != topState)
			{
				if (pastState != null)
					pastState.pause();
				topState.resume();
			}

			topState.tick(this);
			pastState = topState;
		}
	}

	/**
	 * Render/ Draw the active state
	 */
	public void render(MasterRenderer renderer)
	{
		if(!statesStack.isEmpty())
		{
			GameState topState = statesStack.peek();
			topState.render(renderer);
		}
	}

	/**
	 * Clean up all of the states in the managers list.
	 * Again, it is important that this is called after all game states
	 * are added. Called when engine closes in Engine class.
	 */
	public void cleanUp()
	{
		for (GameState state : states)
			if(state != null)
				state.cleanUp();
	}

	/**
	 * Set the active state, this calls the currently active state's pause method (if not null),
	 * then sets the active state, then calls the new state's resume method.
	 */
	public void set(GameState state)
	{
		if(!statesStack.isEmpty())
			statesStack.pop();
		statesStack.push(state);
	}

	/**
	 * Push a gamestate to the front of the game state stack.
	 * This will allow this state to the main active state, so it gets ticked and rendered.
	 * Once pop() is called, the current state is restored.
	 * */
	public void push(GameState state)
	{
		statesStack.push(state);
	}

	/**
	 * Remove the top active state from the stack. This will set the state back to the previously
	 * active state (if the stack is not empty)
	 * */
	public void pop()
	{
		if(!statesStack.isEmpty())
			statesStack.pop();
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
