package enitities.components;

import enitities.Entity;
import rendering.Light;

/**
 * Light Emitter class
 *
 * Adds and controls a light that emits from the entity
 * */
public class LightEmitterComponent extends EntityComponent
{
	private Light light;

	public LightEmitterComponent(Entity parent, Light light)
	{
		super(parent);
		this.light = light;
	}

	public Light getLight()
	{
		return light;
	}

	public void setLight(Light light)
	{
		this.light = light;
	}
}
