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
	Map<Entity, ParticleEmitterComponent> particleComponents = new HashMap<>();

	@Override
	public void addEntity(Entity e)
	{
		ParticleEmitterComponent particleComponent = e.getComponent(ParticleEmitterComponent.class);

		if (particleComponent != null)
			particleComponents.put(e, particleComponent);
	}

	@Override
	public void removeEntity(Entity e)
	{
		particleComponents.remove(e);
	}

	@Override
	public void tick()
	{
		for (Entity e : particleComponents.keySet())
		{
			ParticleEmitterComponent particleComponent = particleComponents.get(e);
			ParticleSystem system = particleComponent.getSystem();
			system.setPosition(e.getPosition());
			system.update();
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{

	}
}
