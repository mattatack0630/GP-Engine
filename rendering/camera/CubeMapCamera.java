package rendering.camera;

import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 11/25/2016.
 */
public class CubeMapCamera extends Camera
{
	// R L T BT BK F
	private static Vector2f[] faceDirs = new Vector2f[]{
			new Vector2f(0, 90), new Vector2f(0, -90), new Vector2f(-90, 180f),
			new Vector2f(90f, 180f), new Vector2f(0, 180f), new Vector2f(0, 0)};

	public CubeMapCamera(Vector3f position, float near, float far)
	{
		super(position, new Vector3f(), near, far, 90);
	}

	public void pointToFace(int i)
	{
		Vector2f dir = faceDirs[i];
	}
}
