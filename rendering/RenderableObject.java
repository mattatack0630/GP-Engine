package rendering;

import utils.math.geom.AABB;
import utils.math.geom.AABBce;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/13/2016.
 */
public abstract class RenderableObject implements Comparable
{
	protected float distToCamera;
	protected AABB bounds;

	public RenderableObject(AABB bounds)
	{
		this.bounds = bounds;
		this.distToCamera = 0;
	}

	public RenderableObject()
	{
		this.bounds = new AABBce(new Vector3f(), new Vector3f());
		this.distToCamera = 0;
	}

	public AABB getBounds()
	{
		return bounds;
	}

	public void setBounds(AABB bounds)
	{
		this.bounds = bounds;
	}

	public void setDistToCamera(float d)
	{
		this.distToCamera = d;
	}

	public abstract void updateBounds();

	public AABB getUpdatedBoundingBox()
	{
		updateBounds();
		return bounds;
	}

	@Override
	public int compareTo(Object o)
	{
		if (o instanceof RenderableObject)
		{
			RenderableObject or = (RenderableObject) o;
			if (this.distToCamera > or.distToCamera)
				return -1;
			if (this.distToCamera < or.distToCamera)
				return 1;
		}

		return 0;
	}
}
