package input;

import org.lwjgl.input.Mouse;
import rendering.camera.Camera;
import utils.math.Maths;
import utils.math.linear.LinearAlgebra;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 11/18/2016.
 */
public class MouseRay
{
	private Vector3f mouseRay;

	public MouseRay()
	{
		mouseRay = new Vector3f();
	}

	public Vector3f calculateRay(Camera c)
	{
		Vector2f viewPortSpace = new Vector2f(Mouse.getX(), Mouse.getY());
		Vector2f normalDeviceSpace = Maths.glCoordsFromPixle(viewPortSpace);
		Vector4f eyeSpace = toEyeSpace(c, normalDeviceSpace);
		Vector3f worldSpace = toWorldSpace(c, eyeSpace);
		mouseRay = worldSpace;
		return worldSpace;
	}

	public Vector4f toEyeSpace(Camera c, Vector2f nDeviceSpace)
	{
		Matrix4f invertedProjection = Matrix4f.invert(c.getProjection(), null);
		Vector4f eyeCoords = LinearAlgebra.mult(invertedProjection, new Vector4f(nDeviceSpace.x(), nDeviceSpace.y(), -1, 1), null);
		return new Vector4f(eyeCoords.x(), eyeCoords.y(), -1f, 0f);
	}

	// recalculated view, change later
	private Vector3f toWorldSpace(Camera c, Vector4f eyeCoords)
	{
		Matrix4f invertedView = Matrix4f.invert(MatrixGenerator.genViewMatrix(c), null);
		Vector4f rayWorld = LinearAlgebra.mult(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x(), rayWorld.y(), rayWorld.z());
		mouseRay.normalize();
		return mouseRay;
	}

	public Vector3f getRay()
	{
		return mouseRay;//calculateRay();//mouseRay;
	}

	public Vector3f getRayScaled(float s)
	{
		return new Vector3f(mouseRay).scale(s);
	}
}
