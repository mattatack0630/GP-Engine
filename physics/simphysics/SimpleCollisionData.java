package physics.simphysics;

import utils.math.geom.IntersectData;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 1/24/2017.
 */
public class SimpleCollisionData
{
	public static final SimpleCollisionData NONE = new SimpleCollisionData(null, null, null, 0.0f);

	private float timestamp;
	private SimpleCollisionBody body0;
	private SimpleCollisionBody body1;
	private IntersectData intersectData;

	public SimpleCollisionData(IntersectData intersectData, SimpleCollisionBody body0, SimpleCollisionBody body1, float timestamp)
	{
		this.intersectData = intersectData;
		this.timestamp = timestamp;
		this.body0 = body0;
		this.body1 = body1;
	}

	public float getTimestamp()
	{
		return timestamp;
	}

	public SimpleCollisionBody getBody0()
	{
		return body0;
	}

	public SimpleCollisionBody getBody1()
	{
		return body1;
	}

	public IntersectData getIntersectData()
	{
		return intersectData;
	}

	public void setBody0(SimpleCollisionBody body0)
	{
		this.body0 = body0;
	}

	public void setBody1(SimpleCollisionBody body1)
	{
		this.body1 = body1;
	}

	public void setIntersectData(IntersectData intersectData)
	{
		this.intersectData = intersectData;
	}

	public void setTimestamp(float timestamp)
	{
		this.timestamp = timestamp;
	}

	public static SimpleCollisionData genOpposite(SimpleCollisionData src)
	{
		IntersectData srcIntersect = src.getIntersectData();
		IntersectData data = new IntersectData();
		Vector3f point = srcIntersect.getIntersectionPoint();
		Vector3f normal = srcIntersect.getIntersectionNormal();
		Vector3f mtv = srcIntersect.getMinTranslateVector();
		data.setIntersecting(srcIntersect.isIntersecting());
		data.setIntersectionNormal(normal == null ? null : new Vector3f(normal).negate());
		data.setIntersectionPoint(point == null ? null : new Vector3f(point));
		data.setMTV((mtv == null ? null : new Vector3f(mtv).negate()));
		SimpleCollisionData cd = new SimpleCollisionData(data, src.body1, src.body0, src.timestamp);
		return cd;
	}
}
