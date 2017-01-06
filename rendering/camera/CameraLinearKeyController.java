package rendering.camera;

import utils.math.linear.LinearAlgebra;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/1/2016.
 */
public class CameraLinearKeyController extends CameraKeyController
{
	public CameraLinearKeyController(Camera camera)
	{
		super(camera);
	}

	@Override
	public void translate(Vector3f translation)
	{
		Vector3f rot = new Vector3f(camera.getRotation());
		rot.negate();

		translation = LinearAlgebra.rotateVector(translation, new Euler(rot), null);
		//Maths.mulMatrixVector(Maths.buildRotMatrix(rot), translation);

		Vector3f.add(camera.getPosition(), translation, translation);
		camera.setPosition(translation);
	}

	@Override
	public void rotate(Vector3f rotation)
	{
		Vector3f cameraRot = camera.getRotation();
		Vector3f.add(cameraRot, rotation, cameraRot);
		camera.setRotation(cameraRot);
	}
}
