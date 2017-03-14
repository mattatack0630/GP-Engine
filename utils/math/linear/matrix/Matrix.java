package utils.math.linear.matrix;

import utils.math.Maths;
import utils.math.linear.VectorLengthError;

import java.nio.FloatBuffer;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Matrix
{
	public float[][] elements;
	public int width, height;

	/**
	 * Constructors
	 */
	public Matrix(float[][] elements, int w, int h)
	{
		this.elements = elements;
		this.width = w;
		this.height = h;
	}

	public Matrix(int w, int h)
	{
		this.elements = new float[w][h];
		this.width = w;
		this.height = h;
		setZero();
	}

	public Matrix(Matrix src)
	{
		this(src.width, src.height);
		load(src.elements, width, height);
	}

	/**
	 * Matrix Instance Methods
	 */
	protected void load(float[][] f, int w, int h)
	{
		if (width == w && height == h && f.length == w && f[0].length == h)
			for (int i = 0; i < w; i++)
				for (int j = 0; j < h; j++)
					elements[i][j] = f[i][j];
	}

	public float[] col(int c)
	{
		float[] col = new float[height];
		for (int i = 0; i < col.length; i++)
			col[i] = elements[c][i];
		return col;
	}

	public float[] row(int r)
	{
		float[] row = new float[width];
		for (int i = 0; i < row.length; i++)
			row[i] = elements[i][r];
		return row;
	}


	public void setElement(int x, int y, float v)
	{
		if (width >= x && height >= y)
			elements[x][y] = v;
	}

	// up -> down
	public void setCol(int c, float[] data)
	{
		if (data.length == height)
			for (int i = 0; i < height; i++)
				elements[c][i] = data[i];
	}

	// Left -> right
	public void setRow(int r, float[] data)
	{
		if (data.length == width)
			for (int i = 0; i < width; i++)
				elements[i][r] = data[i];
	}

	public Matrix setZero()
	{
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				elements[i][j] = 0.0f;
		return this;
	}

	public Matrix add(Matrix other)
	{
		Matrix.add(this, other, this);
		return this;
	}

	public Matrix sub(Matrix other)
	{
		Matrix.sub(this, other, this);
		return this;
	}

	public Matrix mult(Matrix other)
	{
		Matrix.mult(this, other, this);
		return this;
	}

	public Matrix scale(float scale)
	{
		Matrix.scale(this, scale, this);
		return this;
	}

	public float[][] getElements()
	{
		return elements;
	}

	public float getEl(int x, int y)
	{
		return elements[x][y];
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	/**
	 * Overridden Object Methods
	 */
	@Override
	public String toString()
	{
		String s = "";

		for (int i = 0; i < height; i++)
		{
			s += "[";
			for (int j = 0; j < width; j++)
			{
				s += elements[j][i] + (j == width - 1 ? "]\n" : ", ");
			}
		}

		return s;
	}

	@Override
	public boolean equals(Object o)
	{
		boolean b = false;
		if (o instanceof Matrix)
		{
			b = true;
			Matrix m = (Matrix) o;
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++)
					b = (elements[i][j] == m.elements[i][j]) && b;
		}
		return b;
	}

	/**
	 * Error Check Methods
	 **/
	protected static boolean checkSame(Matrix m0, Matrix m1)
	{
		boolean b = true;
		if (m0.height != m1.height || m0.width != m1.width)
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
		if (m0.height != m1.width)
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
		if (m0.height != height || m0.width != width)
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

	/**
	 * Static Methods
	 **/
	public static Matrix add(Matrix left, Matrix right, Matrix dest)
	{
		Matrix m = new Matrix(left.width, left.height);

		checkSame(left, right);
		checkEnoughSpace(dest, m.height, m.width);
		// check left size == right size and that the dest has right dimensions

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = left.elements[i][j] + right.elements[i][j];

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return m;
	}

	public static Matrix sub(Matrix left, Matrix right, Matrix dest)
	{
		Matrix m = new Matrix(left.width, left.height);

		checkSame(left, right);
		checkEnoughSpace(dest, m.height, m.width);
		// check left size == right size and that the dest has right dimensions

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				m.elements[i][j] = left.elements[i][j] - right.elements[i][j];

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return m;
	}

	public static Matrix mult(Matrix left, Matrix right, Matrix dest)
	{
		Matrix m = new Matrix(left.width, right.height);

		checkSameRowCol(left, right);
		checkEnoughSpace(dest, m.height, m.width);
		// check cols == rows and that the dest has right dimensions

		for (int i = 0; i < m.width; i++)
		{
			for (int j = 0; j < m.height; j++)
			{
				float[] r = left.row(j);
				float[] c = right.col(i);

				for (int k = 0; k < r.length; k++)
					m.elements[i][j] += (r[k] * c[k]);
			}
		}

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return m;
	}

	public static Matrix transpose(Matrix left, Matrix dest)
	{
		Matrix m = new Matrix(left.height, left.width);

		checkEnoughSpace(dest, m.height, m.width);

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = left.elements[j][i];

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return m;
	}

	public static Matrix negate(Matrix left, Matrix dest)
	{
		Matrix m = new Matrix(left.width, left.height);

		checkEnoughSpace(dest, left.height, left.width);

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = -1.0f * left.elements[i][j];

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return m;
	}

	public static Matrix scale(Matrix left, float s, Matrix dest)
	{
		Matrix m = new Matrix(left.width, left.height);

		checkEnoughSpace(dest, left.height, left.width);

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = left.elements[i][j] * s;

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return m;
	}

	public static Matrix round(Matrix left, int n, Matrix dest)
	{
		Matrix m = new Matrix(left.width, left.height);

		checkEnoughSpace(dest, left.height, left.width);

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = Maths.round(left.elements[i][j], n);

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return m;
	}

	public static void store(Matrix matrix, FloatBuffer matrixBuffer)
	{
		for (int i = 0; i < matrix.width; i++)
			for (int j = 0; j < matrix.height; j++)
				matrixBuffer.put(matrix.elements[i][j]);
	}

	public static int store(Matrix matrix, float[] matrixBuffer, int off)
	{
		for (int i = 0; i < matrix.width; i++)
			for (int j = 0; j < matrix.height; j++)
				matrixBuffer[off++] = matrix.elements[i][j];

		return off;
	}


}
