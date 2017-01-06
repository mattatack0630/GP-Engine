package rendering.camera;

import org.lwjgl.opengl.Display;
import utils.math.geom.AABB;
import utils.math.geom.Geometry;
import utils.math.geom.Plane;
import utils.math.linear.LinearAlgebra;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 9/26/2016.
 */
public class Frustum
{
	Plane f1;
	Plane f2;
	Plane f3;
	Plane f4;

	public Frustum()
	{
		f1 = new Plane(new Vector3f(), 0);
		f2 = new Plane(new Vector3f(), 0);
		f3 = new Plane(new Vector3f(), 0);
		f4 = new Plane(new Vector3f(), 0);
	}

	public void update(Vector3f pos, Vector3f up, Vector3f right, Vector3f forward, float fov, float farDist)
	{
		float aspectRatio = 1 - (float) Display.getHeight() / (float) Display.getWidth();
		float fova = fov + (aspectRatio * fov);//fov + (aspectRatio * fov) - 0.1f;
		float fovb = fov;//fov - 0.1f;//(fov / aspectRatio);
		Matrix4f rot = new Matrix4f();

		// UP LEFT
		rot.setIdentity();
		rot.rotate(-fova / 2, up);
		rot.rotate(fovb / 2, right);
		Vector3f p0 = LinearAlgebra.mult(rot, forward, null);
		p0.scale(farDist / 2);
		Vector3f.add(p0, pos, p0);

		// UP RIGHT
		rot.setIdentity();
		rot.rotate(fova / 2, up);
		rot.rotate(fovb / 2, right);
		Vector3f p1 = LinearAlgebra.mult(rot, forward, null);
		p1.scale(farDist / 2);
		Vector3f.add(p1, pos, p1);

		// DOWN RIGHT
		rot.setIdentity();
		rot.rotate(fova / 2, up);
		rot.rotate(-fovb / 2, right);
		Vector3f p2 = LinearAlgebra.mult(rot, forward, null);
		p2.scale(farDist / 2);
		Vector3f.add(p2, pos, p2);

		// DOWN LEFT
		rot.setIdentity();
		rot.rotate(-fova / 2, up);
		rot.rotate(-fovb / 2, right);
		Vector3f p3 = LinearAlgebra.mult(rot, forward, null);
		p3.scale(farDist / 2);
		Vector3f.add(p3, pos, p3);

		Plane pl0 = Geometry.planeFromPoints(pos, p1, p0);
		Plane pl1 = Geometry.planeFromPoints(pos, p2, p1);
		Plane pl2 = Geometry.planeFromPoints(pos, p3, p2);
		Plane pl3 = Geometry.planeFromPoints(pos, p0, p3);
		f1.set(pl0.getNormal(), pl0.getHeight());
		f2.set(pl1.getNormal(), pl1.getHeight());
		f3.set(pl2.getNormal(), pl2.getHeight());
		f4.set(pl3.getNormal(), pl3.getHeight());
	}

	public boolean inView(AABB aabb)
	{
		Vector3f[] points = aabb.points();
		boolean intersects = false;

		for (Vector3f p : points)
		{
			if (inView(p))
			{
				intersects = true;
				break;
			}
		}

		return intersects;
	}

	public boolean inView(Vector3f p)
	{
		boolean b0 = Vector3f.dot(f1.getNormal().normalize(), p) - f1.getHeight() <= 0;
		boolean b1 = Vector3f.dot(f2.getNormal().normalize(), p) - f2.getHeight() <= 0;
		boolean b2 = Vector3f.dot(f3.getNormal().normalize(), p) - f3.getHeight() <= 0;
		boolean b3 = Vector3f.dot(f4.getNormal().normalize(), p) - f4.getHeight() <= 0;
		return b0 && b1 && b2 && b3;
	}
}