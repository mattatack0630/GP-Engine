package enitities.components;

import enitities.Entity;
import rendering.RenderData;

/**
 * Created by mjmcc on 12/22/2016.
 */
public abstract class RenderComponent extends EntityComponent
{
	public RenderComponent(Entity parent)
	{
		super(parent);
	}

	public abstract RenderData getRenderData();
}
