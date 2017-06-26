package particles;

import engine.Engine;
import rendering.renderers.MasterRenderer;
import utils.math.Transform;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/27/2016.
 * <p>
 * The abstract particle system class is used to contain, update, and render a list of particles.
 * This class also controls the internal spawning of new particles, and deleting of dead ones.
 */
public abstract class ParticleSystem
{
	// The systems global transformation
	protected Transform transform;
	// The systems base particle, used to auto-generate new particle
	protected Particle baseParticle;
	// The list of particle used to keep track of this systems particles
	protected List<Particle> particles;
	// The amount of particles to spawn each time
	protected int spawnAmount;
	// The rate at which to spawn new particles
	protected float spawnRate;
	// The time when this system last spawned a particle
	protected float lastSpawn;

	public ParticleSystem(Particle particle)
	{
		this.particles = new ArrayList<>(100);
		this.transform = new Transform();
		this.baseParticle = particle;
		this.spawnAmount = 15;
		this.spawnRate = 0.15f;
	}

	/**
	 * Called upon a new particle spawn
	 * @param p tbe new particle spawned
	 */
	protected abstract void onParticleSpawn(Particle p);

	/**
	 * Called once a frame to update the system
	 * */
	protected abstract void updateSystem();

	/**
	 * Called to spawn a particle using the baseParticle
	 * */
	protected Particle spawnParticle()
	{
		return spawnParticleClone(baseParticle);

	}

	/**
	 * Called to spawn a particle based on base particle parameter
	 * @param tBase the base particle to clone and spawn
	 * */
	protected Particle spawnParticleClone(Particle tBase)
	{
		Particle p = tBase.copyParticle();

		p.setPosition(transform.getPosition());

		onParticleSpawn(p);

		particles.add(p);

		return p;
	}

	/**
	 * Set the base particle used to auto-spawn new particles
	 * @param p the new base particle
	 * */
	protected void setBaseParticle(Particle p)
	{
		baseParticle = p;

	}

	/**
	 * Checked once per frame to see if a new particle should be spawned
	 * */
	protected void checkSpawn()
	{
		if (Engine.getTime() - lastSpawn > spawnRate)
		{
			lastSpawn = Engine.getTime();

			for (int i = 0; i < spawnAmount; i++)
				spawnParticle();
		}
	}

	/**
	 * Called once per frame to update the system and check for new spawns
	 * */
	public void update()
	{
		checkSpawn();
		updateSystem();
		updateParticles();
	}

	/**
	 * update the particles every frame, also checks to delete dead particles
	 * */
	protected void updateParticles()
	{
		float time = Engine.getTime();

		for (int i = particles.size() - 1; i >= 0; i--)
		{
			Particle p = particles.get(i);
			p.update(time);

			// remove dead particles
			if (time - p.getSpawnedAt() >= p.getLifeSpan())
				particles.remove(p);
		}
	}

	/**
	 * render each particle once per frame
	 * */
	public void renderParticles(MasterRenderer renderer)
	{
		for (Particle p : particles)
			renderer.processParticle(p);
	}

	/**
	 * Set the system's position
	 * */
	public void setPosition(Vector3f position)
	{
		this.transform.setPosition(position);
	}
}
