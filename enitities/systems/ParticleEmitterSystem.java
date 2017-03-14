package enitities.systems;

import enitities.Entity;
import enitities.components.ParticleEmitterComponent;
import particles.ParticleSystem;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/28/2016.
 */
public class ParticleEmitterSystem extends EntitySystem
{
	private Map<Entity, ParticleEmitterComponent> component;

	public ParticleEmitterSystem()
	{
		component = new HashMap<>();
	}

	@Override
	public void tick()
	{
		for (Entity e : component.keySet())
		{
			ParticleEmitterComponent particleComponent = component.get(e);
			ParticleSystem system = particleComponent.getSystem();
			system.setPosition(e.getPosition());
			system.update();
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
	}

	@Override
	public void setupNeededComponents()
	{
		super.addNeededComponent(ParticleEmitterComponent.class, component);
	}
}
