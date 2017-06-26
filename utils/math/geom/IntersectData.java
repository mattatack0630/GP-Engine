package utils.math.geom;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/17/2016.
 */
public class IntersectData
{
	private boolean isIntersecting;
	private boolean hasMTV;
	private boolean hasPoint;
	private boolean hasNormal;
	private Vector3f intersectionPoint;
	private Vector3f intersectionNormal;
	private Vector3f minTranslateVector;
	private IntersectType intersectType;

	public IntersectData()
	{
		intersectionPoint = new Vector3f();
		intersectionNormal = new Vector3f();
		minTranslateVector = new Vector3f();
		hasMTV = false;
		hasPoint = false;
		hasNormal = false;
		isIntersecting = false;
		intersectType = IntersectType.NONE;
	}

	public Vector3f getIntersectionPoint()
	{
		return intersectionPoint;
	}

	public void setIntersecting(boolean intersecting)
	{
		isIntersecting = intersecting;
	}

	public void setIntersectionPoint(Vector3f intersectionPoint)
	{
		this.intersectionPoint = intersectionPoint;
		this.hasPoint = true;
	}

	public void setIntersectionNormal(Vector3f intersectionNormal)
	{
		this.intersectionNormal = intersectionNormal;
		this.hasNormal = true;
	}

	public void setIntersectType(IntersectType intersectType)
	{
		this.intersectType = intersectType;
	}

	public boolean isIntersecting()
	{
		return isIntersecting;
	}

	public Vector3f getIntersectionNormal()
	{
		return intersectionNormal;
	}

	public void setMTV(Vector3f MTV)
	{
		this.minTranslateVector = MTV;
		this.hasMTV = true;
	}

	public Vector3f getMinTranslateVector()
	{
		return minTranslateVector;
	}

	public IntersectType getIntersectType()
	{
		return intersectType;
	}

	public boolean isHasMTV()
	{
		return hasMTV;
	}

	public boolean isHasPoint()
	{
		return hasPoint;
	}

	public boolean isHasNormal()
	{
		return hasNormal;
	}

}
