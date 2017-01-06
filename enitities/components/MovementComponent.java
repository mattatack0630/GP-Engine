package enitities.components;

import enitities.Entity;
import utils.math.Transform;
import utils.math.linear.LinearAlgebra;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 11/24/2016.
 */
public class MovementComponent extends EntityComponent
{
	public Vector3f position;
	public Vector3f rotation;
	public Transform transform;

	public MovementComponent(Entity parent)
	{
		super(parent);
		position = new Vector3f();
		rotation = new Vector3f();
	}

	public void setRotation(Vector3f r)
	{
		rotation = r;
	}

	public void setTranslation(Vector3f t)
	{
		position = t;
	}

	public void translate(Vector3f t)
	{
		Vector3f.add(position, t, position);
	}

	public void rotate(Vector3f r)
	{
		Vector3f.add(rotation, r, rotation);
	}

	public void translate(Vector3f startVec, float d)
	{
		Vector3f v = new Vector3f(startVec);

		Matrix4f r = new Matrix4f();
		r.setIdentity();
		Matrix4f.rotate(rotation.z(), new Vector3f(0, 0, 1), r, r);
		Matrix4f.rotate(rotation.y(), new Vector3f(0, 1, 0), r, r);
		Matrix4f.rotate(rotation.x(), new Vector3f(1, 0, 0), r, r);

		v = LinearAlgebra.mult(r, v, null);
		v.normalize();
		v.scale(d);

		Vector3f.add(position, v, position);
	}

	public Matrix4f getMatrix()
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		Matrix4f.rotate(rotation.z(), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate(rotation.y(), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate(rotation.x(), new Vector3f(1, 0, 0), matrix, matrix);

		Matrix4f.translate(matrix, position, matrix);

		return matrix;
	}
}
