package utils.math.geom;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/5/2016.
 */
public class AABBmm extends AABB
{
	public static final Vector3f BOUNDS_MAX = new Vector3f(-10000);
	public static final Vector3f BOUNDS_MIN = new Vector3f(10000);

	public AABBmm(Vector3f min, Vector3f max)
	{
		this.min = new Vector3f(min);
		this.max = new Vector3f(max);
		this.center = new Vector3f();
		this.extend = new Vector3f();
	}

	@Override
	public Vector3f getMin()
	{
		return min;
	}

	@Override
	public Vector3f getMax()
	{
		return max;
	}

	@Override
	public Vector3f getCenter()
	{
		Vector3f.add(max, min, center);
		center.scale(0.5f);
		return center;
	}

	@Override
	public Vector3f getExtends()
	{
		Vector3f.sub(max, min, extend);
		extend.scale(0.5f);
		return extend;
	}

	@Override
	public void setMin(Vector3f min)
	{
		this.min.set(min.x(), min.y(), min.z());
	}

	@Override
	public void setMax(Vector3f max)
	{
		this.max.set(max.x(), max.y(), max.z());
	}

	@Override
	public void setCenter(Vector3f center)
	{
		Vector3f e = getExtends();
		Vector3f.sub(center, e, min);
		Vector3f.add(center, e, max);
	}

	@Override
	public void setExtends(Vector3f extend)
	{
		Vector3f c = getCenter();
		Vector3f.sub(c, extend, min);
		Vector3f.add(c, extend, max);
	}
}