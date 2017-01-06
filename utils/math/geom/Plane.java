package utils.math.geom;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/7/2016.
 */
public class Plane extends GeometryObject
{
	private Vector3f normal;
	private float height;

	public Plane(Vector3f normal, float d)
	{
		this.normal = normal;
		this.height = d;
	}

	public float getHeight()
	{
		return height;
	}

	public Vector3f getNormal()
	{
		return normal;
	}

	public void set(Vector3f normal, float height)
	{
		this.normal = new Vector3f(normal);
		this.height = height;
	}

	@Override
	public String equation()
	{
		return normal.x() + "x + " + normal.y() + "y + " + normal.z() + "z = " + height;
	}

	@Override
	public boolean equals(Object o)
	{
		boolean e = false;

		if (o instanceof Plane)
		{
			Plane p = (Plane) o;
			e = (this.height == p.height && this.normal.equals(p.normal));
		}

		return e;
	}

	public String toString()
	{
		return equation();
	}
}
