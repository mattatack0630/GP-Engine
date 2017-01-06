package utils.math.linear.vector;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Vector3f extends Vector
{
	public static final Vector3f UP = new Vector3f(0, 1, 0);
	public static final Vector3f DOWN = new Vector3f(0, -1, 0);
	public static final Vector3f LEFT = new Vector3f(-1, 0, 0);
	public static final Vector3f RIGHT = new Vector3f(1, 0, 0);
	public static final Vector3f FORWARD = new Vector3f(0, 0, -1);
	public static final Vector3f BACKWARD = new Vector3f(0, 0, 1);
	public static final Vector3f SMAll_OFFSET = new Vector3f(0.00001f, 0.00001f, 0.00001f);

	/**
	 * Constructors
	 */
	public Vector3f(float x, float y, float z)
	{
		super(new float[]{x, y, z});
	}

	public Vector3f()
	{
		this(0, 0, 0);
	}

	public Vector3f(float s)
	{
		this(s, s, s);
	}

	public Vector3f(float[] f)
	{
		super(f, 3);
	}

	public Vector3f(Vector3f src)
	{
		this(src.elements);
	}

	/**
	 * Vec3 specific
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

	public void set(float x, float y, float z)
	{
		elements[0] = x;
		elements[1] = y;
		elements[2] = z;
	}

	public Vector3f cross(Vector3f other)
	{
		return Vector3f.cross(this, other, this);
	}

	/**
	 * Casted Methods
	 */
	public Vector3f negate()
	{
		return (Vector3f) super.negate();
	}

	public Vector3f normalize()
	{
		return (Vector3f) super.normalize();
	}

	public Vector3f add(Vector other)
	{
		return (Vector3f) super.add(other);
	}

	public Vector3f sub(Vector other)
	{
		return (Vector3f) super.sub(other);
	}

	public Vector3f scale(float scale)
	{
		return (Vector3f) super.scale(scale);
	}

	public Vector3f multElements(Vector other)
	{
		return (Vector3f) super.multElements(other);
	}

	public Vector3f setMagnitude(float magnitude)
	{
		return (Vector3f) super.setMagnitude(magnitude);
	}

	/**
	 * Static Methods
	 */
	public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest)
	{
		dest = dest == null ? new Vector3f() : dest;
		Vector.add(left, right, dest);
		return dest;
	}

	public static Vector3f sub(Vector3f left, Vector3f right, Vector3f dest)
	{
		dest = dest == null ? new Vector3f() : dest;
		Vector.sub(left, right, dest);
		return dest;
	}

	public static Vector3f cross(Vector3f left, Vector3f right, Vector3f dest)
	{
		Vector3f v = dest == null ? new Vector3f(0, 0, 0) : dest;
		float x = (left.elements[1] * right.elements[2]) - (left.elements[2] * right.elements[1]);
		float y = (left.elements[2] * right.elements[0]) - (left.elements[0] * right.elements[2]);
		float z = (left.elements[0] * right.elements[1]) - (left.elements[1] * right.elements[0]);
		v.set(x, y, z);
		return v;
	}

	public static Vector3f multElements(Vector3f left, Vector3f right, Vector3f dest)
	{
		dest = dest == null ? new Vector3f() : dest;
		Vector.multElements(left, right, dest);
		return dest;
	}
}
