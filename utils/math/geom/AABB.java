package utils.math.geom;

import utils.math.linear.LinearAlgebra;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/5/2016.
 */
public class AABB extends GeometryObject
{
	public static final Vector3f BOUNDS_MAX = new Vector3f(-10000, -10000, -10000);
	public static final Vector3f BOUNDS_MIN = new Vector3f(10000, 10000, 10000);

	private Vector3f min;
	private Vector3f max;

	public AABB(Vector3f min, Vector3f max)
	{
		this.min = new Vector3f(min);
		this.max = new Vector3f(max);
	}

	public Vector3f getMax()
	{
		return max;
	}

	public Vector3f getMin()
	{
		return min;
	}

	public void setMax(Vector3f max)
	{
		this.max = max;
	}

	public void setMin(Vector3f min)
	{
		this.min = min;
	}

	public void setCenterExtends(Vector3f center, Vector3f scale)
	{
		Vector3f.sub(center, scale, min);
		Vector3f.add(center, scale, max);
	}

	public Vector3f[] points()
	{
		Vector3f[] points = new Vector3f[8];

		points[0] = new Vector3f(min.x(), min.y(), min.z());
		points[1] = new Vector3f(max.x(), min.y(), min.z());
		points[2] = new Vector3f(min.x(), max.y(), min.z());
		points[3] = new Vector3f(min.x(), min.y(), max.z());

		points[4] = new Vector3f(max.x(), max.y(), max.z());
		points[5] = new Vector3f(min.x(), max.y(), max.z());
		points[6] = new Vector3f(max.x(), min.y(), max.z());
		points[7] = new Vector3f(max.x(), max.y(), min.z());

		return points;
	}

	public void checkBoundsUpdate(Vector3f position)
	{
		min.setX((position.x() < min.x()) ? position.x() : min.x());
		min.setY((position.y() < min.y()) ? position.y() : min.y());
		min.setZ((position.z() < min.z()) ? position.z() : min.z());
		max.setX((position.x() > max.x()) ? position.x() : max.x());
		max.setY((position.y() > max.y()) ? position.y() : max.y());
		max.setZ((position.z() > max.z()) ? position.z() : max.z());
	}

	public static AABB transform(AABB boundingBox, Matrix4f transform)
	{
		Vector3f max = LinearAlgebra.mult(transform, boundingBox.getMax(), null);
		Vector3f min = LinearAlgebra.mult(transform, boundingBox.getMin(), null);
		return new AABB(new Vector3f(min.x(), min.y(), min.z()), new Vector3f(max.x(), max.y(), max.z()));
	}

	public Vector3f getCenter()
	{
		// Maybe update to not calc every time
		return Vector3f.add(min, max, null).scale(0.5f);
	}

	public Vector3f getExtends()
	{
		return Vector3f.sub(max, min, null).scale(.5f);
	}

	@Override
	public String equation()
	{
		return null;
	}

}
