package physics.collidables;

import rendering.Color;
import rendering.RenderData;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 8/26/2016.
 */
public class Sphere extends Collidable
{
	public Vector3f pos;
	public float radius;

	public Sphere(Vector3f pos, float radius){
		super(Collidable.SPHERE);
		this.pos = pos;
		this.radius = radius;
		data = new RenderData(new Vector3f());
		data.tempColor = Color.GREEN;
		data.tempColor = new Color((float)Math.random(), (float)Math.random(), (float) Math.random(), .5f);
	}

	public void update(Vector3f pos,  Vector3f COM, Vector3f rot)
	{
		this.pos = new Vector3f(pos);
	}


	public void tempRender(MasterRenderer renderer){
	}
}
