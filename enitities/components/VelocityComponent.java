package enitities.components;

import enitities.Entity;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 4/2/2017.
 */
public class VelocityComponent extends EntityComponent
{
	private Vector3f velocity;
	private Vector3f rotVelocity;

	public VelocityComponent(Entity e, Vector3f velocity)
	{
		super(e);
		this.velocity = velocity;
		this.rotVelocity = new Vector3f();
	}

	public Vector3f getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vector3f velocity)
	{
		this.velocity = velocity;
	}
}
