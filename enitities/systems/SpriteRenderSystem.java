package enitities.systems;

import enitities.Entity;
import enitities.components.SpriteModelComponent;
import rendering.SpriteRenderObject;
import rendering.renderers.MasterRenderer;
import rendering.renderers.SpriteRenderData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 1/11/2017.
 */
public class SpriteRenderSystem extends EntitySystem
{
	private Map<Entity, SpriteModelComponent> components;

	public SpriteRenderSystem()
	{
		this.components = new HashMap<>();
	}

	@Override
	public void tick()
	{
		for (Entity e : components.keySet())
		{
			SpriteModelComponent component = components.get(e);
			SpriteRenderData renderData = component.getSpriteRenderData();
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
			SpriteModelComponent component = components.get(e);
			renderer.processSpriteModel(new SpriteRenderObject(component.getModel(), component.getSpriteRenderData()));
		}
	}

	@Override
	public void setupNeededComponents()
	{
		super.addNeededComponent(SpriteModelComponent.class, components);
	}
}
