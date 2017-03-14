package animation;

import rendering.RenderData;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Euler;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 11/7/2016.
 *
 * Data used render an animated model instance
 */
public class AnimatedRenderData extends RenderData
{
	// Frame/Time that the animation is currently on
	public float time;
	// The animation name that the animated model is currently on
	public String animationOn;

	/**
	 * Constructors
	 */
	public AnimatedRenderData()
	{
		this(new Vector3f(), new Euler(), new Vector3f(1));
	}

	public AnimatedRenderData(Vector3f pos, Rotation rot, Vector3f s)
	{
		super(pos, rot, s);
		time = 0;
		animationOn = "Defualt";
	}

	public AnimatedRenderData(Matrix4f transformMatrix)
	{
		super(transformMatrix);
		time = 0;
		animationOn = "Defualt";
	}

	/**
	 * Getters and setters
	 * */
	public float getTime()
	{
		return time;
	}

	public String getAnimationOn()
	{
		return animationOn;
	}

	public void setTime(float time)
	{
		this.time = time;
	}

	public void setAnimationOn(String animationOn)
	{
		this.animationOn = animationOn;
	}
}
