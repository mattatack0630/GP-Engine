package enitities.systems;

import enitities.Entity;
import rendering.renderers.MasterRenderer;

/**
 * Created by mjmcc on 12/21/2016.
 */
public abstract class EntitySystem
{
	public abstract void addEntity(Entity e);

	public abstract void removeEntity(Entity e);

	public abstract void tick();

	public abstract void render(MasterRenderer renderer);
}
