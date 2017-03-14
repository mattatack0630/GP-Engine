package utils.math.linear.matrix;

import utils.math.linear.MatrixGenerator;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Matrix3f extends Matrix
{
	public static final Matrix3f CARTESIAN_NBT = MatrixGenerator.genNBTMatrix(Vector3f.RIGHT, Vector3f.UP, Vector3f.FORWARD, null);

	/**
	 * Constructors
	 */
	public Matrix3f()
	{
		super(3, 3);
	}

	public Matrix3f(Matrix3f src)
	{
		super(src);
	}

	/**
	 * Mat3 Specific Methods
	 */
	public Matrix3f setIdentity()
	{
		for (int i = 0; i < width; i++)
			elements[i][i] = 1.0f;
		return this;
	}

	/**
	 * Casting Methods
	 */
	public Matrix3f setZero()
	{
		return (Matrix3f) super.setZero();
	}

	public Matrix3f add(Matrix other)
	{
		return (Matrix3f) super.add(other);
	}

	public Matrix3f sub(Matrix other)
	{
		return (Matrix3f) super.sub(other);
	}

	public Matrix3f mult(Matrix other)
	{
		return (Matrix3f) super.mult(other);
	}

	public Matrix3f scale(float scale)
	{
		return (Matrix3f) super.scale(scale);
	}

	public float determinant()
	{
		float f = elements[0][0] * (elements[1][1] * elements[2][2] - elements[1][2] * elements[2][1]) + elements[0][1] * (elements[1][2] * elements[2][0] - elements[1][0] * elements[2][2]) + elements[0][2] * (elements[1][0] * elements[2][1] - elements[1][1] * elements[2][0]);
		return f;
	}

	/**
	 * Static Methods
	 **/
	public static Matrix3f negate(Matrix3f left, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		Matrix.negate(left, dest);
		return dest;
	}

	public static Matrix3f invert(Matrix3f src, Matrix3f dest)
	{
		float determinant = src.determinant();

		if (determinant != 0.0F)
		{
			if (dest == null)
			{
				dest = new Matrix3f();
			}

			float determinant_inv = 1.0F / determinant;
			float t00 = src.elements[1][1] * src.elements[2][2] - src.elements[1][2] * src.elements[2][1];
			float t01 = -src.elements[1][0] * src.elements[2][2] + src.elements[1][2] * src.elements[2][0];
			float t02 = src.elements[1][0] * src.elements[2][1] - src.elements[1][1] * src.elements[2][0];
			float t10 = -src.elements[0][1] * src.elements[2][2] + src.elements[0][2] * src.elements[2][1];
			float t11 = src.elements[0][0] * src.elements[2][2] - src.elements[0][2] * src.elements[2][0];
			float t12 = -src.elements[0][0] * src.elements[2][1] + src.elements[0][1] * src.elements[2][0];
			float t20 = src.elements[0][1] * src.elements[1][2] - src.elements[0][2] * src.elements[1][1];
			float t21 = -src.elements[0][0] * src.elements[1][2] + src.elements[0][2] * src.elements[1][0];
			float t22 = src.elements[0][0] * src.elements[1][1] - src.elements[0][1] * src.elements[1][0];
			dest.elements[0][0] = t00 * determinant_inv;
			dest.elements[1][1] = t11 * determinant_inv;
			dest.elements[2][2] = t22 * determinant_inv;
			dest.elements[0][1] = t10 * determinant_inv;
			dest.elements[1][0] = t01 * determinant_inv;
			dest.elements[2][0] = t02 * determinant_inv;
			dest.elements[0][2] = t20 * determinant_inv;
			dest.elements[1][2] = t21 * determinant_inv;
			dest.elements[2][1] = t12 * determinant_inv;
			return dest;
		} else
		{
			return null;
		}
	}

	public static Matrix3f add(Matrix3f left, Matrix3f right, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		Matrix.add(left, right, dest);
		return dest;
	}

	public static Matrix3f sub(Matrix3f left, Matrix3f right, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		Matrix.sub(left, right, dest);
		return dest;
	}

	public static Matrix3f mult(Matrix3f left, Matrix3f right, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		Matrix.mult(left, right, dest);
		return dest;
	}

	public static Matrix3f scale(Matrix3f left, float s, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		scale(left, s, dest);
		return dest;
	}

	public static Matrix3f round(Matrix3f m0, int n, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		Matrix.round(m0, n, dest);
		return dest;
	}

	public static Matrix3f transpose(Matrix3f left, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		Matrix.transpose(left, dest);
		return dest;
	}
}
