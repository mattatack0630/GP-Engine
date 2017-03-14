package physics.simphysics;

import utils.math.geom.AABB;
import utils.math.geom.IntersectData;
import utils.math.geom.IntersectMath;
import utils.math.geom.IntersectParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 1/23/2017.
 */
public class SimpleCollisionResolver
{
	public Map<SimpleCollisionBody, SimpleCollisionData> resolveCollisions(List<SimpleCollisionBody> bodies)
	{
		Map<SimpleCollisionBody, SimpleCollisionData> cdMap = new HashMap<>();

		for (int i = 0; i < bodies.size(); i++)
		{
			for (int j = i + 1; j < bodies.size(); j++)
			{
				resolveCollision(bodies.get(i), bodies.get(j), cdMap);
			}
		}

		return cdMap;
	}

	public Map<SimpleCollisionBody, SimpleCollisionData> resolveCollisions(List<List<SimpleCollisionBody>> bodyChunks,
																		   int width, int height)
	{
		Map<SimpleCollisionBody, SimpleCollisionData> cdMap = new HashMap<>();

		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				List<SimpleCollisionBody> bodies0 = bodyChunks.get(y * width + x);

				for (int x1 = x - 1; x1 < x + 1; x1++)
				{
					for (int y1 = y - 1; y1 < y + 1; y1++)
					{
						if (x1 < 0 || y1 < 0 || x1 > width || y1 > height)
							continue;

						List<SimpleCollisionBody> bodies1 = bodyChunks.get(y1 * width + x1);

						for (int i = 0; i < bodies0.size(); i++)
						{
							SimpleCollisionBody b0 = bodies0.get(i);

							for (int j = 0; j < bodies1.size(); j++)
							{
								SimpleCollisionBody b1 = bodies1.get(j);
								resolveCollision(b0, b1, cdMap);
							}
						}
					}
				}
			}
		}

		return cdMap;
	}

	public void resolveCollision(SimpleCollisionBody body0, SimpleCollisionBody body1,
								 Map<SimpleCollisionBody, SimpleCollisionData> cdMap)
	{
		AABB bounds0 = body0.getBounds();
		AABB bounds1 = body1.getBounds();

		if (body0 != body1)
		{
			boolean ignoreMasks = (body0.shouldIgnore(body1.getMask()) || body1.shouldIgnore(body0.getMask()));
			IntersectData data = IntersectMath.intersects(bounds0, bounds1, IntersectParams.CALC_ALL);
			SimpleCollisionData collisionData0 = new SimpleCollisionData(data, body0, body1, 0.0f);
			SimpleCollisionData collisionData1 = SimpleCollisionData.genOpposite(collisionData0);

			if (data.isIntersecting() && body0.isDynamic() && !ignoreMasks)
			{
				IntersectData idata = collisionData0.getIntersectData();
				body0.translate(idata.getMinTranslateVector());
				cdMap.put(body0, collisionData0);
			}

			if (data.isIntersecting() && body1.isDynamic() && !ignoreMasks)
			{
				IntersectData idata = collisionData1.getIntersectData();
				body1.translate(idata.getMinTranslateVector());
				cdMap.put(body1, collisionData1);
			}
		}
	}

}
