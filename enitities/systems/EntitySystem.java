package enitities.systems;

import enitities.Entity;
import enitities.components.EntityComponent;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/21/2016.
 */
public abstract class EntitySystem
{
	private Map<Class, Map> neededComponents = new HashMap<>();

	public abstract void tick();

	public abstract void render(MasterRenderer renderer);

	public abstract void setupNeededComponents();

	protected <E extends EntityComponent> void addNeededComponent(Class<E> componentClass, Map<Entity, E> componentMap)
	{
		neededComponents.put(componentClass, componentMap);
	}

	public void addEntity(Entity entity)
	{
		Map<Class, EntityComponent> matchingComponents = new HashMap<>();

		// better way
		for (Class neededCompClass : neededComponents.keySet())
			for (EntityComponent entityComponent : entity.getComponents())
				if (neededCompClass.isInstance(entityComponent))
					matchingComponents.put(neededCompClass, entityComponent);

		if (matchingComponents.size() == neededComponents.size())
		{
			for (Class className : matchingComponents.keySet())
			{
				Map<Entity, EntityComponent> componentMap = neededComponents.get(className);
				EntityComponent component = matchingComponents.get(className);
				componentMap.put(entity, component);
			}
		}
	}

	public void removeEntity(Entity e)
	{
		for (Map compMap : neededComponents.values())
			compMap.remove(e);
	}
}
