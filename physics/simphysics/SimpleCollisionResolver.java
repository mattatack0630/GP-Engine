package physics.simphysics;

import utils.dstruct.OctTree;
import utils.math.geom.AABB;
import utils.math.geom.IntersectData;
import utils.math.geom.IntersectMath;
import utils.math.geom.IntersectParams;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 1/23/2017.
 */
public class SimpleCollisionResolver
{
	public Map<SimpleCollisionBody, List<SimpleCollisionData>> resolveCollisions(List<SimpleCollisionBody> bodies)
	{
		Map<SimpleCollisionBody, List<SimpleCollisionData>> cdMap = new HashMap<>();

		for (int i = 0; i < bodies.size(); i++)
		{
			for (int j = i + 1; j < bodies.size(); j++)
			{
				SimpleCollisionBody b0 = bodies.get(i);
				SimpleCollisionBody b1 = bodies.get(j);
				IntersectData data = IntersectMath.intersects(b0.getBounds(), b1.getBounds(), IntersectParams.CALC_ALL);
				resolveCollision(b0, b1, cdMap, data);
			}
		}

		return cdMap;
	}

	public Map<SimpleCollisionBody, List<SimpleCollisionData>> resolveCollisions(OctTree<SimpleCollisionBody> bodiesTree)
	{
		Map<SimpleCollisionBody, List<SimpleCollisionData>> cdMap = new HashMap<>();


		List<SimpleCollisionBody> bodies = bodiesTree.getItems();
		cdMap.putAll(resolveCollisions(bodies));

		for (SimpleCollisionBody b0 : bodies)
		{
			for (OctTree<SimpleCollisionBody> childTree : bodiesTree.getChildren())
			{
				if (b0.collides(childTree.getBoundingRegion()))
				{
					List<SimpleCollisionBody> decedents = childTree.getDecedentItems();

					for (SimpleCollisionBody b1 : decedents)
					{
						if (b0.changedTransform() || b1.changedTransform())
						{
							IntersectData data = IntersectMath.intersects(b0.getBounds(), b1.getBounds(), IntersectParams.CALC_ALL);
							resolveCollision(b0, b1, cdMap, data);
						}
					}
				}
			}
		}

		for (OctTree<SimpleCollisionBody> childTree : bodiesTree.getChildren())
		{
			cdMap.putAll(resolveCollisions(childTree));
		}

		return cdMap;
	}

	public Map<SimpleCollisionBody, List<SimpleCollisionData>> resolveCollisions(List<List<SimpleCollisionBody>> bodyChunks,
																		   int width, int height)
	{
		Map<SimpleCollisionBody, List<SimpleCollisionData>> cdMap = new HashMap<>();

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

								AABB bounds0 = b0.getBounds();
								AABB bounds1 = b1.getBounds();
								IntersectData data = IntersectMath.intersects(bounds0, bounds1, IntersectParams.CALC_ALL);
								resolveCollision(b0, b1, cdMap, data);
							}
						}
					}
				}
			}
		}

		return cdMap;
	}

	public void resolveCollision(SimpleCollisionBody body0, SimpleCollisionBody body1,
								 Map<SimpleCollisionBody, List<SimpleCollisionData>> cdMap, IntersectData data)
	{
		if (body0 != body1)
		{
			boolean ignoreMasks = (body0.shouldIgnore(body1.getMask()) || body1.shouldIgnore(body0.getMask()));
			SimpleCollisionData collisionData0 = new SimpleCollisionData(data, body0, body1, 0.0f);
			SimpleCollisionData collisionData1 = SimpleCollisionData.genOpposite(collisionData0);

			if (data.isIntersecting() && body0.isDynamic() && !ignoreMasks)
			{
				IntersectData idata = collisionData0.getIntersectData();
				List<SimpleCollisionData> dataList = cdMap.get(body0);
				if(dataList == null) dataList = new ArrayList<>();
				body0.translate(idata.getMinTranslateVector());
				dataList.add(collisionData0);
				cdMap.put(body0, dataList);
			}

			if (data.isIntersecting() && body1.isDynamic() && !ignoreMasks)
			{
				List<SimpleCollisionData> dataList = cdMap.get(body1);
				if(dataList == null) dataList = new ArrayList<>();
				IntersectData idata = collisionData1.getIntersectData();
				Vector3f tmtv = idata.getIntersectionNormal();

				// don't counter other collisions (maybe change if this becomes a fps issue)
				Vector3f t = new Vector3f(idata.getMinTranslateVector());
				for(SimpleCollisionData cData : dataList)
				{
					IntersectData idata1 = cData.getIntersectData();
					Vector3f mtv = idata1.getIntersectionNormal();
					if(mtv.x() == -tmtv.x())
						t.setX(0);
					if(mtv.y() == -tmtv.y())
						t.setY(0);
					if(mtv.z() == -tmtv.z())
						t.setZ(0);
				}

				body1.translate(t);
				dataList.add(collisionData1);
				cdMap.put(body1, dataList);
			}
		}
	}

}
