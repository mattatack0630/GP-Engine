package enitities.components;

import enitities.Entity;

/**
 * <p>
 * This class along with its System class adds functionality such as movement
 * to the base entity, using a component based architecture
 *
 * The Component class holds data, while the System class runs the logic and can access multiple
 * components of an entity
 */

public abstract class EntityComponent
{
	// the parent entity that utilizes this component
	protected Entity parent;

	public EntityComponent(Entity parent)
	{
		this.parent = parent;
	}

	public Entity getParent()
	{
		return parent;
	}
}
