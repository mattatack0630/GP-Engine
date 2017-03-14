package shadows;

import rendering.Light;
import rendering.camera.Camera;
import utils.math.geom.AABBmm;
import utils.math.linear.LinearAlgebra;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix3f;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 1/14/2017.
 */
public class ShadowBox
{
	private static final float SHADOW_BACK_AMOUNT = 40;
	private static final float SHADOW_DIST = 200;

	private float shadowDistance;
	private Matrix3f lightSpaceNBT;
	private Matrix4f lightOrtho;
	private Matrix4f lightView;
	private AABBmm frustumBounds;
	private Euler lightRotation;

	public ShadowBox()
	{
		this.shadowDistance = SHADOW_DIST;
		this.frustumBounds = new AABBmm(AABBmm.BOUNDS_MIN, AABBmm.BOUNDS_MAX);
		this.lightSpaceNBT = new Matrix3f().setIdentity();
		this.lightOrtho = new Matrix4f().setIdentity();
		this.lightView = new Matrix4f().setIdentity();
		this.lightRotation = new Euler();

	}

	public void update(Camera camera, Light light)
	{
		updateLightSpaceNBT(light);
		updateLightSpaceBounds(camera);

		Vector3f center = frustumBounds.getCenter();
		Vector3f dimensions = frustumBounds.getExtends().scale(2.0f);
		center = LinearAlgebra.convertBetweenSpaces(lightSpaceNBT, Matrix3f.CARTESIAN_NBT, center);

		MatrixGenerator.genOrthoMatrix(dimensions.x(), dimensions.y(), dimensions.z(), lightOrtho);
		MatrixGenerator.genViewMatrix(center.add(new Vector3f(0, 0, 0)), lightRotation.asVector(), lightView);
	}

	public void updateLightSpaceNBT(Light light)
	{

		Vector3f lightDir = new Vector3f(light.getPosition()).negate();
		lightRotation = Euler.fromDirVector(lightDir, null);
		Matrix4f lightRotMat = MatrixGenerator.genRotationMatrix(lightRotation, null);

		Vector3f up = LinearAlgebra.mult(lightRotMat, Vector3f.UP, null);
		Vector3f right = LinearAlgebra.mult(lightRotMat, Vector3f.RIGHT, null);
		Vector3f forward = LinearAlgebra.mult(lightRotMat, Vector3f.BACKWARD, null);

		MatrixGenerator.genNBTMatrix(right, up, forward, lightSpaceNBT);
	}

	public AABBmm updateLightSpaceBounds(Camera camera)
	{
		Vector3f[] nPoints = camera.getFrustPoints();
		Vector3f[] fPoints = new Vector3f[8];

		frustumBounds.setMax(AABBmm.BOUNDS_MAX);
		frustumBounds.setMin(AABBmm.BOUNDS_MIN);

		for (int i = 0; i < fPoints.length; i++)
		{
			fPoints[i] = new Vector3f(nPoints[(i % 4) + 1]);
			fPoints[i].scale(i < 4 ? 0.1f : shadowDistance); // add near plane later (0.1)
			fPoints[i].add(nPoints[0]);
			fPoints[i] = LinearAlgebra.convertBetweenSpaces(Matrix3f.CARTESIAN_NBT, lightSpaceNBT, fPoints[i]);
			frustumBounds.checkBoundsUpdate(fPoints[i]);
		}

		frustumBounds.getMax().add(new Vector3f(0, 0, SHADOW_BACK_AMOUNT));
		frustumBounds.getMin().add(new Vector3f(0, 0, -SHADOW_BACK_AMOUNT));

		return frustumBounds;
	}

	public Matrix4f getViewMatrix()
	{
		return lightView;
	}

	public Matrix4f getOrthoMatrix()
	{
		return lightOrtho;
	}

}
