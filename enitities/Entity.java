package enitities;

import enitities.components.EntityComponent;
import utils.math.Transform;
import utils.math.linear.rotation.Euler;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Entity Class
 * <p>
 * Every entity uses this base class and adds
 * EntityComponents to its components list to add functionality
 **/

public class Entity
{
	private List<EntityComponent> components = new ArrayList<>();
	private Transform transform;

	public Entity(Vector3f position, Rotation rotation, Vector3f scale)
	{
		this.transform = new Transform(position, rotation, scale);
	}

	public Entity()
	{
		this(new Vector3f(), new Euler(), new Vector3f(1));
	}

	public void addComponent(EntityComponent component)
	{
		this.components.add(component);
	}

	public List<EntityComponent> getComponents()
	{
		return components;
	}

	public Vector3f getPosition()
	{
		return transform.getPosition();
	}

	public Rotation getRotation()
	{
		return transform.getRotation();
	}

	public Vector3f getScale()
	{
		return transform.getScale();
	}

	public void setRotation(Rotation rotation)
	{
		this.transform.setRotation(rotation);
	}

	public void setPosition(Vector3f position)
	{
		this.transform.setPosition(position);
	}

	public void setScale(Vector3f scale)
	{
		this.transform.setScale(scale);
	}

	public <E extends EntityComponent> E getComponent(Class classType)
	{
		for (EntityComponent component : components)
		{
			if (classType.isInstance(component))
			{
				return (E) component;
			}
		}

		return null;
	}


}