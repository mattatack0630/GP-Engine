package states;

import rendering.renderers.MasterRenderer;

/**
 * Created by mjmcc on 3/11/2017.
 */
public abstract class GameState
{
	public abstract void init();

	public abstract void cleanUp();

	public abstract void tick(GameStateManager stateManager);

	public abstract void render(MasterRenderer renderer);

	public abstract void pause();

	public abstract void resume();
}
