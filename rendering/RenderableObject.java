package rendering;

import utils.math.geom.AABB;

/**
 * Created by mjmcc on 12/13/2016.
 */
public class RenderableObject implements Comparable
{
	protected float distToCamera;
	protected AABB bounds;

	public RenderableObject(AABB bounds)
	{
		this.bounds = bounds;
		this.distToCamera = 0;
	}

	public AABB getBounds()
	{
		return bounds;
	}

	public void setDistToCamera(float d)
	{
		this.distToCamera = d;
	}

	public void updateBounds()
	{

	}

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
