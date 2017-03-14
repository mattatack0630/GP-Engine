package rendering.camera;

import utils.math.geom.AABB;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

public abstract class Camera
{
	public Matrix4f projectionMatrix;
	public Matrix4f viewMatrix;

	protected float nearPlane;
	protected float farPlane;

	protected Vector3f position;
	protected Vector3f rotation;

	protected Vector3f up;
	protected Vector3f right;
	protected Vector3f forward;

	protected float logRange;

	public abstract void update();

	public abstract boolean inView(AABB aabb);

	public abstract Vector3f[] getFrustPoints();

	public float getLogRange()
	{
		// Used in picking algorithm
		return logRange;
	}

	public Vector3f getUp()
	{
		return up;
	}

	public Vector3f getRight()
	{
		return right;
	}

	public Vector3f getForward()
	{
		return forward;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public Matrix4f getViewMatrix()
	{
		return viewMatrix;
	}

	public Matrix4f getProjection()
	{
		return projectionMatrix;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public void setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
	}

	public float getFarPlane()
	{
		return farPlane;
	}

	public float getNearPlane()
	{
		return nearPlane;
	}

/*	private Frustum clippingFrustum;
	public Matrix4f projectionMatrix;
	public Matrix4f viewMatrix;
	public float near;
	public float far;
	public float fov;

	private Vector3f position;
	private Vector3f rotation;

	private Vector3f up;
	private Vector3f right;
	private Vector3f forward;

	private float logRange;

	public Camera(Vector3f position, Vector3f rotation, float near, float far, float fov)
	{
		this.position = position;
		this.rotation = rotation;

		this.fov = (float) Math.toRadians(fov);
		this.near = near;
		this.far = far;

		this.projectionMatrix = MatrixGenerator.genPerspectiveMatrix(this.near, this.far, this.fov, null);

		this.logRange = (float) (1.0f / Math.log(near + far + 1));
		this.clippingFrustum = new Frustum();
	}

	public void update()
	{
		Matrix4f rot = new Matrix4f();
		rot.setIdentity();
		rot.rotate(rotation.y(), new Vector3f(0, -1, 0));
		rot.rotate(rotation.x(), new Vector3f(-1, 0, 0));

		up = LinearAlgebra.mult(rot, Vector3f.UP, up);
		right = LinearAlgebra.mult(rot, Vector3f.RIGHT, right);
		forward = LinearAlgebra.mult(rot, Vector3f.FORWARD, forward);

		/*Frustum*/
		/*clippingFrustum.update(position, up, right, forward, fov, far);

		viewMatrix = MatrixGenerator.genViewMatrix(this);
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public Matrix4f getProjection()
	{
		return projectionMatrix;
	}

	public Matrix4f getViewMatrix()
	{
		return viewMatrix;
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

	public float getLogRange()
	{
		// Used in picking algorithm
		return logRange;
	}

	public Frustum getFrustum()
	{
		return clippingFrustum;
	}

	public Vector3f getUp()
	{
		return up;
	}

	public Vector3f getRight()
	{
		return right;
	}

	public Vector3f getForward()
	{
		return forward;
	}*/
}
