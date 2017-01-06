package utils.math.linear.rotation;

import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Quaternion extends Vector4f implements Rotation
{
	public Quaternion()
	{
		super(0, 0, 0, 0);
	}

	public Quaternion(float x, float y, float z, float w)
	{
		super(x, y, z, w);
		normalize();
	}

	public Quaternion(Vector4f src)
	{
		this(src.x(), src.y(), src.z(), src.w());
	}

	@Override
	public void fromAxisAngle(AxisAngle a)
	{
		Vector3f axis = a.getAxis();
		float s = (float) Math.sin(a.getAngleRad() / 2);
		elements[3] = (float) Math.cos(a.getAngleRad() / 2);
		elements[0] = axis.x() * s;
		elements[1] = axis.y() * s;
		elements[2] = axis.z() * s;
	}

	@Override
	public AxisAngle toAxisAngle()
	{
		if (w() > 1)
			normalize();

		Vector3f axis = new Vector3f();
		float angle = (float) (2 * Math.acos(w()));
		float s = (float) Math.sqrt(1 - w() * w());
		if (s < 0.001)
		{
			axis.elements[0] = x();
			axis.elements[1] = y();
			axis.elements[2] = z();
		} else
		{
			axis.elements[0] = (x() / s);
			axis.elements[1] = (y() / s);
			axis.elements[2] = (z() / s);
		}

		return new AxisAngle(axis, angle);
	}

	@Override
	public Rotation copy()
	{
		return new Quaternion(x(), y(), z(), w());
	}

	@Override
	public String toString()
	{
		String s = "Quaternion: ";
		s += "[(x)" + elements[0] + ", (y)" + elements[1] + ", (z)" + elements[2] + ", (w)" + elements[3] + "]";
		return s;
	}
}
