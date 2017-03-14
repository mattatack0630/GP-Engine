package utils.math;

import org.lwjgl.opengl.Display;
import physics.collidables.Plane;
import rendering.DisplayManager;
import utils.math.linear.vector.Vector;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

import java.util.Random;

public class Maths
{
	public static int ScreenSizeX = DisplayManager.WIDTH;
	public static int ScreenSizeY = DisplayManager.HEIGHT;

	private static Random random = new Random();
	private static int uniqueCounter;

	public static float getSign(float num)
	{
		if (num >= 0)
			return 1;
		else
			return -1;
	}

	public static float round(float d, int i)
	{
		float tens = (float) Math.pow(10, i);
		return Math.round(d * tens) / tens;
	}

	public static float clamp(float n, float min, float max)
	{
		if (n < min)
			n = min;
		else if (n > max)
			n = max;
		return n;
	}

	public static int clamp(int n, int min, int max)
	{
		if (n < min)
			n = min;
		else if (n > max)
			n = max;
		return n;
	}

	public static float random(float f0, float f1)
	{
		return map(random.nextFloat(), 0, 1, f0, f1);
	}

	public static int randomi(int i0, int i1)
	{
		return (int) map(random.nextFloat(), 0, 1, i0, i1);
	}

	public static float map(float s, float a0, float a1, float b0, float b1)
	{
		return (s - a0) / (a1 - a0) * (b1 - b0) + b0;
	}

	public static float squared(float v)
	{
		return v * v;
	}

	public static float max(float... numbers)
	{
		float largest = numbers[0];

		for (float n : numbers)
			if (n > largest)
				largest = n;

		return largest;
	}

	public static int max(int... numbers)
	{
		int largest = numbers[0];

		for (int n : numbers)
			if (n > largest)
				largest = n;

		return largest;
	}

	public static float min(float... numbers)
	{
		float largest = numbers[0];

		for (float n : numbers)
			if (n < largest)
				largest = n;

		return largest;
	}

	public static int min(int... numbers)
	{
		int largest = numbers[0];

		for (int n : numbers)
			if (n < largest)
				largest = n;

		return largest;
	}

	/**
	 * Get the next unique number in a static counter
	 * variable, this can be useful in situations where you need defiantly
	 * unique numbers and don't mind that they are in sequence
	 * <p>
	 * NOTE - this is not a random number! this method simply
	 * returns the next in a counter variable
	 */
	public static int uniqueInteger()
	{
		return (uniqueCounter++);
	}

	////////////////////////////////////////// To Remove ////////////////////////////////////////////////////////////

	public static Vector3f calculateTangent(Vector3f p0, Vector3f p1, Vector3f p2, Vector2f t0, Vector2f t1, Vector2f t2)
	{
		Vector3f deltaV1 = Vector3f.sub(p1, p0, null);
		Vector3f deltaV2 = Vector3f.sub(p2, p0, null);
		Vector2f deltaT1 = Vector2f.sub(t1, t0, null);
		Vector2f deltaT2 = Vector2f.sub(t2, t0, null);

		float d = (deltaT1.x() * deltaT2.y() - deltaT1.y() * deltaT2.x());
		float r = d != 0.0f ? 1f / d : 0.0f;

		Vector3f alpha1 = deltaV1.scale(deltaT2.y());
		Vector3f alpha2 = deltaV2.scale(deltaT1.y());

		return Vector3f.sub(alpha1, alpha2, null).scale(r);
	}

	public static Vector2f glCoordsFromPixle(Vector2f pixleCoord)
	{
		Vector2f result = new Vector2f();
		result.setX(((2f * pixleCoord.x()) / Display.getWidth()) - 1);
		result.setY(((2f * pixleCoord.y()) / Display.getHeight()) - 1);
		return result;
	}

	public static Vector2f glLengthFromPixle(Vector2f pixleCoord)
	{
		return new Vector2f
				(((2f / ScreenSizeX) * pixleCoord.x()), ((2f / ScreenSizeY) * pixleCoord.y()));
	}

	public static Vector3f planeIntersect(Plane p, Vector3f a, Vector3f b)
	{
		Vector3f ba = Vector3f.sub(b, a, null);

		float nDotA = Vector3f.dot(p.normal, a);
		float nDotBA = Vector3f.dot(p.normal, ba);
		if (nDotA == 0 || nDotBA == 0)
			return null;

		Vector3f vec = ba.scale((p.height - nDotA) / nDotBA);

		return Vector3f.add(a, vec, null);
	}

	public static float distToPlane(Vector3f total, Plane facePlane)
	{
		return Vector3f.dot(facePlane.normal.normalize(), total) - facePlane.height;
	}

	public static Vector randomVec(float v0, float v1)
	{
		return new Vector3f(random(v0, v1), random(v0, v1), random(v0, v1));
	}
}