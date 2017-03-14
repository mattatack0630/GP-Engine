package rendering;

import animation.AnimatedRenderData;
import models.AnimatedModel;
import models.StaticModel;
import shadows.Shadowable;
import utils.math.geom.AABB;
import utils.math.geom.AABBmm;

/**
 * Created by mjmcc on 12/13/2016.
 */
public class AnimatedRenderObject extends RenderableObject implements Shadowable
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
		bounds = AABBmm.transform(animatedModel.getBoundingBox(), renderData.getTransformMatrix());
	}

	public AnimatedModel getAnimatedModel()
	{
		return animatedModel;
	}

	public AnimatedRenderData getRenderData()
	{
		return renderData;
	}


	@Override
	// Interface to add shadow casting
	public StaticModel getShadowModel()
	{
		return animatedModel;
	}
}
