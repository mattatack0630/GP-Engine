package physics;

import physics.collidables.*;
import utils.math.Maths;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by mjmcc on 8/25/2016.
 */
public class CollisionDictionary
{
	public static CollisionData doesCollide(Collidable c1, Collidable c2)
	{
		if (c1.getClass().equals(AABB.class))
		{
			if (c2.getClass().equals(AABB.class)) return doesCollide((AABB) c1, (AABB) c2);
			if (c2.getClass().equals(Sphere.class)) return doesCollide((AABB) c1, (Sphere) c2);
			if (c2.getClass().equals(Plane.class)) return doesCollide((AABB) c1, (Plane) c2);
			if (c2.getClass().equals(Polygon.class)) return doesCollide((AABB) c1, (Polygon) c2);
		}
		if (c1.getClass().equals(Sphere.class))
		{
			if (c2.getClass().equals(AABB.class)) return doesCollide((Sphere) c1, (AABB) c2);
			if (c2.getClass().equals(Sphere.class)) return doesCollide((Sphere) c1, (Sphere) c2);
			if (c2.getClass().equals(Plane.class)) return doesCollide((Sphere) c1, (Plane) c2);
			if (c2.getClass().equals(Polygon.class)) return doesCollide((Sphere) c1, (Polygon) c2);
		}
		if (c1.getClass().equals(Plane.class))
		{
			if (c2.getClass().equals(AABB.class)) return doesCollide((Plane) c1, (AABB) c2);
			if (c2.getClass().equals(Sphere.class)) return doesCollide((Plane) c1, (Sphere) c2);
			if (c2.getClass().equals(Plane.class)) return doesCollide((Plane) c1, (Plane) c2);
			if (c2.getClass().equals(Polygon.class)) return doesCollide((Plane) c1, (Polygon) c2);
		}
		if (c1.getClass().equals(Polygon.class))
		{
			if (c2.getClass().equals(AABB.class)) return doesCollide((Polygon) c1, (AABB) c2);
			if (c2.getClass().equals(Sphere.class)) return doesCollide((Polygon) c1, (Sphere) c2);
			if (c2.getClass().equals(Plane.class)) return doesCollide((Polygon) c1, (Plane) c2);
			if (c2.getClass().equals(Polygon.class)) return doesCollide((Polygon) c1, (Polygon) c2);
		}
		return null;
	}

	/*AABB Methods*/
	public static CollisionData doesCollide(AABB a, AABB b)
	{
		boolean intersects = (a.min.x() <= b.max.x() && a.max.x() >= b.min.x()) &&
				(a.min.y() <= b.max.y() && a.max.y() >= b.min.y()) &&
				(a.min.z() <= b.max.z() && a.max.z() >= b.min.z());

		return new CollisionData(intersects);
	}

	public static CollisionData doesCollide(AABB a1, Sphere s2)
	{
		return null;
	}

	public static CollisionData doesCollide(AABB a1, Plane p2)
	{
		return null;
	}

	public static CollisionData doesCollide(AABB a1, Polygon p2)
	{
		return null;
	}

	/*Sphere Methods*/
	public static CollisionData doesCollide(Sphere s1, Sphere s2)
	{
		CollisionData data = new CollisionData(false);
		Vector3f subVec = Vector3f.sub(s1.pos, s2.pos, data.collisionNormal);

		//length() uses sqrt, maybe fix this
		boolean doesCollide = subVec.length() < (s1.radius + s2.radius);
		data.doesCollide = doesCollide;
		if (!doesCollide)
			return data;
		//Don't do extra calculations if there is no collision
		Vector3f sub = Vector3f.sub(s2.pos, s1.pos, null);
		sub.normalize();
		sub.scale(s1.radius);
		data.collisionPoint = Vector3f.add(s1.pos, sub, null);
		return data;
	}

	public static CollisionData doesCollide(Sphere s1, AABB a2)
	{
		return null;
	}

	public static CollisionData doesCollide(Sphere s1, Plane p2)
	{
		CollisionData c = new CollisionData(false);
		float dot = Vector3f.dot(p2.normal.normalize(), s1.pos) - p2.height;

		if (dot < s1.radius)
			c.doesCollide = true;

		if (!c.doesCollide)
			return c;

		Vector3f sub = new Vector3f(p2.normal);
		sub.scale(-dot);
		c.collisionPoint = Vector3f.add(sub, s1.pos, null);
		c.collisionNormal = p2.normal;

		return c;
	}

	public static CollisionData doesCollide(Sphere s1, Polygon p2)
	{
		return null;
	}

	/*Plane Methods*/
	public static CollisionData doesCollide(Plane p1, Plane p2)
	{
		return new CollisionData(false);
	}

	public static CollisionData doesCollide(Plane p1, AABB a2)
	{
		return null;
	}

	public static CollisionData doesCollide(Plane p1, Sphere s2)
	{
		CollisionData c = doesCollide(s2, p1);
		c.collisionNormal = p1.normal.negate();
		c.collisionPoint = null;
		return c;
	}

