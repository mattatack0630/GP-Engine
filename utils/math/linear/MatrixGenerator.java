package utils.math.linear;

import org.lwjgl.opengl.Display;
import rendering.camera.Camera;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.AxisAngle;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/15/2016.
 */
public class MatrixGenerator
{
	// Make directional later
	public static Matrix4f genTransformMatrix(Vector3f translation, Rotation rotation, Vector3f scale)
	{
		Matrix4f matrix = new Matrix4f().setIdentity();

		// Translate
		matrix.translate(translation);

		// Rotate
		AxisAngle aaRotation = rotation.toAxisAngle();
		matrix.rotate(aaRotation.getAngleRad(), aaRotation.getAxis());

		// Scale
		Matrix4f.scale(matrix, scale, matrix);

		return matrix;
	}

	// rotation mat gen
	public static Matrix4f genRotationMatrix(Rotation rotation)
	{
		Matrix4f matrix = new Matrix4f().setIdentity();
		return LinearAlgebra.rotateMatrix(matrix, rotation, matrix);
	}

	// perspective matrix gen
	public static Matrix4f genPerspectiveMatrix(float near, float far, float fov)
	{
		Matrix4f projectionMatrix = new Matrix4f();

		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(fov / 2f)));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far - near;

		projectionMatrix.elements[0][0] = x_scale;
		projectionMatrix.elements[1][1] = y_scale;
		projectionMatrix.elements[2][2] = -((far + near) / frustum_length);
		projectionMatrix.elements[2][3] = -1;
		projectionMatrix.elements[3][2] = -((2 * near * far) / frustum_length);
		projectionMatrix.elements[3][3] = 0;

		return projectionMatrix;
	}

	// view matrix gen
	public static Matrix4f genViewMatrix(Camera c)
	{
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();

		Matrix4f.rotate(c.getRotation().x(), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate(c.getRotation().y(), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);

		Vector3f negativeCameraPos = new Vector3f(-c.getPosition().x(), -c.getPosition().y(), -c.getPosition().z());
		viewMatrix = Matrix4f.translate(viewMatrix, negativeCameraPos, viewMatrix);

		return viewMatrix;
	}

}
