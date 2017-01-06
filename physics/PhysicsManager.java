package physics;

import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 8/25/2016.
 */
public class PhysicsManager
{
	public static boolean LOCK_ROT = false;
	public static List<PhysicsEnvironment> environments = new ArrayList<PhysicsEnvironment>();

	public static void init()
	{

	}

	public static void addEnvironment(PhysicsEnvironment environment)
	{
		environments.add(environment);
	}

	public static void removeEnvironment(PhysicsEnvironment environment)
	{
		environments.add(environment);
	}

	public static void tick()
	{
		for (PhysicsEnvironment e : environments)
			tickEnvironment(e);
	}

	public static void tickEnvironment(PhysicsEnvironment environment)
	{
		//	Apply environment forces
		for (PhysicsObject o1 : environment.physicsObjects)
		{
			for (Vector3f force : environment.forces)
				o1.addForce(force);
			o1.rotVel.scale(.995f); // add resistance
			o1.posVel.scale(.999f); // add resistance
			o1.update(); // Update pos
		}

		// Narrow down results using AABB technique

		// For each environments physicsObjects, check for collisions with other objects
		ArrayList<CollisionData> collisions = CollisionManager.getCollisions(environment.physicsObjects);

		// Resolve Collisions
		for (CollisionData cd : collisions)
		{
			/*Setup for response*/
			PhysicsObject o1 = cd.objOne;
			PhysicsObject o2 = cd.objTwo;
			o1.undoUpdate();
			o2.undoUpdate();


			/*Linear Velocity Calculations*/
			Vector3f vrel = Vector3f.sub(cd.objOneVel, cd.objTwoVel, null);
			Vector3f nmass = new Vector3f(cd.collisionNormal).scale((1 / o1.mass) + (1 / o2.mass));

			float restitution = -1 * (1.0f + o1.restitution);
			vrel.scale(restitution);
			float nmassDot = Vector3f.dot(cd.collisionNormal, nmass);
			float impulse = Math.max( Vector3f.dot(vrel, cd.collisionNormal) / nmassDot, .001f );
			float friction = Math.max(( 1 - (o1.surfaceFriction + o2.surfaceFriction )), 0);

			Vector3f linearResult = new Vector3f(cd.collisionNormal).scale(impulse / o1.mass);

			o1.posVel = Vector3f.add(cd.objOneVel, linearResult, null);
			o1.posVel.scale(friction);


			/**Calculate Rotation Velocity**/
			if (cd.collisionPoint == null || LOCK_ROT)
				continue;

			Vector3f contactVector = Vector3f.sub(cd.collisionPoint, o1.pos, null);
			Vector3f contactCross = Vector3f.cross(contactVector, linearResult, null);
			contactCross.scale((float) 1 / (o1.momentOfInertia + o1.mass));
			o1.rotVel = contactCross;
		}

		// Update each objects pos
		for (PhysicsObject o1 : environment.physicsObjects)
			o1.updatePos();
	}
}
