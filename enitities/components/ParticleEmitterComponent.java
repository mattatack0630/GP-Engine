package enitities.components;

import enitities.Entity;
import particles.ParticleSystem;

/**
 * Created by mjmcc on 12/28/2016.
 */
public class ParticleEmitterComponent extends EntityComponent
{
	private ParticleSystem system;

	public ParticleEmitterComponent(Entity parent, ParticleSystem system)
	{
		super(parent);
		this.system = system;
	}

	public ParticleSystem getSystem()
	{
		return system;
	}

	public void setSystem(ParticleSystem system)
	{
		this.system = system;
	}
}
