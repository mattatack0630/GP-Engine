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
	Map<Entity, LightEmitterComponent> lightComponents = new HashMap<>();

	@Override
	public void addEntity(Entity e)
	{
		LightEmitterComponent lightEmitterComponent = e.getComponent(LightEmitterComponent.class);

		if (lightEmitterComponent != null)
			lightComponents.put(e, lightEmitterComponent);
	}

	@Override
	public void removeEntity(Entity e)
	{
		lightComponents.remove(e);
	}

	@Override
	public void tick()
	{
		for (Entity e : lightComponents.keySet())
		{
			LightEmitterComponent lightEmitterComponent = lightComponents.get(e);
			Light light = lightEmitterComponent.getLight();
			light.setPosition(e.getPosition());
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
		for (Entity e : lightComponents.keySet())
		{
			LightEmitterComponent lightEmitterComponent = lightComponents.get(e);
			Light light = lightEmitterComponent.getLight();
			renderer.processLightSource(light);
		}
	}
}
