package physics;

import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 8/25/2016.
 */
public class PhysicsEnvironment
{
	public static final float METER_SCALE = .0005f;

	public static Vector3f gravity = new Vector3f(0, METER_SCALE * -9.8f,0);

	public boolean pointForce_on;
	public Vector3f pointForce_point;
	public float pointForce_strength;

	public List<PhysicsObject> physicsObjects;
	public List<Vector3f> forces;

	public PhysicsEnvironment()
	{
		physicsObjects = new ArrayList<>();
		forces = new ArrayList<>();
		turnOffToPointForce();
	}

	public void setGravityStrength(float strength)
	{
		gravity.scale(strength);
	}

	public void setGravityDir(float degrees)
	{

	}

	public void addPhysicsObject(PhysicsObject object)
	{
		physicsObjects.add(object);
	}

	public void removeObject(PhysicsObject object)
	{
		physicsObjects.remove(object);
	}

	public void addForce(Vector3f force)
	{
		forces.add(force);
	}

	public void removeForce(Vector3f force)
	{
		forces.remove(force);
	}

	public void addToPointForce(Vector3f vector3f, float i)
	{
		pointForce_on = true;
		pointForce_strength = i;
		pointForce_point = vector3f;
	}

	public void turnOffToPointForce()
	{
		pointForce_on = false;
		pointForce_strength = 0;
		pointForce_point = null;
	}

	public void applySpecialForces(PhysicsObject o)
	{
		if(pointForce_on)
		{
			Vector3f force = Vector3f.sub(pointForce_point, o.pos, null);
			force.scale(pointForce_strength / 1000);
			o.addForce(force);
		}
	}
}
