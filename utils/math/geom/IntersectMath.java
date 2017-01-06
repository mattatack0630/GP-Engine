package utils.math.geom;

import utils.math.Maths;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/5/2016.
 */
public class IntersectMath
{
	public static IntersectData intersects(GeometryObject geo0, GeometryObject geo1)
	{
		if (geo0 instanceof Sphere && geo1 instanceof Sphere)
			return intersects((Sphere) geo0, (Sphere) geo1);

		if (geo0 instanceof Sphere && geo1 instanceof AABB)
			return intersects((Sphere) geo0, (AABB) geo1);

		if (geo0 instanceof Sphere && geo1 instanceof Plane)
			return intersects((Sphere) geo0, (Plane) geo1);


		if (geo0 instanceof Plane && geo1 instanceof Sphere)
			return intersects((Plane) geo0, (Sphere) geo1);

		if (geo0 instanceof Plane && geo1 instanceof AABB)
			return intersects((Plane) geo0, (AABB) geo1);

		if (geo0 instanceof Plane && geo1 instanceof Plane)
			return intersects((Plane) geo0, (Plane) geo1);


		if (geo0 instanceof AABB && geo1 instanceof Sphere)
			return intersects((AABB) geo0, (Sphere) geo1);

		if (geo0 instanceof AABB && geo1 instanceof AABB)
			return intersects((AABB) geo0, (AABB) geo1);

		if (geo0 instanceof AABB && geo1 instanceof Plane)
			return intersects((AABB) geo0, (Plane) geo1);

		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	private static IntersectData intersects(Sphere geo0, Sphere geo1)
	{
		IntersectData data = new IntersectData();

		Vector3f sub = Vector3f.sub(geo0.getPosition(), geo1.getPosition(), null);

		data.setIntersecting(sub.length() < (geo0.getRadius() + geo1.getRadius()));
		if (data.isIntersecting())
			data.setIntersectionPoint(sub.normalize().scale(geo0.getRadius() / 2f));

		return data;
	}

	private static IntersectData intersects(Sphere geo0, AABB geo1)
	{
		IntersectData data = new IntersectData();
		Vector3f max = geo1.getMax();
		Vector3f min = geo1.getMin();
		Vector3f sc = geo0.getPosition();

		float dist_squared = geo0.getRadius() * geo0.getRadius();
		if (sc.x() < min.x())
			dist_squared -= Maths.squared(sc.x() - min.x());
		else if (sc.x() > max.x())
			dist_squared -= Maths.squared(sc.x() - max.x());
		if (sc.y() < min.y())
			dist_squared -= Maths.squared(sc.y() - min.y());
		else if (sc.y() > max.y())
			dist_squared -= Maths.squared(sc.y() - max.y());
		if (sc.z() < min.z())
			dist_squared -= Maths.squared(sc.z() - min.z());
		else if (sc.z() > max.z())
			dist_squared -= Maths.squared(sc.z() - max.z());

		data.setIntersecting(dist_squared > 0);

		// Collision Normal not implemented
		if (data.isIntersecting())
		{
			Vector3f sub = Vector3f.sub(geo1.getCenter(), geo0.getPosition(), null);
			sub.setMagnitude(geo0.getRadius());
			data.setIntersectionPoint(Vector3f.add(geo0.getPosition(), sub, null));
		}

		return data;
	}

	private static IntersectData intersects(Sphere geo0, Plane geo1)
	{
		IntersectData data = new IntersectData();

		float dist = Geometry.dist(geo1, geo0.getPosition());
		data.setIntersecting(dist < geo0.getRadius());

		if (data.isIntersecting())
		{
			Vector3f nDist = new Vector3f(geo1.getNormal()).scale(-dist);
			data.setIntersectionPoint(Vector3f.add(nDist, geo0.getPosition(), null));
			data.setIntersectionNormal(new Vector3f(geo1.getNormal()));
		}

		return data;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	private static IntersectData intersects(AABB geo0, Sphere geo1)
	{
		return intersects(geo1, geo0);
	}

	private static IntersectData intersects(AABB geo0, AABB geo1)
	{
		IntersectData data = new IntersectData();
		Vector3f amin = geo0.getMin();
		Vector3f amax = geo0.getMax();
		Vector3f bmin = geo1.getMin();
		Vector3f bmax = geo1.getMax();

		boolean imaxx = amax.x() > bmin.x() && amax.x() < bmax.x();
		boolean iminx = amin.x() < bmax.x() && amin.x() > bmin.x();
		boolean iminy = amax.y() > bmin.y() && amax.y() < bmax.y();
		boolean imaxy = amin.y() < bmax.y() && amin.y() > bmin.y();
		boolean imaxz = amax.z() > bmin.z() && amax.z() < bmax.z();
		boolean iminz = amin.z() < bmax.z() && amin.z() > bmin.z();

		data.setIntersecting((iminx || imaxx) && (iminy || imaxy) && (iminz || imaxz));

		return data;
	}

	private static IntersectData intersects(AABB geo0, Plane geo1)
	{
		IntersectData data = new IntersectData();

		Vector3f[] points = geo0.points();

		for (Vector3f point : points)
		{
			float dist = Geometry.dist(geo1, point);
			data.setIntersecting(data.isIntersecting() || dist < 0);
			if (dist > 0)
			{
				data.setIntersectionNormal(new Vector3f(geo1.getNormal()));
				Vector3f intersectPoint = data.getIntersectionPoint();
				intersectPoint = intersectPoint == null ? new Vector3f(point) :
						Vector3f.add(point, intersectPoint, null).scale(.5f);
				data.setIntersectionPoint(intersectPoint);
			}
		}

		return data;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	private static IntersectData intersects(Plane geo0, Sphere geo1)
	{
		return intersects(geo1, geo0);
	}

	private static IntersectData intersects(Plane geo0, AABB geo1)
	{
		return intersects(geo1, geo0);
	}

	private static IntersectData intersects(Plane geo0, Plane geo1)
	{
		IntersectData data = new IntersectData();

		return data;
	}
}
