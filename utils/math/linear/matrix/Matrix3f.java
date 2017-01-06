package utils.math.linear.matrix;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Matrix3f extends Matrix
{
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

	/**
	 * Static Methods
	 **/
	public static Matrix3f negate(Matrix3f left, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		Matrix.negate(left, dest);
		return dest;
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
