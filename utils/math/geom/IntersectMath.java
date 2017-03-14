package utils.math.geom;

import utils.math.Maths;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/5/2016.
 */
public class IntersectMath
{
	public static IntersectData intersects(GeometryObject geo0, GeometryObject geo1, IntersectParams params)
	{
		if (geo0 instanceof Sphere && geo1 instanceof Sphere)
			return intersects((Sphere) geo0, (Sphere) geo1, params);

		if (geo0 instanceof Sphere && geo1 instanceof AABB)
			return intersects((Sphere) geo0, (AABB) geo1, params);

		if (geo0 instanceof Sphere && geo1 instanceof Plane)
			return intersects((Sphere) geo0, (Plane) geo1, params);


		if (geo0 instanceof Plane && geo1 instanceof Sphere)
			return intersects((Plane) geo0, (Sphere) geo1, params);

		if (geo0 instanceof Plane && geo1 instanceof AABB)
			return intersects((Plane) geo0, (AABB) geo1, params);

		if (geo0 instanceof Plane && geo1 instanceof Plane)
			return intersects((Plane) geo0, (Plane) geo1, params);


		if (geo0 instanceof AABB && geo1 instanceof Sphere)
			return intersects((AABB) geo0, (Sphere) geo1, params);

		if (geo0 instanceof AABB && geo1 instanceof AABB)
			return intersects((AABB) geo0, (AABB) geo1, params);

		if (geo0 instanceof AABB && geo1 instanceof Plane)
			return intersects((AABB) geo0, (Plane) geo1, params);

		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	private static IntersectData intersects(Sphere geo0, Sphere geo1, IntersectParams params)
	{
		IntersectData data = new IntersectData();

		Vector3f sub = Vector3f.sub(geo0.getPosition(), geo1.getPosition(), null);

		data.setIntersecting(sub.length() < (geo0.getRadius() + geo1.getRadius()));

		if (data.isIntersecting())
		{
			sub.normalize();
			if (params.isCalculateIntersectNormal())
				data.setIntersectionNormal(new Vector3f(sub));
			if (params.isCalculateIntersectPoint())
				data.setIntersectionPoint(new Vector3f(sub.scale(geo0.getRadius() / 2.0f)));
		}

		return data;
	}

	private static IntersectData intersects(Sphere geo0, AABB geo1, IntersectParams params)
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

		if (data.isIntersecting())
		{
			Vector3f sub = Vector3f.sub(geo1.getCenter(), geo0.getPosition(), null);
			sub.setMagnitude(geo0.getRadius());

			if (params.isCalculateIntersectPoint())
				data.setIntersectionPoint(Vector3f.add(geo0.getPosition(), sub, null));
			if (params.isCalculateIntersectNormal())
				data.setIntersectionNormal(new Vector3f(sub).normalize());
		}

		return data;
	}

