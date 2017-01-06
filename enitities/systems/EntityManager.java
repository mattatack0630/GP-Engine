package enitities.systems;

import enitities.Entity;
import rendering.renderers.MasterRenderer;

import java.util.List;

/**
 * Created by mjmcc on 12/21/2016.
 */
public class EntityManager
{
	// systems
	private ParticleEmitterSystem particleEmissionSystem = new ParticleEmitterSystem();
	private AnimatedRenderSystem animatedRenderSystem = new AnimatedRenderSystem();
	private LightEmissionSystem lightEmissionSystem = new LightEmissionSystem();
	private StaticRenderSystem staticRenderSystem = new StaticRenderSystem();
	private MovementSystem movementSystem = new MovementSystem();
	private PickingSystem pickingSystem = new PickingSystem();

	// Entities
	public List<Entity> entities;

	public EntityManager()
	{

	}

	public void addEntity(Entity e)
	{
		particleEmissionSystem.addEntity(e);
		animatedRenderSystem.addEntity(e);
		lightEmissionSystem.addEntity(e);
		staticRenderSystem.addEntity(e);
		movementSystem.addEntity(e);
		pickingSystem.addEntity(e);
	}

	public void removeEntity(Entity e)
	{
		particleEmissionSystem.removeEntity(e);
		animatedRenderSystem.removeEntity(e);
		lightEmissionSystem.removeEntity(e);
		staticRenderSystem.removeEntity(e);
		movementSystem.removeEntity(e);
		pickingSystem.removeEntity(e);
	}

	public void tick()
	{
		particleEmissionSystem.tick();
		animatedRenderSystem.tick();
		lightEmissionSystem.tick();
		staticRenderSystem.tick();
		movementSystem.tick();
		pickingSystem.tick();
	}

	public void render(MasterRenderer renderer)
	{
		particleEmissionSystem.render(renderer);
		animatedRenderSystem.render(renderer);
		lightEmissionSystem.render(renderer);
		staticRenderSystem.render(renderer);
		movementSystem.render(renderer);
		pickingSystem.render(renderer);
	}
}
