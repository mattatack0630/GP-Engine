package enitities;

import enitities.systems.*;
import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/21/2016.
 */
public class EntityManager
{
	// Entity systems
	public List<EntitySystem> systems;

	// Entities
	public List<Entity> entities;

	public EntityManager()
	{
		systems = new ArrayList<>();
		entities = new ArrayList<>();

		addSystem(new SoundEffectEmissionSystem());
		addSystem(new ParticleEmitterSystem());
		addSystem(new AnimatedRenderSystem());
		addSystem(new LightEmissionSystem());
		addSystem(new SpriteRenderSystem());
		addSystem(new StaticRenderSystem());
		addSystem(new MovementSystem());
		addSystem(new PickingSystem());

		setupSystems();
	}

	public void addSystem(EntitySystem entitySystem)
	{
		systems.add(entitySystem);
	}

	public void setupSystems()
	{
		for (EntitySystem system : systems)
			system.setupNeededComponents();
	}

	public void addEntity(Entity e)
	{
		for (EntitySystem entitySystem : systems)
			entitySystem.addEntity(e);
	}

	public void removeEntity(Entity e)
	{
		for (EntitySystem entitySystem : systems)
			entitySystem.removeEntity(e);
	}

	public void tick()
	{
		for (EntitySystem entitySystem : systems)
			entitySystem.tick();
	}

	public void render(MasterRenderer renderer)
	{
		for (EntitySystem entitySystem : systems)
			entitySystem.render(renderer);
	}
}