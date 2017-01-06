package enitities.systems;

import animation.AnimatedRenderData;
import enitities.Entity;
import enitities.components.AnimatedModelComponent;
import rendering.AnimatedRenderObject;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/21/2016.
 */
public class AnimatedRenderSystem extends EntitySystem
{
	Map<Entity, AnimatedModelComponent> components;

	public AnimatedRenderSystem()
	{
		this.components = new HashMap<>();
	}

	@Override
	public void addEntity(Entity e)
	{
		AnimatedModelComponent component = e.getComponent(AnimatedModelComponent.class);
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
			AnimatedModelComponent component = components.get(e);
			AnimatedRenderData renderData = component.getAnimatedRenderData();
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
			AnimatedModelComponent component = components.get(e);
			renderer.processAnimatedModel(new AnimatedRenderObject(component.getModel(), component.getAnimatedRenderData()));
		}
	}
}
