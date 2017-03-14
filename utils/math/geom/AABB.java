package utils.math.geom;

import utils.math.linear.LinearAlgebra;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 1/25/2017.
 */
public abstract class AABB extends GeometryObject
{
	protected Vector3f min;
	protected Vector3f max;
	protected Vector3f center;
	protected Vector3f extend;

	public abstract Vector3f getMin();

	public abstract Vector3f getMax();

	public abstract Vector3f getCenter();

	public abstract Vector3f getExtends();

	public abstract void setMin(Vector3f min);

	public abstract void setMax(Vector3f max);

	public abstract void setCenter(Vector3f center);

	public abstract void setExtends(Vector3f extend);

	public String equation()
	{
		return "";
	}

	public Vector3f[] points()
	{
		Vector3f[] points = new Vector3f[8];

		Vector3f min = getMin();
		Vector3f max = getMax();

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
		Vector3f min = getMin();
		Vector3f max = getMax();

		min.setX((position.x() < min.x()) ? position.x() : min.x());
		min.setY((position.y() < min.y()) ? position.y() : min.y());
		min.setZ((position.z() < min.z()) ? position.z() : min.z());
		max.setX((position.x() > max.x()) ? position.x() : max.x());
		max.setY((position.y() > max.y()) ? position.y() : max.y());
		max.setZ((position.z() > max.z()) ? position.z() : max.z());

		setMin(min);
		setMax(max);
	}

	public static AABBmm transform(AABBmm boundingBox, Matrix4f transform)
	{
		AABBmm aabb = new AABBmm(boundingBox.getMin(), boundingBox.getMax());
		Vector3f min = LinearAlgebra.mult(transform, boundingBox.getMin(), null);
		Vector3f max = LinearAlgebra.mult(transform, boundingBox.getMax(), null);
		aabb.setMin(min);
		aabb.setMax(max);
		return aabb;
	}
}
