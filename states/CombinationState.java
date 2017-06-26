package states;

import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 4/26/2017.
 *
 * Meta-GameState that combines and wrap one or more states into a single state
 * then, the states can be update, render, ticked, etc at the same time.
 */
public class CombinationState extends GameState
{
	// Combined States List
	private List<GameState> states;

	/**
	 * Constructor takes in a list of states
	 * The states are added to a list in the order that they are recieved
	 * This determines what order the states will be called
	 * */
	public CombinationState(GameState... states)
	{
		this.states = new ArrayList<>();

		for (GameState state : states)
		{
			this.states.add(state);
		}
	}

	@Override
	public void init()
	{
		for (GameState state : states)
		{
			state.init();
		}
	}

	@Override
	public void cleanUp()
	{
		for (GameState state : states)
		{
			state.cleanUp();
		}
	}

	@Override
	public void tick(GameStateManager stateManager)
	{
		for (GameState state : states)
		{
			state.tick(stateManager);
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
		for (GameState state : states)
		{
			state.render(renderer);
		}
	}

	@Override
	public void pause()
	{
		for (GameState state : states)
		{
			state.pause();
		}
	}

	@Override
	public void resume()
	{
		for (GameState state : states)
		{
			state.resume();
		}
	}
}
