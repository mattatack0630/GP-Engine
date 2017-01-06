package utils.math.geom;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/17/2016.
 */
public class Sphere extends GeometryObject
{
	private Vector3f position;
	private float radius;

	public Sphere(Vector3f pos, float rad)
	{
		this.position = pos;
		this.radius = rad;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public float getRadius()
	{
		return radius;
	}

	public void setRadius(float radius)
	{
		this.radius = radius;
	}


	@Override
	public String equation()
	{
		return null;
	}
}
