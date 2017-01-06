package utils.math.linear.vector;

import utils.math.Maths;
import utils.math.linear.VectorLengthError;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Vector
{
	private static final float SAME_TOLERANCE = 0.0001f;
	public float[] elements;

	public Vector(float[] elements)
	{
		this.elements = new float[elements.length];
		set(elements);
	}

	public Vector(float[] f, int i)
	{
		this.elements = new float[i];
		set(f);
	}

	public void set(float[] elements)
	{
		for (int i = 0; i < this.elements.length; i++)
			this.elements[i] = elements[i];
	}

	public Vector negate()
	{
		for (int i = 0; i < elements.length; i++)
			elements[i] *= -1;

		return this;
	}

	public Vector normalize()
	{
		float l = length();
		if (length() != 0)
			scale(1f / l);
		// avoid div by 0
		return this;
	}

	public Vector add(Vector other)
	{
		Vector.add(this, other, this);

		return this;
	}

	public Vector sub(Vector other)
	{
		Vector.sub(this, other, this);

		return this;
	}

	public Vector scale(float scale)
	{
		for (int i = 0; i < elements.length; i++)
			elements[i] = elements[i] * scale;

		return this;
	}

	public Vector multElements(Vector other)
	{
		Vector.multElements(this, other, this);

		return this;
	}

	public Vector setMagnitude(float magnitude)
	{
		normalize();
		scale(magnitude);
		return this;
	}

	public float dot(Vector other)
	{
		return Vector.dot(this, other);
	}

	public float length()
	{
		return (float) Math.sqrt(lengthSquared());
	}

	public float lengthSquared()
	{
		float l = 0;
		for (int i = 0; i < elements.length; i++)
			l += (elements[i] * elements[i]);
		return l;
	}

	public float angle(Vector other)
	{
		return Vector.angle(this, other);
	}

	public float[] getElements()
	{
		return elements;
	}

	public float getEl(int i)
	{
		return i < elements.length ? elements[i] : 0;
	}

	@Override
	public String toString()
	{
		String s = "Vector[";

		for (int i = 0; i < elements.length; i++)
			s += elements[i] + (i == elements.length - 1 ? "]" : ", ");

		return s;
	}

	@Override
	public boolean equals(Object o)
	{
		boolean e = false;
		if (o instanceof Vector)
		{
			e = true;
			Vector v = (Vector) o;
			checkSame(this, v);

			for (int i = 0; i < v.elements.length; i++)
				e = (Math.abs(elements[i] - v.elements[i]) < SAME_TOLERANCE) && e;
		}
		return e;
	}

	/**
	 * Error Checking Methods
	 */
	protected static void checkSame(Vector v, Vector v1)
	{
		if (v1.elements.length != v.elements.length)
		{
			try
			{
				throw new VectorLengthError();
			} catch (VectorLengthError vectorLengthError)
			{
				vectorLengthError.printStackTrace();
			}
		}
	}

	/**
	 * Static Methods
	 **/
	public static float dot(Vector left, Vector right)
	{
		checkSame(left, right);
		float d = 0;

		for (int i = 0; i < left.elements.length; i++)
			d += (left.elements[i] * right.elements[i]);

		return d;
	}

	public static float angle(Vector left, Vector right)
	{
		checkSame(left, right);

		float dls = Vector.dot(left, right) / (left.length() * right.length());

		dls = Maths.clamp(dls, -1f, 1f);

		return (float) Math.acos(dls);
	}

	public static Vector round(Vector left, int n, Vector dest)
	{
		dest = dest == null ? new Vector(new float[left.elements.length]) : dest;
		checkSame(left, dest);

		for (int i = 0; i < left.elements.length; i++)
			dest.elements[i] = Maths.round(left.elements[i], n);

		return dest;
	}

	public static Vector add(Vector left, Vector right, Vector dest)
	{
		Vector v = dest == null ? new Vector(new float[left.elements.length]) : dest;
		checkSame(left, right);

		for (int i = 0; i < left.elements.length; i++)
			v.elements[i] = left.elements[i] + right.elements[i];

		return v;
	}

	public static Vector sub(Vector left, Vector right, Vector dest)
	{
		Vector v = dest == null ? new Vector(new float[left.elements.length]) : dest;
		checkSame(left, right);

		for (int i = 0; i < left.elements.length; i++)
			v.elements[i] = left.elements[i] - right.elements[i];

		return v;
	}

	public static Vector multElements(Vector left, Vector right, Vector dest)
	{
		Vector v = dest == null ? new Vector(new float[left.elements.length]) : dest;
		checkSame(left, right);

		for (int i = 0; i < left.elements.length; i++)
			v.elements[i] = left.elements[i] * right.elements[i];

		return v;
	}


	public static Vector scale(Vector left, float scale, Vector dest)
	{
		Vector v = new Vector(new float[left.elements.length]);

		for (int i = 0; i < v.elements.length; i++)
			v.elements[i] = left.elements[i] * scale;

		if (dest != null)
			dest.set(v.elements);// chaneg to load

		return v;
	}
}
