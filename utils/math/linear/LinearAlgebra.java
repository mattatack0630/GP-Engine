package utils.math.linear;

import utils.math.linear.matrix.Matrix;
import utils.math.linear.matrix.Matrix3f;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.AxisAngle;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 12/14/2016.
 * <p>
 * The Linear Algebra class is used to computer mathematical
 * problems involving Linear concepts like matrices, rotations, and vector spaces.
 */
public class LinearAlgebra
{
	// rotate by converting to axis angle
	public static Matrix4f rotateMatrix(Matrix4f src, Rotation rotation, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;

		AxisAngle aaRotation = rotation.toAxisAngle();
		Matrix4f.rotate(aaRotation.getAngleRad(), aaRotation.getAxis(), src, dest);

		return dest;
	}

	public static Vector3f mult(Matrix4f left, Vector3f right, Vector3f dest)
	{
		Vector4f v = mult(left, new Vector4f(right.x(), right.y(), right.z(), 1.0f), null);
		Vector3f v3 = new Vector3f(v.x(), v.y(), v.z());
		return v3;
	}

	public static Vector3f mult(Matrix3f left, Vector3f right, Vector3f dest)
	{
		dest = dest == null ? new Vector3f() : dest;

		float x = left.getEl(0, 0) * right.x() + left.getEl(1, 0) * right.y() + left.getEl(2, 0) * right.z();
		float y = left.getEl(0, 1) * right.x() + left.getEl(1, 1) * right.y() + left.getEl(2, 1) * right.z();
		float z = left.getEl(0, 2) * right.x() + left.getEl(1, 2) * right.y() + left.getEl(2, 2) * right.z();

		dest.set(x, y, z);
		return dest;
	}

	// make more flex later
	public static Vector4f mult(Matrix4f left, Vector4f right, Vector4f dest)
	{
		dest = dest == null ? new Vector4f() : dest;

		float x = left.getEl(0, 0) * right.x() + left.getEl(1, 0) * right.y() + left.getEl(2, 0) * right.z() + left.getEl(3, 0) * right.w();
		float y = left.getEl(0, 1) * right.x() + left.getEl(1, 1) * right.y() + left.getEl(2, 1) * right.z() + left.getEl(3, 1) * right.w();
		float z = left.getEl(0, 2) * right.x() + left.getEl(1, 2) * right.y() + left.getEl(2, 2) * right.z() + left.getEl(3, 2) * right.w();
		float w = left.getEl(0, 3) * right.x() + left.getEl(1, 3) * right.y() + left.getEl(2, 3) * right.z() + left.getEl(3, 3) * right.w();

		dest.set(x, y, z, w);
		return dest;
	}

	public static Vector3f rotateVector(Vector3f vector, Rotation rotation, Vector3f dest)
	{
		dest = dest == null ? new Vector3f() : dest;

		Matrix4f rotMat = MatrixGenerator.genRotationMatrix(rotation, null);
		Vector4f result = mult(rotMat, new Vector4f(vector.x(), vector.y(), vector.z(), 1.0f), new Vector4f());
		dest.set(result.x(), result.y(), result.z());
		return dest;
	}

	public static Vector3f convertBetweenSpaces(Matrix3f from, Matrix3f to, Vector3f point)
	{
		Matrix3f convert = MatrixGenerator.genNBTConversion(from, to, null);
		return mult(convert, point, null);
	}

	/**
	 * Error Check Methods
	 **/
	protected static boolean checkSame(Matrix m0, Matrix m1)
	{
		boolean b = true;
		if (m0.getHeight() != m1.getHeight() || m0.getWidth() != m1.getWidth())
		{
			try
			{
				throw new VectorLengthError();
			} catch (VectorLengthError vectorLengthError)
			{
				b = false;
				vectorLengthError.printStackTrace();
			}
		}
		return b;
	}

	protected static boolean checkSameRowCol(Matrix m0, Matrix m1)
	{
		boolean b = true;
		if (m0.getHeight() != m1.getWidth())
		{
			try
			{
				throw new VectorLengthError();
			} catch (VectorLengthError vectorLengthError)
			{
				b = false;
				vectorLengthError.printStackTrace();
			}
		}
		return b;
	}

	protected static boolean checkEnoughSpace(Matrix m0, int height, int width)
	{
		boolean b = true;
		if (m0.getHeight() != height || m0.getWidth() != width)
		{
			try
			{
				throw new VectorLengthError();
			} catch (VectorLengthError vectorLengthError)
			{
				b = false;
				vectorLengthError.printStackTrace();
			}
		}
		return b;
	}

}
