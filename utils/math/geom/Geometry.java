package utils.math.geom;


import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/7/2016.
 */
public class Geometry
{
	public static Plane planeFromPoints(Vector3f p0, Vector3f p1, Vector3f p2)
	{
		Vector3f s0 = Vector3f.sub(p1, p0, null);
		Vector3f s1 = Vector3f.sub(p2, p0, null);
		Vector3f normal = Vector3f.cross(s1, s0, null);
		normal.normalize();
		float distance = (normal.x() * p0.x()) + (normal.y() * p0.y()) + (normal.z() * p0.z());
		return new Plane(normal, distance);
	}

	public static float dist(Plane p0, Vector3f point)
	{
		return (Vector3f.dot(p0.getNormal(), point) - p0.getHeight());
	}

	public static Vector3f pointOn(AABBmm aabb, Vector3f point)
	{
		Vector3f max = aabb.getMax();
		Vector3f min = aabb.getMin();
		Vector3f center = aabb.getCenter();

		Vector3f[] testPoints = new Vector3f[6];
		testPoints[0] = new Vector3f(point.x(), point.y(), min.z());
		testPoints[1] = new Vector3f(point.x(), point.y(), max.z());
		testPoints[2] = new Vector3f(point.x(), min.y(), point.z());
		testPoints[3] = new Vector3f(point.x(), max.y(), point.z());
		testPoints[4] = new Vector3f(min.x(), point.y(), point.z());
		testPoints[5] = new Vector3f(max.x(), point.y(), point.z());

		float leastDist = Float.MAX_VALUE;
		Vector3f leastVec = null;

		for (Vector3f tp : testPoints)
		{
			float dist = Vector3f.sub(tp, point, null).lengthSquared();
			if (dist < leastDist)
			{
				leastVec = tp;
				leastDist = dist;
			}

			if (dist == leastDist)
			{
				float d0 = Vector3f.sub(leastVec, center, null).lengthSquared();
				float d1 = Vector3f.sub(tp, center, null).lengthSquared();
				if (d1 > d0)
				{
					leastVec = tp;
					leastDist = dist;
				}
			}
		}


		return leastVec;
	}

	public static PolygonFace closestFace(Polygon polygon, Vector3f point)
	{
		PolygonFace closestFace = null;
		float leastDist = Float.MAX_VALUE;

		for (PolygonFace f : polygon.getFaces())
		{
			float d = dist(f.getPlane(), point);
			if (d < leastDist)
			{
				leastDist = d;
				closestFace = f;
			}
		}

		return closestFace;
	}

	public static Polygon booleanAND(Polygon clippingPoly, Polygon subjectPoly)
	{
		// Create a copy of the subject polygon to use for clipping
		List<Plane> testPlanes = clippingPoly.getPlanes();
		Polygon newSubject = new Polygon(subjectPoly);

		// for each face in the clipping polygon
		for (Plane clippingPlane : testPlanes)
			clipPoly(newSubject, clippingPlane);

		return newSubject;
	}

	public static Polygon clipPoly(Polygon subjectPoly, Plane clippingPlane)
	{
		//create a clipping plane and clip each side of the subject polygon
		PolygonFace topFace = new PolygonFace(new ArrayList<>());

		// for each face in the subject polygon
		for (PolygonFace subjectFace : subjectPoly.getFaces())
		{
			PolygonEdge topEdge = new PolygonEdge(null, null);
			List<PolygonEdge> edges = subjectFace.getEdges();

			// For each edge in this face (backwards)
			for (int i = edges.size() - 1; i >= 0; i--)
			{
				PolygonEdge subjectEdge = edges.get(i);
				boolean d0 = dist(clippingPlane, subjectEdge.v0) < 0;
				boolean d1 = dist(clippingPlane, subjectEdge.v1) < 0;

				// if one vertex is over the plane
				if (d0 || d1)
				{
					edges.remove(subjectEdge);

					if (d0 == !d1)
					{
						PolygonEdge newEdge = new PolygonEdge(subjectEdge);
						newEdge.v0 = d0 ? planeIntersect(subjectEdge, clippingPlane) : newEdge.v0;
						newEdge.v1 = d1 ? planeIntersect(subjectEdge, clippingPlane) : newEdge.v1;
						edges.add(newEdge);
						topEdge.add((d0 ? newEdge.v0 : newEdge.v1));
					}
				}

				// Add topEdge to this face and the top face, if there is one
				if (topEdge.v0 != null && topEdge.v1 != null)
				{
					edges.add(topEdge);
					topFace.getEdges().add(topEdge);
				}
			}
		}

		// Add topFace to the end polygon
		if (topFace.getEdges().size() > 0)
			subjectPoly.getFaces().add(topFace);

		return subjectPoly;
	}

	public static Vector3f planeIntersect(PolygonEdge subjectEdge, Plane plane)
	{
		Vector3f ba = Vector3f.sub(subjectEdge.v1, subjectEdge.v0, null).add(Vector3f.SMAll_OFFSET);

		float nDotA = Vector3f.dot(plane.getNormal(), subjectEdge.v0);
		float nDotBA = Vector3f.dot(plane.getNormal(), ba);

		if (nDotBA == 0)
			return null;

		Vector3f vec = ba.scale((plane.getHeight() - nDotA) / nDotBA);

		return Vector3f.add(subjectEdge.v0, vec, null);
	}
}