package rendering.renderers;

import rendering.RenderData;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 1/9/2017.
 */
public class SpriteRenderData extends RenderData
{
	private String animationOn;
	private float timeAt;

	public SpriteRenderData(String animationOn, Vector3f pos)
	{
		super(pos);
		this.animationOn = animationOn;
		this.timeAt = 0;
	}

	public void setAnimationOn(String animationOn)
	{
		this.animationOn = animationOn;
	}

	public void setTimeAt(float time)
	{
		this.timeAt = time;
	}

	public String getAnimationOn()
	{
		return animationOn;
	}

	public float getTimeAt()
	{
		return timeAt;
	}
}
