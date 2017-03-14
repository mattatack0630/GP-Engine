package rendering.camera;

import utils.math.geom.AABB;
import utils.math.linear.LinearAlgebra;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 2/13/2017.
 */
public class PerspectiveCamera extends Camera
{
	private float fov;
	private Frustum clippingFrustum;

	public PerspectiveCamera(float near, float far, float fov)
	{
		this.position = new Vector3f();
		this.rotation = new Vector3f();

		this.fov = (float) Math.toRadians(fov);
		this.nearPlane = near;
		this.farPlane = far;

		this.projectionMatrix = MatrixGenerator.genPerspectiveMatrix(near, far, fov, null);

		this.logRange = (float) (1.0f / Math.log(near + far + 1));
		this.clippingFrustum = new Frustum();
	}

	@Override
	public void update()
	{
		Matrix4f rot = new Matrix4f();
		rot.setIdentity();
		rot.rotate(rotation.y(), new Vector3f(0, -1, 0));
		rot.rotate(rotation.x(), new Vector3f(-1, 0, 0));

		up = LinearAlgebra.mult(rot, Vector3f.UP, up);
		right = LinearAlgebra.mult(rot, Vector3f.RIGHT, right);
		forward = LinearAlgebra.mult(rot, Vector3f.FORWARD, forward);

		clippingFrustum.update(position, up, right, forward, fov, farPlane);

		viewMatrix = MatrixGenerator.genViewMatrix(this);
	}

	@Override
	public boolean inView(AABB aabb)
	{
		return clippingFrustum.inView(aabb);
	}

	@Override
	public Vector3f[] getFrustPoints()
	{
		return clippingFrustum.getPoints();
	}
}
