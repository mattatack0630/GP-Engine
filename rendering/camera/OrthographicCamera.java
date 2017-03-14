package rendering.camera;

import utils.math.geom.*;
import utils.math.linear.LinearAlgebra;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 2/13/2017.
 */
public class OrthographicCamera extends Camera
{
	private float width;
	private float height;
	private float length;
	private AABB clippingBounds;
	private Vector3f[] frustumPoints;

	public OrthographicCamera(float width, float height, float length)
	{
		this.position = new Vector3f();
		this.rotation = new Vector3f();

		this.width = width;
		this.height = height;
		this.length = length;
		this.farPlane = length;
		this.nearPlane = 0.0f;

		this.projectionMatrix = MatrixGenerator.genOrthoMatrix(this.width, this.height, this.length, null);

		this.logRange = (float) (1.0f / Math.log(0.0f + length + 1));
		this.clippingBounds = new AABBmm(new Vector3f(), new Vector3f(width, height, length));
		this.frustumPoints = new Vector3f[8];
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

		Vector3f uScaled = new Vector3f(up).scale(height);
		Vector3f rScaled = new Vector3f(right).scale(width);
		Vector3f fScaled = new Vector3f(forward).scale(width);

		frustumPoints[0] = new Vector3f(position).add(uScaled).add(rScaled);
		frustumPoints[1] = new Vector3f(position).add(uScaled).sub(rScaled);
		frustumPoints[2] = new Vector3f(position).sub(uScaled).add(rScaled);
		frustumPoints[3] = new Vector3f(position).sub(uScaled).sub(rScaled);
		frustumPoints[4] = new Vector3f(position).add(uScaled).add(rScaled).add(fScaled);
		frustumPoints[5] = new Vector3f(position).add(uScaled).sub(rScaled).add(fScaled);
		frustumPoints[6] = new Vector3f(position).sub(uScaled).add(rScaled).add(fScaled);
		frustumPoints[7] = new Vector3f(position).sub(uScaled).sub(rScaled).add(fScaled);

		clippingBounds.setMin(frustumPoints[0]);
		clippingBounds.setMax(frustumPoints[0]);

		for (int i = 0; i < frustumPoints.length; i++)
			clippingBounds.checkBoundsUpdate(frustumPoints[i]);

		viewMatrix = MatrixGenerator.genViewMatrix(this);
	}

	@Override
	public boolean inView(AABB aabb)
	{
		IntersectData data = IntersectMath.intersects(aabb, clippingBounds, IntersectParams.CALC_INTERSECT_ONLY);
		return data.isIntersecting();
	}

	@Override
	public Vector3f[] getFrustPoints()
	{
		return frustumPoints;
	}
}
