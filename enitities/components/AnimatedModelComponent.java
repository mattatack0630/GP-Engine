package enitities.components;

import animation.AnimatedRenderData;
import enitities.Entity;
import models.AnimatedModel;
import rendering.RenderData;

/**
 * Created by mjmcc on 12/21/2016.
 */
public class AnimatedModelComponent extends RenderComponent
{
	private AnimatedModel model;
	private AnimatedRenderData renderData;

	public AnimatedModelComponent(Entity parent, AnimatedModel model)
	{
		super(parent);
		this.model = model;
		this.renderData = new AnimatedRenderData();
	}

	public AnimatedModel getModel()
	{
		return model;
	}

	public void setModel(AnimatedModel model)
	{
		this.model = model;
	}

	public AnimatedRenderData getAnimatedRenderData()
	{
		return renderData;
	}

	@Override
	public RenderData getRenderData()
	{
		return renderData;
	}

	public void setRenderData(AnimatedRenderData renderData)
	{
		this.renderData = renderData;
	}

}
