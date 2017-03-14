package utils.math.geom;

import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/19/2016.
 * <p>
 * The PolygonFace contains data about a polygons face,
 * specifically the faces edges and plane
 */
public class PolygonFace
{
	// Edges of this face
	private List<PolygonEdge> edges;
	// Plane of this face
	private Plane plane;

	/**
	 * Build a PolyFace from a list of edges
	 */
	public PolygonFace(List<PolygonEdge> edges)
	{
		this.edges = new ArrayList<>();
		this.edges.addAll(edges);
		this.plane = constructPlane();
	}

	/**
	 * Build a PolyFace from another source PolyFace
	 */
	public PolygonFace(PolygonFace src)
	{
		this.edges = new ArrayList<>();
		for (PolygonEdge otherEdge : src.edges)
			this.edges.add(new PolygonEdge(otherEdge));
		this.plane = constructPlane();
	}

	/**
	 * Calculate and setElements this face's plane
	 */
	public Plane constructPlane()
	{
		List<Vector3f> points = getPoints(3);

		if (points.size() >= 3)
		{
			this.plane = Geometry.planeFromPoints(points.get(0), points.get(1), points.get(2));
			return this.plane;
		}

		return null;
	}

	/**
	 * Get a List of unique Vector points
	 *
	 * @param i the amount of unique points needed to get
	 */
	public List<Vector3f> getPoints(int i)
	{
		List<Vector3f> points = new ArrayList<>();
		for (PolygonEdge edge : edges)
		{
			if (points.size() >= i)
				break;
			if (!points.contains(edge.v0))
				points.add(edge.v0);
			if (!points.contains(edge.v1))
				points.add(edge.v1);
		}
		return points;
	}

	/**
	 * Get this face's plane (does not recalculate the plane)
	 */
	public Plane getPlane()
	{
		return plane;
	}

	/**
	 * Get this faces edges list
	 */
	public List<PolygonEdge> getEdges()
	{
		return edges;
	}

	/**
	 * Set this face's edges
	 */
	public void setEdges(List<PolygonEdge> edges)
	{
		this.edges = edges;
	}
}
