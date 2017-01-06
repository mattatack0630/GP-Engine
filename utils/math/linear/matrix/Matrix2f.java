package utils.math.linear.matrix;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Matrix2f extends Matrix
{
	/**
	 * Constructors
	 */
	public Matrix2f()
	{
		super(2, 2);
	}

	public Matrix2f(Matrix2f src)
	{
		super(src);
	}

	/**
	 * Mat2 Specific Methods
	 */
	public Matrix2f setIdentity()
	{
		for (int i = 0; i < width; i++)
			elements[i][i] = 1.0f;
		return this;
	}

	/**
	 * Casting Methods
	 */
	public Matrix2f setZero()
	{
		return (Matrix2f) super.setZero();
	}

	public Matrix2f add(Matrix other)
	{
		return (Matrix2f) super.add(other);
	}

	public Matrix2f sub(Matrix other)
	{
		return (Matrix2f) super.sub(other);
	}

	public Matrix2f mult(Matrix other)
	{
		return (Matrix2f) super.mult(other);
	}

	public Matrix2f scale(float scale)
	{
		return (Matrix2f) super.scale(scale);
	}

	/**
	 * Static Methods
	 **/
	public static Matrix2f add(Matrix2f left, Matrix2f right, Matrix2f dest)
	{
		dest = dest == null ? new Matrix2f() : dest;
		Matrix.add(left, right, dest);
		return dest;
	}

	public static Matrix2f sub(Matrix2f left, Matrix2f right, Matrix2f dest)
	{
		dest = dest == null ? new Matrix2f() : dest;
		Matrix.sub(left, right, dest);
		return dest;
	}

	public static Matrix2f mult(Matrix2f left, Matrix2f right, Matrix2f dest)
	{
		dest = dest == null ? new Matrix2f() : dest;
		Matrix.mult(left, right, dest);
		return dest;
	}

	public static Matrix2f transpose(Matrix2f left, float s, Matrix2f dest)
	{
		dest = dest == null ? new Matrix2f() : dest;
		scale(left, s, dest);
		return dest;
	}

	public static Matrix2f negate(Matrix2f left, Matrix2f dest)
	{
		dest = dest == null ? new Matrix2f() : dest;
		Matrix.negate(left, dest);
		return dest;
	}

	public static Matrix2f round(Matrix2f m0, int n, Matrix2f dest)
	{
		dest = dest == null ? new Matrix2f() : dest;
		Matrix.round(m0, n, dest);
		return dest;
	}

	public static Matrix2f transpose(Matrix2f left, Matrix2f dest)
	{
		dest = dest == null ? new Matrix2f() : dest;
		Matrix.transpose(left, dest);
		return dest;
	}
}
