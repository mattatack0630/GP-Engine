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
	Map<Entity, StaticModelComponent> components;

	public StaticRenderSystem()
	{
		this.components = new HashMap<>();
	}

	@Override
	public void addEntity(Entity e)
	{
		StaticModelComponent component = e.getComponent(StaticModelComponent.class);
		if (component != null)
			components.put(e, component);
	}

	@Override
	public void removeEntity(Entity e)
	{
		components.remove(e);
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
