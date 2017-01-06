package enitities.components;

import enitities.Entity;
import models.StaticModel;
import rendering.RenderData;

/**
 * StaticModel Component Class
 * <p>
 * Renders a vaoObject at the entity's position
 */

public class StaticModelComponent extends RenderComponent
{

	private StaticModel model;
	private RenderData renderData;

	/**
	 * @param parent the entity parent
	 * @param model  the StaticModel to render following the entity position
	 */
	public StaticModelComponent(Entity parent, StaticModel model)
	{
		super(parent);
		this.model = model;
		this.renderData = new RenderData(parent.getPosition());
	}

	public RenderData getRenderData()
	{
		return renderData;
	}

	public StaticModel getModel()
	{
		return model;
	}
}
