package physics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 8/25/2016.
 * The Collision Manager class is used to detect collisions, but not handle them
 *
 */
public class CollisionManager
{
	/**
	 * @param physicsObjects the list of physics objects to test for collisions in.
	 * @return a list of collision data objects that can be used by the physics manager to handle them.
	 * */
	public static ArrayList<CollisionData> getCollisions(List<PhysicsObject> physicsObjects)
	{
		ArrayList<CollisionData> datas = new ArrayList<CollisionData>();

		for (PhysicsObject p1 : physicsObjects)
		{
			for (PhysicsObject p2 : physicsObjects)
			{
				if (p1 == p2)
					continue;

				CollisionData data = CollisionDictionary.doesCollide(p1.collidable, p2.collidable);
				data.objOne = p1;
				data.objTwo = p2;
				data.objOneVel = p1.posVel;
				data.objTwoVel = p2.posVel;

				if (!data.doesCollide)
					continue;

				datas.add(data);

			}
		}
		return datas;
	}

}
