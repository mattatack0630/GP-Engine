package particles;

import engine.Engine;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/27/2016.
 * <p>
 * The particle is an abstract class used to render affects like fireworks and smoke.
 * The particle uses an internal physics system, whose values can be set to control
 * the particles movement.
 */
public abstract class Particle
{
	// Used to render the particle
	protected ParticleRenderData renderData;

	// Used to control the particles deletion within a system
	protected float lifeSpan;
	protected float spawnedAt;

	// Used to control the particles physics/movement
	protected PhysicsValue scale;
	protected PhysicsValue position;
	protected PhysicsValue rotation;

	public Particle(float lifeSpan, Vector3f position, Vector3f rotation, Vector3f scale)
	{
		this.lifeSpan = lifeSpan;
		this.spawnedAt = Engine.getTime();

		this.position = new PhysicsValue(position);
		this.rotation = new PhysicsValue(rotation);
		this.scale = new PhysicsValue(scale);

		this.renderData = new ParticleRenderData(position, new Euler(rotation), scale);
		this.renderData.updateMatrix();
	}

	// Get the particles texture/ texture sheet
	public abstract int getTextureId();

	// Set the particles tile stage, updated per frame
	public abstract void updateTileStage();

	// Used to clone the particle in a system
	public abstract Particle copyParticle();

	/**
	 * Update the particle each frame, called by the particle system
	 */
	public void update(float time)
	{
		scale.update();
		position.update();
		rotation.update();

		updateTileStage();
		renderData.setScale(scale.getStaticVal());
		renderData.setPosition(position.getStaticVal());
		renderData.setRotation(new Euler(rotation.getStaticVal())); // X

		renderData.updateMatrix();
	}

	/* Getters and setters */

	public float getLifeSpan()
	{
		return lifeSpan;
	}

	public float getSpawnedAt()
	{
		return spawnedAt;
	}

	public ParticleRenderData getRenderData()
	{
		return renderData;
	}

	public void setLifeSpan(float lifeSpan)
	{
		this.lifeSpan = lifeSpan;
	}

	public void setScalePhysics(PhysicsValue scalePhysics)
	{
		this.scale = scalePhysics;
	}

	public void setPositionPhysics(PhysicsValue posPhysics)
	{
		this.position = posPhysics;
	}

	public void setRotationPhysics(PhysicsValue rotPhysics)
	{
		this.rotation = rotPhysics;
	}

	public PhysicsValue getSPhysics()
	{
		return scale;
	}

	public PhysicsValue getPPhysics()
	{
		return position;
	}

	public PhysicsValue getRPhysics()
	{
		return rotation;
	}

	public Vector3f getPosition()
	{
		return position.getStaticVal();
	}

	public Vector3f getPVelocity()
	{
		return position.getVelocityVal();
	}

	public Vector3f getPAcceleration()
	{
		return position.getAccelerationVal();
	}

	public void setPosition(Vector3f pos)
	{
		position.setStaticVal(pos);
	}

	public void setPVelocity(Vector3f vel)
	{
		position.setVelocityVal(vel);
	}

	public void setPAcceleration(Vector3f acc)
	{
		position.setAccelerationVal(acc);
	}

	public void setRenderData(ParticleRenderData renderData)
	{
		this.renderData = renderData;
	}
}
