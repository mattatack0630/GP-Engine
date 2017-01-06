package rendering;

import models.StaticModel;
import utils.math.geom.AABB;

/**
 * Created by mjmcc on 12/13/2016.
 */
public class StaticRenderObject extends RenderableObject
{
	private StaticModel staticModel;
	private RenderData renderData;

	public StaticRenderObject(StaticModel staticModel, RenderData renderData)
	{
		super(AABB.transform(staticModel.getBoundingBox(), renderData.getTransformMatrix()));
		this.staticModel = staticModel;
		this.renderData = renderData;
	}

	public void updateBounds()
	{
		bounds = AABB.transform(staticModel.getBoundingBox(), renderData.getTransformMatrix());
	}

	public StaticModel getStaticModel()
	{
		return staticModel;
	}

	public RenderData getRenderData()
	{
		return renderData;
	}

	@Override
	public String toString()
	{
		String s = "";

		s += "renderSrc: " + staticModel + ", ";
		s += "distCam: " + distToCamera + "\n";

		return s;
	}
}
