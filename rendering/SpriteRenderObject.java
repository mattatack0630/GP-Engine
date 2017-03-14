package rendering;

import models.SpriteModel;
import rendering.renderers.SpriteRenderData;
import utils.math.geom.AABBmm;

/**
 * Created by mjmcc on 1/9/2017.
 */
public class SpriteRenderObject extends RenderableObject
{
	private SpriteModel spriteModel;
	private SpriteRenderData renderData;

	public SpriteRenderObject(SpriteModel spriteModel, SpriteRenderData renderData)
	{
		super(AABBmm.transform(spriteModel.getBoundingBox(), renderData.getTransformMatrix()));
		this.renderData = renderData;
		this.spriteModel = spriteModel;
	}

	@Override
	public void updateBounds()
	{
		bounds = AABBmm.transform(spriteModel.getBoundingBox(), renderData.getTransformMatrix());
	}

	public SpriteRenderData getRenderData()
	{
		return renderData;
	}

	public SpriteModel getSpriteModel()
	{
		return spriteModel;
	}

}
