package enitities.systems;

import enitities.Entity;
import enitities.components.StaticModelComponent;
import rendering.RenderData;
import rendering.StaticRenderObject;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/21/2016.
 */
public class StaticRenderSystem extends EntitySystem
{
	private Map<Entity, StaticModelComponent> components;

	public StaticRenderSystem()
	{
		components = new HashMap<>();
	}

	@Override
	public void setupNeededComponents()
	{
		super.addNeededComponent(StaticModelComponent.class, components);
	}

	@Override
	public void tick()
	{
		for (Entity e : components.keySet())
		{
			StaticModelComponent component = components.get(e);
			RenderData renderData = component.getRenderData();
			renderData.setScale(e.getScale());
			renderData.setPosition(e.getPosition());
			renderData.setRotation(e.getRotation());
			renderData.updateMatrix();
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
		for (Entity e : components.keySet())
		{
			StaticModelComponent component = components.get(e);
			renderer.processStaticModel(new StaticRenderObject(component.getModel(), component.getRenderData()));
		}
	}
}
