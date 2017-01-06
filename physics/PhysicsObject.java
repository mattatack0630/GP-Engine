package physics;

import physics.collidables.Collidable;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 8/25/2016.
 * The Physics Object is an object that can be placed inside a PhysicsEnvironment to be affected by that environment's physics
 */
public class PhysicsObject
{
	public static final Vector3f naturalOrientation = new Vector3f(0.0001f, 0.999f, 0);

	public float mass;
	public float restitution;
	public Collidable collidable;

	public Vector3f rot; // orientation vector, never mind... cant define using vector because one axis would be locked. Maybe 2?
	public Vector3f rotVel;
	public Vector3f rotAcc;
	public float momentOfInertia;
	public float surfaceFriction;
	public Vector3f centerOfMass; // relative to position

	public Vector3f pos;
	public Vector3f posVel;
	public Vector3f posAcc;

	//lastPos variable is used to reset the position to before the last update (Collision purposes)
	public Vector3f lastPos;
	public Vector3f lastRot;

	public PhysicsObject(Collidable collidable, Vector3f pos, Vector3f vel, Vector3f acc, float mass, float momentOfInertia, float restitution)
	{
		this.mass = mass;
		this.restitution = restitution;
		this.surfaceFriction = 0;
		this.collidable = collidable;

		this.pos = pos;
		this.posVel = vel;
		this.posAcc = acc;
		this.lastPos = pos;
		this.lastRot = new Vector3f();

		this.rot = new Vector3f();
		this.rotVel = new Vector3f();
		this.rotAcc = new Vector3f();
		this.centerOfMass = new Vector3f();
		this.momentOfInertia = momentOfInertia;
	}

	public void addForce (Vector3f f)
	{
		Vector3f.add(this.posAcc, f, this.posAcc);
	}
	public void addTorque(Vector3f f)
	{
		Vector3f.add(this.rotAcc, f, this.rotAcc);
	}

	public void update()
	{
		lastPos = new Vector3f(this.pos);
		lastRot = new Vector3f(this.rot);

		posAcc.scale(1/mass);
		rotAcc.scale(1/momentOfInertia);

		Vector3f.add(this.rotVel, this.rotAcc, this.rotVel);
		Vector3f.add(this.posVel, this.posAcc, this.posVel);
		Vector3f.add(this.pos, this.posVel, this.pos);
		Vector3f.add(this.rot, this.rotVel, this.rot);

		this.rotAcc.set(0,0,0);
		this.posAcc.set(0,0,0);

		this.collidable.update(this.pos, this.centerOfMass, this.rot);
	}

	public void updatePos()
	{
		Vector3f.add(this.pos, this.posVel, this.pos);
		Vector3f.add(this.rot, this.rotVel, this.rot);
		this.collidable.update(this.pos, this.centerOfMass, this.rot);
	}

	public void undoUpdate(){
		this.pos = new Vector3f(lastPos);
		this.rot = new Vector3f(lastRot);
	}
}
