package states;

import rendering.Color;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import utils.Timer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 3/23/2017.
 */
public class FadeTransState extends GameState
{
	private GameState preState;
	private GameState postState;
	private boolean lastHalved;
	private Color fadeColor;
	private float duration;
	private Timer time;

	public FadeTransState(GameState preState, GameState postState, float duration)
	{
		this.preState = preState;
		this.postState = postState;
		this.duration = duration;
		this.lastHalved = false;
		this.time = new Timer();
		this.fadeColor = new Color(0,0,0,1);
	}

	@Override
	public void init()
	{

	}

	@Override
	public void cleanUp()
	{

	}

	@Override
	public void tick(GameStateManager stateManager)
	{
		boolean isHalved = time.getTime() >= duration / 2.0f;

		if (isHalved != lastHalved)
		{
			preState.pause();
			postState.resume();
		}

		if (time.getTime() >= duration)
			stateManager.set(postState);
	}

	@Override
	public void render(MasterRenderer renderer)
	{
		float f = (float) Math.sin((time.getTime() / duration) * Math.PI);

		if (time.getTime() / duration <= 0.5f)
		{
			preState.render(renderer);
		} else
		{
			postState.render(renderer);
		}

		Trinket2D.setRenderLevel(101);
		Trinket2D.setDrawColor(new Color(fadeColor.getR(), fadeColor.getG(), fadeColor.getB(), f * fadeColor.getA()));
		Trinket2D.drawRectangle(new Vector2f(), new Vector2f(2));
		Trinket2D.setRenderLevel(0);
	}

	@Override
	public void pause()
	{
		time.pause();
		postState.pause();
	}

	@Override
	public void resume()
	{
		time.reset();
		time.start();
		preState.resume();
	}
}