	private static IntersectData intersects(Sphere geo0, Plane geo1, IntersectParams params)
	{
		IntersectData data = new IntersectData();

		float dist = Geometry.dist(geo1, geo0.getPosition());
		data.setIntersecting(dist < geo0.getRadius());

		if (data.isIntersecting())
		{
			Vector3f nDist = new Vector3f(geo1.getNormal()).scale(-dist);
			if (params.isCalculateIntersectPoint())
				data.setIntersectionPoint(Vector3f.add(nDist, geo0.getPosition(), null));
			if (params.isCalculateIntersectNormal())
				data.setIntersectionNormal(new Vector3f(geo1.getNormal()));
		}

		return data;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	private static IntersectData intersects(AABB geo0, Sphere geo1, IntersectParams params)
	{
		return intersects(geo1, geo0, params);
	}

	private static IntersectData intersects(AABB geo0, AABB geo1, IntersectParams params)
	{
		IntersectData data = new IntersectData();

		Vector3f ea = Vector3f.add(geo0.getExtends(), geo1.getExtends(), null);
		Vector3f cs = Vector3f.sub(geo0.getCenter(), geo1.getCenter(), null);
		Vector3f csa = (Vector3f) new Vector3f(cs).absolute();
		Vector3f sub = Vector3f.sub(ea, csa, null);

		data.setIntersecting((sub.x() > 0 && sub.y() > 0 && sub.z() > 0));

		if (data.isIntersecting())
		{
			if (params.isCalculateIntersectNormal())
			{
				Vector3f normal = new Vector3f();

				float min = Maths.min(sub.x(), sub.y(), sub.z());

				if (min == sub.x()) normal.setX(cs.x() < 0 ? -1 : 1);
				if (min == sub.y()) normal.setY(cs.y() < 0 ? -1 : 1);
				if (min == sub.z()) normal.setZ(cs.z() < 0 ? -1 : 1);

				data.setIntersectionNormal(normal);
				data.setMTV(Vector3f.multElements(sub, normal, null));
			}
		}

		return data;
	}

	private static IntersectData intersects(AABB geo0, Plane geo1, IntersectParams params)
	{
		IntersectData data = new IntersectData();

		Vector3f[] points = geo0.points();

		for (Vector3f point : points)
		{
			float dist = Geometry.dist(geo1, point);
			data.setIntersecting(data.isIntersecting() || dist < 0);

			if (dist > 0)
			{
				if (params.isCalculateIntersectNormal())
					data.setIntersectionNormal(new Vector3f(geo1.getNormal()));

				if (!params.isCalculateIntersectPoint())
					break;

				if (params.isCalculateIntersectPoint())
				{
					Vector3f intersectPoint = data.getIntersectionPoint();

					intersectPoint = intersectPoint == null ? new Vector3f(point) :
							Vector3f.add(point, intersectPoint, null).scale(.5f);

					data.setIntersectionPoint(intersectPoint);
				}
			}
		}

		return data;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	private static IntersectData intersects(Plane geo0, Sphere geo1, IntersectParams params)
	{
		return intersects(geo1, geo0, params);
	}

	private static IntersectData intersects(Plane geo0, AABB geo1, IntersectParams params)
	{
		return intersects(geo1, geo0, params);
	}

	private static IntersectData intersects(Plane geo0, Plane geo1, IntersectParams params)
	{
		IntersectData data = new IntersectData();

		return data;
	}
}
/*
		Vector3f asize = new Vector3f(geo0.getExtends()).scale(2.0f);
		float dminx = (amax.x() - bmin.x());
		float dmaxx = (bmax.x() - amin.x());
		float dminy = (amax.y() - bmin.y());
		float dmaxy = (bmax.y() - amin.y());
		float dminz = (amax.z() - bmin.z());
		float dmaxz = (bmax.z() - amin.z());

				Vector3f c0 = geo0.getCenter();
		Vector3f c1 = geo1.getCenter();
		Vector3f e0 = geo0.getExtends();
		Vector3f e1 = geo1.getExtends();
		Vector3f cs = Vector3f.sub(c0, c1, null);

		boolean xin = (Math.abs(cs.x()) <= (e0.x() + e1.x()));
		boolean yin = (Math.abs(cs.y()) <= (e0.y() + e1.y()));
		boolean zin = (Math.abs(cs.z()) <= (e0.z() + e1.z()));

		data.setIntersecting(xin && yin && zin);

		if (data.isIntersecting())
		{
			if (params.isCalculateIntersectPoint())
			{
				Vector3f p = new Vector3f((cs.x() >= 0 ? -1 : 1), (cs.y() >= 0 ? -1 : 1), (cs.z() >= 0 ? -1 : 1));
				p.multElements(geo0.getExtends()).add(geo0.getCenter());
				Vector3f cp = Geometry.pointOn(geo1, geo0, p);
				// make better by keeping track of closest face

				if (params.isCalculateIntersectNormal())
				{
					float mx0 = (bmin.x() - amax.x());
					float mx1 = (bmax.x() - amin.x());
					float my0 = (bmin.y() - amax.y());
					float my1 = (bmax.y() - amin.y());
					float mz0 = (bmin.z() - amax.z());
					float mz1 = (bmax.z() - amin.z());
					mx0 = mx0 > mx1 ? mx0 : mx1;
					my0 = my0 > my1 ? my0 : my1;
					mz0 = mz0 > mz1 ? mz0 : mz1;

					Vector3f n = new Vector3f();

					if(mx0 < my0 && mx0 < mz0)
						n.setX((cs.x()));
					if(my0 < mx0 && my0 < mz0)
						n.setY((cs.y() > 0 ? -1 : 1));
					if(mz0 < mx0 && mz0 < my0)
						n.setZ((cs.z() > 0 ? -1 : 1));
					System.out.println(cs);

					data.setIntersectionNormal(n);
				}
			}
		}
		*/
