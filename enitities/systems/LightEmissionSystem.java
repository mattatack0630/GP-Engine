package enitities.systems;

import enitities.Entity;
import enitities.components.LightEmitterComponent;
import rendering.Light;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/22/2016.
 */
public class LightEmissionSystem extends EntitySystem
{
	private Map<Entity, LightEmitterComponent> components;

	public LightEmissionSystem()
	{
		components = new HashMap<>();
	}

	@Override
	public void tick()
	{
		for (Entity e : components.keySet())
		{
			LightEmitterComponent lightEmitterComponent = components.get(e);
			Light light = lightEmitterComponent.getLight();
			light.setPosition(e.getPosition());
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
		for (Entity e : components.keySet())
		{
			LightEmitterComponent lightEmitterComponent = components.get(e);
			Light light = lightEmitterComponent.getLight();
			renderer.processLightSource(light);
		}
	}

	@Override
	public void setupNeededComponents()
	{
		super.addNeededComponent(LightEmitterComponent.class, components);
	}
}
