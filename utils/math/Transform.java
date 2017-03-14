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

	// keeps track of matrix changes
	private boolean needsUpdating;

	public Transform(Vector3f position, Rotation rotation, Vector3f scale)
	{
		this.rotation = rotation;
		this.position = position;
		this.scale = scale;
		this.matrix = MatrixGenerator.genTransformMatrix(position, rotation, scale, null);
		this.needsUpdating = false;
	}

	public Transform(Matrix4f mat)
	{
		Vector[] decomposed = Matrix4f.decompose(mat);
		this.rotation = new Quaternion((Vector4f) decomposed[1]);
		this.position = (Vector3f) decomposed[0];
		this.scale = (Vector3f) decomposed[2];
		this.matrix = new Matrix4f(mat);
		this.needsUpdating = false;
	}

	public Transform()
	{
		this.rotation = new Quaternion();
		this.position = new Vector3f();
		this.scale = new Vector3f();
		this.matrix = new Matrix4f();
		this.needsUpdating = false;
	}

	public void update()
	{
		if (needsUpdating)
		{
			MatrixGenerator.genTransformMatrix(position, rotation, scale, this.matrix);
			needsUpdating = false;
		}
	}

	public void setRotation(Rotation rotation)
	{
		this.rotation = rotation;
		this.needsUpdating = true;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
		this.needsUpdating = true;
	}

	public void setScale(Vector3f scale)
	{
		this.scale = scale;
		this.needsUpdating = true;
	}

	public void setMatrix(Matrix4f matrix)
	{
		this.matrix = matrix;
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
