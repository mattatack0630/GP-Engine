package physics.collidables;

import rendering.RenderData;
import rendering.renderers.Gizmos3D;
import rendering.renderers.MasterRenderer;
import utils.math.linear.rotation.AxisAngle;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 8/26/2016.
 */
public class Plane extends Collidable
{
	private Vector3f origin;

	public Vector3f knownPoint;
	public Vector3f normal;
	public float height;

	public Plane(Vector3f normal, float height){
		super(Collidable.PLANE);
		this.normal = normal.normalize();
		this.height = height;
		this.knownPoint = new Vector3f(10, 0, 0);
		data = new RenderData(new Vector3f());
		origin = new Vector3f(0,1,0);
		data.setScale(new Vector3f(600, .0125f, 600));
		data.setRotation(new AxisAngle(new Vector3f(), 0));
	}

	public void update(Vector3f pos,  Vector3f COM, Vector3f rot)
	{
	}

	public void tempRender(MasterRenderer renderer)
	{
		Gizmos3D.setGizmosColor(data.tempColor);
		Gizmos3D.drawPlane(this.normal, this.height, 1000);
	}

	@Override
	public boolean equals(Object o){
		Plane p = (Plane) o;
		return (this.normal.equals(p.normal) && this.height == p.height);
	}
}
