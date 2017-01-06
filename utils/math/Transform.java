package utils.math;

import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Quaternion;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 10/29/2016.
 * <p>
 * Transform hold information about spacial transformations
 */
public class Transform
{
	// The transforms TRS matrix
	public Matrix4f matrix;

	// The transforms rotation, position, and scale
	public Rotation rotation;
	public Vector3f position;
	public Vector3f scale;

	public Transform(Vector3f position, Rotation rotation, Vector3f scale)
	{
		this.rotation = rotation;
		this.position = position;
		this.scale = scale;
		this.matrix = MatrixGenerator.genTransformMatrix(position, rotation, scale);
		//Maths.createMatrix(rotation, position, scale);
	}

	public Transform(Matrix4f mat)
	{
		Vector[] decomposed = Matrix4f.decompose(mat);
		this.rotation = new Quaternion((Vector4f) decomposed[1]);
		this.position = (Vector3f) decomposed[0];
		this.scale = (Vector3f) decomposed[2];
		this.matrix = new Matrix4f(mat);
	}

	public Transform()
	{
		this.rotation = new Quaternion();
		this.position = new Vector3f();
		this.scale = new Vector3f();
		this.matrix = new Matrix4f();
	}

	public void update()
	{
		this.matrix = MatrixGenerator.genTransformMatrix(position, rotation, scale);
	}

	public void setRotation(Rotation rotation)
	{
		this.rotation = rotation;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public void setMatrix(Matrix4f matrix)
	{
		this.matrix = matrix;
	}

	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}

	public Rotation getRotation()
	{
		return rotation;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Matrix4f getMatrix()
	{
		return matrix;
	}

	public Vector3f getScale()
	{
		return scale;
	}

}
