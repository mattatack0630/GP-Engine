package models;

import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 9/29/2016.
 */
public class Vertex
{
	private Vector3f position;
	private Vector3f normal;
	private Vector3f tangent;
	private Vector2f textureCoord;

	public Vertex(Vector3f position)
	{
		this.position = position;
		this.normal = new Vector3f();
		this.tangent = new Vector3f();
		this.textureCoord = new Vector2f();
	}

	public void setTextureCoord(Vector2f t)
	{
		this.textureCoord = t;
	}

	public void addNormal(Vector3f normal)
	{
		Vector3f.add(this.normal, normal, this.normal);
		this.normal.normalize();
	}

	public void averageNormals()
	{

		//normal.scale(1.0f / normalCount);
		//normalCount = 0;
	}

	public void addTangents(Vector3f t)
	{
		Vector3f.add(this.tangent, t, this.tangent);
		this.tangent.normalize();
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getNormal()
	{
		return normal;
	}

	public Vector3f getTangent()
	{
		return tangent;
	}

	public Vector2f getTextureCoord()
	{
		return textureCoord;
	}
}