package rendering;

import models.StaticModel;
import shadows.Shadowable;
import utils.math.geom.AABBmm;

/**
 * Created by mjmcc on 12/13/2016.
 */
public class StaticRenderObject extends RenderableObject implements Shadowable
{
	private StaticModel staticModel;
	private RenderData renderData;

	public StaticRenderObject(StaticModel staticModel, RenderData renderData)
	{
		super(AABBmm.transform(staticModel.getBoundingBox(), renderData.getTransformMatrix()));
		this.staticModel = staticModel;
		this.renderData = renderData;
	}

	public void updateBounds()
	{
		bounds = AABBmm.transform(staticModel.getBoundingBox(), renderData.getTransformMatrix());
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
	// Interface to shadow rendering
	public StaticModel getShadowModel()
	{
		return staticModel;
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
