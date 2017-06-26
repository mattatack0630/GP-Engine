package utils.math.geom;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 1/25/2017.
 */
public class AABBce extends AABB
{
	public AABBce(Vector3f center, Vector3f extend)
	{
		this.center = new Vector3f(center);
		this.extend = new Vector3f(extend);
		this.min = new Vector3f();
		this.max = new Vector3f();
	}

	public AABBce(AABB src)
	{
		this.center = new Vector3f(src.getCenter());
		this.extend = new Vector3f(src.getExtends());
		this.min = new Vector3f();
		this.max = new Vector3f();
	}

	@Override
	public Vector3f getMin()
	{
		Vector3f.sub(center, extend, min);
		return min;
	}

	@Override
	public Vector3f getMax()
	{
		Vector3f.add(center, extend, max);
		return max;
	}

	@Override
	public Vector3f getCenter()
	{
		return center;
	}

	@Override
	public Vector3f getExtends()
	{
		return extend;
	}

	@Override
	public void setMin(Vector3f min)
	{
		Vector3f max = getMax();

		Vector3f.sub(max, min, extend);
		extend.scale(0.5f);
		Vector3f.add(max, min, center);
		center.scale(0.5f);
	}

	@Override
	public void setMax(Vector3f max)
	{
		Vector3f min = getMin();

		Vector3f.sub(max, min, extend);
		extend.scale(0.5f);
		Vector3f.add(max, min, center);
		center.scale(0.5f);
	}

	@Override
	public void setCenter(Vector3f center)
	{
		this.center.set(center.x(), center.y(), center.z());
	}

	@Override
	public void setExtends(Vector3f extend)
	{
		this.extend.set(extend.x(), extend.y(), extend.z());
	}
}
