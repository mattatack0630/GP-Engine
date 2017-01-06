package physics.collidables;

import rendering.RenderData;
import rendering.renderers.Gizmos3D;
import rendering.renderers.MasterRenderer;
import utils.math.linear.LinearAlgebra;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by mjmcc on 8/26/2016.
 */
public class Polygon extends Collidable
{
	public PolyFace[] polyFaces;
	public PolyEdge[] polyEdges;
	public PolyVertex[] polyVerts;

	public AABB proximityBox;

	public Vector3f position;
	public Vector3f scale;
	public Vector3f rotation;

	public Polygon(ArrayList<PolyFace> faces)
	{
		super(Collidable.POLY);

		buildPoly(faces);

		this.proximityBox = new AABB(new Vector3f(), new Vector3f());

		this.position = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = new Vector3f(1, 1, 1);

		data = new RenderData(position);
	}

	public Polygon(Polygon src)
	{
		super(Collidable.POLY);

		ArrayList<PolyFace> faces = new ArrayList<>();

		for (PolyFace face : src.getFaces())
			faces.add(new PolyFace(face));

		buildPoly(faces);

		this.position = new Vector3f(src.position);
		this.rotation = new Vector3f(src.rotation);
		this.scale = new Vector3f(src.scale);

		data = new RenderData(position);
	}

	public void buildPoly(ArrayList<PolyFace> faces)
	{
		this.polyFaces = new PolyFace[faces.size()];
		for (int i = 0; i < faces.size(); i++)
			this.polyFaces[i] = faces.get(i);

		ArrayList<PolyEdge> edges = new ArrayList<>();
		for (PolyFace f : polyFaces)
			for (PolyEdge e : f.polyEdges)
				if (!edges.contains(e))
					edges.add(e);

		ArrayList<PolyVertex> vertices = new ArrayList<>();
		for (PolyFace f : polyFaces)
			for (PolyVertex v : f.polyVertices)
				vertices.add(v);

		polyEdges = new PolyEdge[edges.size()];
		for (int i = 0; i < polyEdges.length; i++)
			polyEdges[i] = edges.get(i);

		polyVerts = new PolyVertex[vertices.size()];
		for (int i = 0; i < polyVerts.length; i++)
			polyVerts[i] = vertices.get(i);
	}

	public void update(Vector3f pos, Vector3f COM, Vector3f rot)
	{
		Vector3f.add(pos, COM, position);
		rotation = new Vector3f(rot);

		Matrix4f translate = MatrixGenerator.genTransformMatrix(pos, new Euler(rot), new Vector3f(1, 1, 1));
		//Maths.createTransformationMatrix(pos, rot, new Vector3f(1,1,1));
		translate.translate(COM);
		translate.scale(scale);

		proximityBox.reset();
		for (PolyVertex vert : polyVerts)
		{
			vert.updatedPos = LinearAlgebra.mult(translate, vert.originalPos, null);
			// Reset min/max of AABB
			proximityBox.min.setX((vert.updatedPos.x() < proximityBox.min.x()) ? vert.updatedPos.x() : proximityBox.min.x());
			proximityBox.min.setY((vert.updatedPos.y() < proximityBox.min.y()) ? vert.updatedPos.y() : proximityBox.min.y());
			proximityBox.min.setZ((vert.updatedPos.z() < proximityBox.min.z()) ? vert.updatedPos.z() : proximityBox.min.z());
			proximityBox.max.setX((vert.updatedPos.x() > proximityBox.max.x()) ? vert.updatedPos.x() : proximityBox.max.x());
			proximityBox.max.setY((vert.updatedPos.y() > proximityBox.max.y()) ? vert.updatedPos.y() : proximityBox.max.y());
			proximityBox.max.setZ((vert.updatedPos.z() > proximityBox.max.z()) ? vert.updatedPos.z() : proximityBox.max.z());
		}
		Matrix4f.translate(translate, COM, translate);
		for (PolyFace f : polyFaces)
			f.update(translate);
	}

	public void setScale(Vector3f scale)
	{
		this.scale = new Vector3f(scale);
	}

	public PolyFace[] getFaces()
	{
		if (polyFaces == null || polyFaces.length == 0)
			return new PolyFace[0];

		return polyFaces;
	}

	public PolyEdge[] getEdges()
	{
		if (polyEdges == null || polyEdges.length == 0)
			return new PolyEdge[0];

		return polyEdges;
	}

	public PolyVertex[] getVertices()
	{
		if (polyVerts == null || polyVerts.length == 0)
			return new PolyVertex[0];

		return polyVerts;
	}

	public ArrayList<PolyFace> getFacesContaining(PolyVertex vertex)
	{
		ArrayList<PolyFace> faces = new ArrayList<>();

		for (PolyFace f : polyFaces)
			for (PolyVertex v : f.polyVertices)
				if (v.equals(vertex))
					faces.add(f);

		return faces;
	}

	public ArrayList<PolyEdge> getEdgesContaining(PolyVertex vertex)
	{
		ArrayList<PolyEdge> edges = new ArrayList<>();

		for (PolyFace f : polyFaces)
			for (PolyEdge e : f.polyEdges)
				if ((e.vertexOne.equals(vertex) || e.vertexTwo.equals(vertex)) && !edges.contains(e))
					edges.add(e);

		return edges;
	}

	public void tempRender(MasterRenderer renderer)
	{
		Gizmos3D.setGizmosColor(data.tempColor);

		for (PolyVertex vert : polyVerts)
			Gizmos3D.drawSphere(vert.updatedPos, 0.1f * scale.z());

		for (PolyEdge e : polyEdges)
			Gizmos3D.drawLine(e.vertexOne.updatedPos, e.vertexTwo.updatedPos, 0.025f * scale.z());
	}

	@Override
	public String toString()
	{
		String string = "Polygon " + this.hashCode() + "\n";
		for (PolyFace face : polyFaces)
			string += face;
		string += "\n";
		return string;
	}
}
