package utils.math;

import com.sun.istack.internal.NotNull;
import utils.math.linear.vector.Vector;

/**
 * Created by mjmcc on 12/8/2016.
 */
public class Interpolation
{
	/**
	 * Interpolate a Vector
	 *
	 * @param v1     the floor vector
	 * @param v2     the ceiling vector
	 * @param result the result Vector to store the interpolated Vector **MUST BE AN INITIALIZED VALUE (Not Null)**
	 * @param t      the value 0-1 indicating progression
	 * @return The resulting interpolated Vector
	 */
	public static <E extends Vector> E interpolateVector(InterpType interpType, E v1, E v2, @NotNull E result, float t)
	{
		if (v1.elements.length == v2.elements.length)
			for (int i = 0; i < v1.elements.length; i++)
				result.elements[i] = interpolate(interpType, v1.elements[i], v2.elements[i], t);

		return result;
	}

	public static float interpolate(InterpType interpType, float f1, float f2, float t)
	{
		float interpValue = 0;

		switch (interpType)
		{
			case LINEAR:
				interpValue = linearInterpolate(f1, f2, t);
				break;
			case COSINE:
				interpValue = cosineInterpolate(f1, f2, t);
				break;
			case CUBIC:
				interpValue = cubicInterpolate(f1, f2, t);
				break;
		}

		return interpValue;
	}

	public static float linearInterpolate(float f1, float f2, float t)
	{
		return (f1 * (1.0f - t) + f2 * t);
	}

	public static float cosineInterpolate(float f1, float f2, float t)
	{
		float tCos = (float) (1 - Math.cos(t * Math.PI)) / 2f;
		return (f1 * (1 - tCos) + f2 * tCos);
	}

	public static float cubicInterpolate(float f1, float f2, float t)
	{
		return 0; // Unimplemented
	}
}
