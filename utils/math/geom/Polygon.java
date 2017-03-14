package utils.math.geom;

import rendering.Color;
import rendering.renderers.Gizmos3D;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/19/2016.
 * <p>
 * The Polygon class is used to represent a convex polygon
 * This class extends the geom object and can be used to detect intersections
 * and run geometric calculations
 */
public class Polygon extends GeometryObject
{
	// This Polygons faces
	List<PolygonFace> faces;

	/**
	 * Create a Polygon from a list of faces
	 */
	public Polygon(List<PolygonFace> faces)
	{
		this.faces = new ArrayList<>();
		this.faces.addAll(faces);
	}

	/**
	 * Create a Polygon from a source Polygon
	 */
	public Polygon(Polygon src)
	{
		this.faces = new ArrayList<>();
		for (PolygonFace otherFace : src.faces)
			this.faces.add(new PolygonFace(otherFace));
	}

	/**
	 * Get this polygon's face list
	 */
	public List<PolygonFace> getFaces()
	{
		return faces;
	}

	/**
	 * Get this polygons vertices (no unique value checks)
	 */
	public List<Vector3f> getVertices()
	{
		List<Vector3f> uVerts = new ArrayList<>();
		for (PolygonFace face : faces)
		{
			for (PolygonEdge edge : face.getEdges())
			{
				uVerts.add(edge.v0);
				uVerts.add(edge.v1);
			}
		}
		return uVerts;
	}

	/**
	 * Calculate and return a bounding box for this Polygon
	 */
	public AABBmm getBounds()
	{
		AABBmm bounds = new AABBmm(AABBmm.BOUNDS_MIN, AABBmm.BOUNDS_MAX);

		List<Vector3f> verts = new ArrayList<>();
		for (Vector3f vert : verts)
			bounds.checkBoundsUpdate(vert);

		return bounds;
	}

	/**
	 * Get all unique planes from this Polygons faces
	 */
	public List<Plane> getPlanes()
	{
		List<Plane> list = new ArrayList<>();

		for (PolygonFace face : faces)
			if (!list.contains(face.getPlane()))
				list.add(face.getPlane());

		return list;
	}

	/**
	 * Reconstruct and setElements this Polygon's face planes
	 */
	public void reconstructPlanes()
	{
		for (PolygonFace face : faces)
			face.constructPlane();
	}

	@Override
	public String equation()
	{
		return "";
	}

	//////////////////////////////////////////////////////////////////////////////////////
	Color r = Color.random();

	public void tempRender()
	{
		Gizmos3D.setGizmosColor(r);
		for (PolygonFace f : faces)
			for (PolygonEdge e : f.getEdges())
			{
				{
					Gizmos3D.drawLine(e.v0, e.v1, .1f);
					Gizmos3D.drawSphere(e.v0, .3f);
					Gizmos3D.drawSphere(e.v1, .3f);
				}
			}
	}
}
