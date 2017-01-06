package utils.math.geom;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/19/2016.
 * <p>
 * The PolygonEdge contains data about a polygons edge
 * The edge holds two vertices (v0, v1)
 */
public class PolygonEdge
{
	// Edges Vertices
	public Vector3f v0;
	public Vector3f v1;

	/**
	 * Create a PolyEdge with two points
	 */
	public PolygonEdge(Vector3f v0, Vector3f v1)
	{
		this.v0 = v0;
		this.v1 = v1;
	}

	/**
	 * Create a PolyEdge from a source edge
	 */
	public PolygonEdge(PolygonEdge edge)
	{
		this.v0 = new Vector3f(edge.v0);
		this.v1 = new Vector3f(edge.v1);
	}

	/**
	 * Get vertex by index
	 */
	public Vector3f getVertex(int index)
	{
		if (index == 0)
			return v0;
		if (index == 1)
			return v1;

		System.err.println("Not valid vertex index");
		return null;
	}

	/**
	 * Add a vertex to a partially empty edge
	 * (used in the booleanAND algorithm)
	 */
	public void add(Vector3f v)
	{
		if (v0 == null)
			v0 = v;
		else if (v1 == null)
			v1 = v;
	}
}
