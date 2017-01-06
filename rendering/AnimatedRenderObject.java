package rendering;

import animation.AnimatedRenderData;
import models.AnimatedModel;
import utils.math.geom.AABB;

/**
 * Created by mjmcc on 12/13/2016.
 */
public class AnimatedRenderObject extends RenderableObject
{
	private AnimatedModel animatedModel;
	private AnimatedRenderData renderData;

	public AnimatedRenderObject(AnimatedModel animatedModel, AnimatedRenderData renderData)
	{
		super(AABB.transform(animatedModel.getBoundingBox(), renderData.getTransformMatrix()));
		this.animatedModel = animatedModel;
		this.renderData = renderData;
	}

	@Override
	public void updateBounds()
	{
		bounds = AABB.transform(animatedModel.getBoundingBox(), renderData.getTransformMatrix());
	}

	public AnimatedModel getStaticModel()
	{
		return animatedModel;
	}

	public AnimatedRenderData getRenderData()
	{
		return renderData;
	}
}
