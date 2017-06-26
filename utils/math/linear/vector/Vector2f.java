package utils.math.linear.vector;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Vector2f extends Vector
{
	/**
	 * Constructors
	 */
	public Vector2f(float x, float y)
	{
		super(new float[]{x, y});
	}

	public Vector2f(float s)
	{
		this(s, s);
	}

	public Vector2f()
	{
		this(0, 0);
	}

	public Vector2f(Vector2f src)
	{
		super(src.elements);
	}

	/**
	 * Vec2 Specific Methods
	 */
	public float x()
	{
		return elements[0];
	}

	public float y()
	{
		return elements[1];
	}

	public void setX(float x)
	{
		elements[0] = x;
	}

	public void setY(float y)
	{
		elements[1] = y;
	}

	public void set(float x, float y)
	{
		elements[0] = x;
		elements[1] = y;
	}

	public void set(Vector2f xy)
	{
		elements[0] = xy.x();
		elements[1] = xy.y();
	}

	/**
	 * Casted Methods
	 */
	public Vector2f negate()
	{
		return (Vector2f) super.negate();
	}

	public Vector2f normalize()
	{
		return (Vector2f) super.normalize();
	}

	public Vector2f add(Vector other)
	{
		return (Vector2f) super.add(other);
	}

	public Vector2f sub(Vector other)
	{
		return (Vector2f) super.sub(other);
	}

	public Vector2f scale(float scale)
	{
		return (Vector2f) super.scale(scale);
	}

	public Vector2f multElements(Vector other)
	{
		return (Vector2f) super.multElements(other);
	}

	public Vector2f setMagnitude(float magnitude)
	{
		return (Vector2f) super.setMagnitude(magnitude);
	}

	/**
	 * Static Methods
	 */
	public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest)
	{
		dest = dest == null ? new Vector2f() : dest;
		Vector.add(left, right, dest);
		return dest;
	}

	public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest)
	{
		dest = dest == null ? new Vector2f() : dest;
		Vector.sub(left, right, dest);
		return dest;
	}

	public static Vector2f multElements(Vector2f left, Vector2f right, Vector2f dest)
	{
		dest = dest == null ? new Vector2f() : dest;
		Vector.multElements(left, right, dest);
		return dest;
	}

	public static Vector2f scale(Vector2f left, float scale, Vector2f dest)
	{
		dest = dest == null ? new Vector2f() : dest;
		Vector.scale(left, scale, dest);
		return dest;
	}
}
