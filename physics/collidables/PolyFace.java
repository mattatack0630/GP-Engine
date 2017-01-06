package physics.collidables;

import utils.math.linear.LinearAlgebra;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by mjmcc on 9/7/2016.
 */
public class PolyFace
{
	public static final int CCW = 0;
	public static final int CW = 1;
	public int orientation;

	public Vector3f originalNormal;
	public Vector3f normal;

	public PolyVertex[] polyVertices;
	public PolyEdge[] polyEdges;

	public Plane facePlane;

	public PolyFace(Vector3f v1, Vector3f v2, Vector3f v3, int orientation)
	{
		if (orientation == CW)
			this.polyVertices = new PolyVertex[]{new PolyVertex(v1), new PolyVertex(v3), new PolyVertex(v2)};
		else
			this.polyVertices = new PolyVertex[]{new PolyVertex(v1), new PolyVertex(v2), new PolyVertex(v3)};

		this.orientation = orientation;

		// normal calculation given 3 points
		originalNormal = Vector3f.cross(Vector3f.sub(v1, v2, null), Vector3f.sub(v1, v3, null), null);

		if (orientation == CW)
			originalNormal.negate();

		originalNormal.normalize();
		normal = new Vector3f(originalNormal);

		facePlane = new Plane(normal, Vector3f.dot(normal,  v1));
	}

	public PolyFace(Vector3f normal, Vector3f... verts)
	{
		this.polyVertices = new PolyVertex[verts.length];
		this.polyEdges = new PolyEdge[verts.length];

		for(int i = 0; i < polyVertices.length; i++)
			polyVertices[i] = new PolyVertex(verts[i]);

		for(int i = 0; i < polyEdges.length; i++)
			polyEdges[i] = new PolyEdge(polyVertices[i], polyVertices[(i+1) % polyVertices.length]);

		this.orientation = CCW;

		this.originalNormal = new Vector3f(normal);
		this.originalNormal.normalize();
		this.normal = new Vector3f(originalNormal);

		facePlane = new Plane(normal, Vector3f.dot(normal,  verts[0]));
	}

	public PolyFace(Vector3f normal, ArrayList<PolyEdge> edges)
	{
		if(edges.size() == 0)
			return;

		ArrayList<PolyVertex> verts = new ArrayList<>();
		for(PolyEdge edge : edges)
		{
			if (!verts.contains(edge.vertexOne))
				verts.add(edge.vertexOne);
			if (!verts.contains(edge.vertexTwo))
				verts.add(edge.vertexTwo);
		}

		this.polyVertices = new PolyVertex[verts.size()];
		this.polyEdges =  new PolyEdge[edges.size()];

		for(int i = 0; i < polyVertices.length; i++)
			polyVertices[i] = verts.get(i);

		for(int i = 0; i < polyEdges.length; i++)
			polyEdges[i] = edges.get(i);

		this.orientation = CCW;

		this.originalNormal = new Vector3f(normal);
		this.originalNormal.normalize();
		this.normal = new Vector3f(originalNormal);

		facePlane = new Plane(normal, Vector3f.dot(normal,  verts.get(0).updatedPos));
	}

	public PolyFace(PolyFace src)
	{
		this.polyVertices = new PolyVertex[src.polyVertices.length];
		this.polyEdges = new PolyEdge[src.polyEdges.length];

		for(int i = 0; i < src.polyVertices.length; i++)
		{
			polyVertices[i] = new PolyVertex(src.polyVertices[i].originalPos);
			polyVertices[i].updatedPos = new Vector3f(src.polyVertices[i].updatedPos);
		}

		for(int i = 0; i < src.polyEdges.length; i++)
		{
			PolyEdge srcEdge = src.polyEdges[i];
			polyEdges[i] = new PolyEdge(new PolyVertex(srcEdge.vertexOne), new PolyVertex(srcEdge.vertexTwo));
		}

		this.orientation = src.orientation;

		this.originalNormal = new Vector3f(src.originalNormal);
		this.normal = new Vector3f(src.normal);

		facePlane = new Plane(new Vector3f(src.facePlane.normal), src.facePlane.height);
	}

	public void update(Matrix4f translate)
	{
		if(polyVertices.length < 0)
			return;

		// calc new edge subline
		for(PolyEdge edge : polyEdges)
			edge.calcSubLine();

		// calc new normal, removed translation
		translate.elements[3][0] = 0;
		translate.elements[3][1] = 0;
		translate.elements[3][2] = 0;
		normal = LinearAlgebra.mult(translate, originalNormal, null);
		if (normal.lengthSquared() != 0) normal.normalize();

		// calc new plane
		facePlane = new Plane(normal, Vector3f.dot(normal,  polyVertices[0].updatedPos));
	}

	public boolean containsVertex(PolyVertex v)
	{
		for(PolyVertex vertex : polyVertices)
			if(vertex.equals(v))
				return true;
		return false;
	}

	@Override
	public boolean equals(Object o){
		if(o.getClass() != this.getClass())
			return false;

		PolyFace other = (PolyFace) o;
		if(other.normal.equals(this.normal))
		{
			for (PolyVertex ov : other.polyVertices)
				if (!this.containsVertex(ov))
					return false;
			return true;
		}

		return false;
	}

	@Override
	public String toString()
	{
		String string = "\tFace "+this.hashCode()+"\n";
		for(PolyEdge edge : polyEdges)
			string+=edge;
		string+="\n";
		return string;
	}
}
