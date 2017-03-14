package enitities.components;

import enitities.Entity;
import models.SpriteModel;
import rendering.RenderData;
import rendering.renderers.SpriteRenderData;

/**
 * Created by mjmcc on 1/11/2017.
 */
public class SpriteModelComponent extends RenderComponent
{
	private SpriteModel model;
	private SpriteRenderData renderData;

	public SpriteModelComponent(Entity parent, SpriteModel model, String animOn)
	{
		super(parent);
		this.model = model;
		this.renderData = new SpriteRenderData(animOn, parent.getPosition());
	}

	public SpriteModel getModel()
	{
		return model;
	}

	public void setModel(SpriteModel model)
	{
		this.model = model;
	}

	public SpriteRenderData getSpriteRenderData()
	{
		return renderData;
	}

	public void setRenderData(SpriteRenderData renderData)
	{
		this.renderData = renderData;
	}

	@Override
	public RenderData getRenderData()
	{
		return renderData;
	}
}
