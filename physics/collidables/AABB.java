package physics.collidables;

import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 8/26/2016.
 */
public class AABB extends Collidable
{
	public Vector3f max;
	public Vector3f min;


	public AABB(Vector3f min, Vector3f max){
		super(Collidable.AABB);
		this.max = max;
		this.min = min;
		reset();
	}

	public void update(Vector3f pos, Vector3f COM, Vector3f rot)
	{

	}

	public void tempRender(MasterRenderer renderer){

	}

	public void reset()
	{
		max = new Vector3f(Float.MIN_VALUE,Float.MIN_VALUE,Float.MIN_VALUE);
		min = new Vector3f(Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE);
	}
}
