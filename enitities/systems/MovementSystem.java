package enitities.systems;

import enitities.Entity;
import enitities.components.VelocityComponent;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/21/2016.
 */
public class MovementSystem extends EntitySystem
{
	private Map<Entity, VelocityComponent> components;

	public MovementSystem()
	{
		this.components = new HashMap<>();
	}

	@Override
	public void tick()
	{
		for (Entity e : components.keySet())
		{
			VelocityComponent velComponent = components.get(e);
			e.setPosition(new Vector3f(e.getPosition()).add(velComponent.getVelocity()));
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
	}

	@Override
	public void setupNeededComponents()
	{
		super.addNeededComponent(VelocityComponent.class, components);
	}
}
