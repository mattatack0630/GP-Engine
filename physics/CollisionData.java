package physics;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 8/26/2016.
 */
public class CollisionData
{
	public PhysicsObject objOne;
	public PhysicsObject objTwo;
	public boolean doesCollide;
	public Vector3f collisionNormal;
	public Vector3f collisionPoint;
	public Vector3f objOneVel;
	public Vector3f objTwoVel;
	public long toi;

	public CollisionData(boolean doesCollide)
	{
		this.doesCollide = doesCollide;
		this.collisionNormal = new Vector3f();
		this.collisionPoint = new Vector3f();
	}

	@Override
	public String toString()
	{
		String s = new String();

		s+="CollisionData ("+this.hashCode()+")\n";
		s+="\tCollides : "+doesCollide+"\n";
		s+="\tCollision Point : "+collisionPoint+"\n";
		s+="\tCollision Normal : "+collisionNormal+"\n\n";

		return s;
	}
}