	public static CollisionData doesCollide(Polygon p2, Plane p1)
	{
		CollisionData data = new CollisionData(false);
		int cpcount = 0;
		for (PolyVertex vert : p2.polyVerts)
		{
			float dot = Vector3f.dot(p1.normal.normalize(), vert.updatedPos) - p1.height;
			if (dot <= 0)
			{
				data.doesCollide = true;
				data.collisionNormal = p1.normal;
				data.collisionPoint = Vector3f.add(data.collisionPoint, vert.updatedPos, null);
				cpcount++;
			}
		}

		if(cpcount > 0)
			data.collisionPoint.scale((float) 1/cpcount);
		return data;
	}

	/*Polygon Methods*/
	public static CollisionData doesCollide(Polygon p1, Polygon p2)
	{
		// Do AABB check first
		CollisionData inProximity = doesCollide(p1.proximityBox, p2.proximityBox);
		if(!inProximity.doesCollide)
			return inProximity;
		Polygon clipped = clipPoly(p1, p2);
		if(clipped == null || clipped.polyVerts.length == 0)
			return new CollisionData(false);

		// Get average point
		Vector3f total = new Vector3f();
		for(PolyVertex vertex : clipped.polyVerts)
			Vector3f.add(total, vertex.updatedPos, total);
		total.scale((float) 1 / clipped.polyVerts.length);

		// Find plane closest to
		Vector3f lowestNormal = null;
		float lowestDist = Float.MAX_VALUE;
		for(PolyFace f : p2.polyFaces)
		{
			float dist = Math.abs(Maths.distToPlane(total ,f.facePlane));
			if(dist < lowestDist)
			{
				lowestDist = dist;
				lowestNormal = new Vector3f(f.facePlane.normal);
			}
		}

		CollisionData data = new CollisionData(true);
		data.collisionPoint = total;
		data.collisionNormal = lowestNormal;

		return data;
	}

	public static CollisionData doesCollide(Polygon p1, AABB a2)
	{
		return null;
	}

	public static CollisionData doesCollide(Polygon p1, Sphere s2)
	{
		return null;
	}

	public static CollisionData doesCollide(Plane p1, Polygon p2)
	{
		CollisionData c = doesCollide(p2, p1);
		c.collisionPoint = null;
		return c;
	}


	public static Polygon clipPoly(Polygon p1, Polygon p2)
	{
		Polygon newPoly = new Polygon(p2);

		for(PolyFace f : p1.polyFaces)
		{
			Plane plane = f.facePlane;
			ArrayList<PolyVertex> below = new ArrayList<>();
			for(PolyVertex v : newPoly.polyVerts)
				if(Maths.distToPlane(v.updatedPos, plane) < 0)
					below.add(v);

			if(below.size() == 0)
				return null;

			ArrayList<PolyFace> saveFaces = new ArrayList<>();
			ArrayList<PolyEdge> topEdges = new ArrayList<>();

			ArrayList<PolyFace> bfaces = new ArrayList<>();
			for(PolyFace f1 : newPoly.polyFaces)
				for(PolyVertex vertex : f1.polyVertices)
					if(below.contains(vertex) && !bfaces.contains(f1))
						bfaces.add(f1);

			for(PolyFace bface : bfaces)
			{
				boolean intersects = false;
				//if all points are below plane
				for(PolyVertex point : bface.polyVertices)
					if(!below.contains(point)){
						intersects = true;
						break;
					}

				if(intersects){
					ArrayList<PolyEdge> saveEdges = new ArrayList<>();
					PolyEdge topEdge = new PolyEdge(null, null);
					for(PolyEdge edge : bface.polyEdges)
					{
						if(below.contains(edge.vertexOne) ^ below.contains(edge.vertexTwo)){

							Vector3f pointOnPlane = Maths.planeIntersect(f.facePlane, edge.vertexOne.updatedPos, edge.vertexTwo.updatedPos);
							if(pointOnPlane == null)
								continue;

							if(topEdge.vertexOne == null)
								topEdge.vertexOne = new PolyVertex(pointOnPlane);
							else
								topEdge.vertexTwo = new PolyVertex(pointOnPlane);

							if(below.contains(edge.vertexOne))
								edge.vertexTwo.updatedPos = pointOnPlane;

							if(below.contains(edge.vertexTwo))
								edge.vertexOne.updatedPos = pointOnPlane;

							saveEdges.add(edge);
						}

						if(below.contains(edge.vertexOne) && below.contains(edge.vertexTwo))
							saveEdges.add(edge);
					}

					if(topEdge.vertexOne != null && topEdge.vertexTwo != null)
					{
						saveEdges.add(new PolyEdge(topEdge.vertexOne, topEdge.vertexTwo));
						topEdges.add(new PolyEdge(topEdge.vertexOne, topEdge.vertexTwo));
					}
					if(saveEdges.size() > 0)
						saveFaces.add(new PolyFace(bface.normal, saveEdges));
				}else{
					saveFaces.add(bface);
				}
			}
			if(topEdges.size() > 0)
				saveFaces.add(new PolyFace(plane.normal, topEdges));
			newPoly = new Polygon(saveFaces);
		}
		return newPoly;
	}


}
