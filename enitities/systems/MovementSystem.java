package enitities.systems;

import enitities.Entity;
import enitities.components.MovementComponent;
import rendering.renderers.MasterRenderer;
import utils.math.linear.rotation.Euler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/21/2016.
 */
public class MovementSystem extends EntitySystem
{
	private Map<Entity, MovementComponent> components;

	public MovementSystem()
	{
		this.components = new HashMap<>();
	}

	@Override
	public void tick()
	{
		for (Entity e : components.keySet())
		{
			MovementComponent component = components.get(e);
			e.setPosition(component.position);
			e.setRotation(new Euler(component.rotation));
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
	}

	@Override
	public void setupNeededComponents()
	{
		super.addNeededComponent(MovementComponent.class, components);
	}
}
