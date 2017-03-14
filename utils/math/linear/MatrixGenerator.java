package utils.math.linear;

import org.lwjgl.opengl.Display;
import rendering.camera.Camera;
import utils.math.linear.matrix.Matrix3f;
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
	public static Matrix4f genTransformMatrix(Vector3f translation, Rotation rotation, Vector3f scale, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;
		dest.setIdentity();

		// Translate
		dest.translate(translation);

		// Rotate
		AxisAngle aaRotation = rotation.toAxisAngle();
		dest.rotate(aaRotation.getAngleRad(), aaRotation.getAxis());

		// Scale
		Matrix4f.scale(dest, scale, dest);

		return dest;
	}

	// rotation mat gen
	public static Matrix4f genRotationMatrix(Rotation rotation, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;
		dest.setIdentity();

		//if(rotation instanceof Quaternion)
		//	return ((Quaternion)rotation).toRotationMatrix();

		return LinearAlgebra.rotateMatrix(dest, rotation, dest);
	}

	// perspective matrix gen
	public static Matrix4f genPerspectiveMatrix(float near, float far, float fov, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;
		dest.setIdentity();

		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(fov / 2f)));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far - near;

		dest.elements[0][0] = x_scale;
		dest.elements[1][1] = y_scale;
		dest.elements[2][2] = -((far + near) / frustum_length);
		dest.elements[2][3] = -1;
		dest.elements[3][2] = -((2 * near * far) / frustum_length);
		dest.elements[3][3] = 0;

		return dest;
	}

	//orthogonal matrix gen
	public static Matrix4f genOrthoMatrix(float width, float height, float length, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;
		dest.setZero();

		dest.setIdentity();
		dest.setElement(0, 0, 2.0f / width);
		dest.setElement(1, 1, 2.0f / height);
		dest.setElement(2, 2, -2.0f / length);

		return dest;
	}

	// view matrix gen
	public static Matrix4f genViewMatrix(Camera c)
	{
		return genViewMatrix(c.getPosition(), c.getRotation(), null);
	}

	public static Matrix4f genViewMatrix(Vector3f pos, Vector3f rotation, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;
		dest.setIdentity();

		Matrix4f.rotate(rotation.x(), new Vector3f(1, 0, 0), dest, dest);
		Matrix4f.rotate(rotation.y(), new Vector3f(0, 1, 0), dest, dest);

		Vector3f negativeCameraPos = new Vector3f(pos).negate();
		dest = Matrix4f.translate(dest, negativeCameraPos, dest);

		return dest;
	}

	// NBT matrix
	public static Matrix3f genNBTMatrix(Vector3f normal, Vector3f tangent, Vector3f bitangent, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		dest.setZero();

		dest.setRow(0, normal.getElements());
		dest.setRow(1, tangent.getElements());
		dest.setRow(2, bitangent.getElements());

		return dest;
	}

	public static Matrix3f genNBTConversion(Matrix3f nbt0, Matrix3f nbt1, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		dest.setZero();

		Matrix3f.mult(nbt0, Matrix3f.invert(nbt1, null), dest);

		return dest;
	}
}
