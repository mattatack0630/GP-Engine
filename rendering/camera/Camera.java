package rendering.camera;

import utils.math.linear.LinearAlgebra;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

public class Camera
{
	private static float SPEED = .15f;
	private static float TURN_SPEED = .15f;

	private Matrix4f projectionMatrix;
	public Frustum clippingFrustum;
	public float near;
	public float far;
	public float fov;

	private Vector3f position;
	private Vector3f rotation;

	private float logRange;

	public Camera(Vector3f position, Vector3f rotation, float near, float far, float fov)
	{
		this.position = position;
		this.rotation = rotation;

		this.projectionMatrix = MatrixGenerator.genPerspectiveMatrix(near, far, (float) Math.toRadians(fov));
		//Maths.createPerspectiveMatrix(near, far, fov);

		this.fov = (float) Math.toRadians(fov);
		this.near = near;
		this.far = far;

		this.logRange = (float) (1.0f / Math.log(near + far + 1));
		this.clippingFrustum = new Frustum();
	}

	public void update()
	{
		Matrix4f rot = new Matrix4f();
		rot.setIdentity();
		rot.rotate(rotation.y(), new Vector3f(0, -1, 0));
		rot.rotate(rotation.x(), new Vector3f(-1, 0, 0));

		Vector3f up = LinearAlgebra.mult(rot, Vector3f.UP, null);
		Vector3f right = LinearAlgebra.mult(rot, Vector3f.RIGHT, null);
		Vector3f forward = LinearAlgebra.mult(rot, Vector3f.FORWARD, null);

		/*Frustum*/
		clippingFrustum.update(position, up, right, forward, fov, far);
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public Matrix4f getProjection()
	{
		return projectionMatrix;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public void setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
	}

	public float getNear()
	{
		return near;
	}

	public float getFar()
	{
		return far;
	}

	// Used in picking algorithm
	public float getLogRange()
	{
		return logRange;
	}

	public Frustum getFrustum()
	{
		return clippingFrustum;
	}
}
