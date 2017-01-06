package utils.math.geom;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/17/2016.
 */
public class IntersectData
{
	private boolean isIntersecting;
	private Vector3f intersectionPoint;
	private Vector3f intersectionNormal;

	public Vector3f getIntersectionPoint()
	{
		return intersectionPoint;
	}

	public void setIntersectionPoint(Vector3f intersectionPoint)
	{
		this.intersectionPoint = intersectionPoint;
	}

	public boolean isIntersecting()
	{
		return isIntersecting;
	}

	public void setIntersecting(boolean intersecting)
	{
		isIntersecting = intersecting;
	}

	public void setIntersectionNormal(Vector3f intersectionNormal)
	{
		this.intersectionNormal = intersectionNormal;
	}

	public Vector3f getIntersectionNormal()
	{
		return intersectionNormal;
	}
}
