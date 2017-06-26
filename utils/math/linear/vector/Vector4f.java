package utils.math.linear.vector;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Vector4f extends Vector
{
	/**
	 * Constructors
	 */
	public Vector4f(float x, float y, float z, float w)
	{
		super(new float[]{x, y, z, w});
	}

	public Vector4f()
	{
		this(0, 0, 0, 0);
	}

	public Vector4f(float s)
	{
		this(s, s, s, s);
	}

	public Vector4f(Vector4f src)
	{
		this(src.x(), src.y(), src.z(), src.w());
	}

	/**
	 * Vec4 Specific Methods
	 */
	public float x()
	{
		return elements[0];
	}

	public float y()
	{
		return elements[1];
	}

	public float z()
	{
		return elements[2];
	}

	public float w()
	{
		return elements[3];
	}

	public void setX(float x)
	{
		elements[0] = x;
	}

	public void setY(float y)
	{
		elements[1] = y;
	}

	public void setZ(float z)
	{
		elements[2] = z;
	}

	public void setW(float w)
	{
		elements[3] = w;
	}

	public void set(float x, float y, float z, float w)
	{
		elements[0] = x;
		elements[1] = y;
		elements[2] = z;
		elements[3] = w;
	}

	public void set(Vector4f v)
	{
		setX(v.x());
		setY(v.y());
		setZ(v.z());
		setW(v.w());
	}

	/**
	 * Casted Methods
	 */
	public Vector4f negate()
	{
		return (Vector4f) super.negate();
	}

	public Vector4f normalize()
	{
		return (Vector4f) super.normalize();
	}

	public Vector4f add(Vector other)
	{
		return (Vector4f) super.add(other);
	}

	public Vector4f sub(Vector other)
	{
		return (Vector4f) super.sub(other);
	}

	public Vector4f scale(float scale)
	{
		return (Vector4f) super.scale(scale);
	}

	public Vector4f multElements(Vector other)
	{
		return (Vector4f) super.multElements(other);
	}

	public Vector4f setMagnitude(float magnitude)
	{
		return (Vector4f) super.setMagnitude(magnitude);
	}

	/**
	 * Static Methods
	 */
	public static Vector4f add(Vector4f left, Vector4f right, Vector4f dest)
	{
		dest = dest == null ? new Vector4f() : dest;
		Vector.add(left, right, dest);
		return dest;
	}

	public static Vector4f sub(Vector4f left, Vector4f right, Vector4f dest)
	{
		dest = dest == null ? new Vector4f() : dest;
		Vector.sub(left, right, dest);
		return dest;
	}

	public static Vector4f multElements(Vector4f left, Vector4f right, Vector4f dest)
	{
		dest = dest == null ? new Vector4f() : dest;
		Vector.multElements(left, right, dest);
		return dest;
	}

}
