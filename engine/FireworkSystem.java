package engine;

import particles.ColorParticle;
import particles.Particle;
import particles.ParticleSystem;
import particles.PhysicsValue;
import rendering.Color;
import utils.math.Maths;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 3/27/2017.
 */
public class FireworkSystem extends ParticleSystem
{
	private static final float GRAVITY = -0.005f;
	private static final float PART_LIFE = 5.0f;

	private float decConst;
	private FireParticle fireBase;
	private Particle chargeBase;
	private boolean expPrev;
	private int expAmount;
	private float minFVel;
	private float maxFVel;
	private float minCVel;
	private float maxCVel;

	public FireworkSystem(Color c)
	{
		this(c, 100, 0.1f, 2.5f, 0.95f, 100.0f, 200.0f);

	}

	public FireworkSystem(Color c, int expAmount, float minFVel, float maxFVel, float decConst, float minCPos, float maxCPos)
	{
		super(null);
		this.chargeBase = new ColorParticle(PART_LIFE, c, new Vector3f(), new Vector3f(), new Vector3f(1));
		this.fireBase = new FireParticle(PART_LIFE, c, new Vector3f(), new Vector3f(), new Vector3f(1));
		this.expPrev = false;
		this.expAmount = expAmount;
		this.decConst = decConst;
		this.minFVel = minFVel;
		this.maxFVel = maxFVel;
		this.minCVel = (float) Math.sqrt(-(2 * GRAVITY * (minCPos)));
		this.maxCVel = (float) Math.sqrt(-(2 * GRAVITY * (maxCPos)));

		// Controlled internally
		spawnAmount = 0;
		spawnRate = 0;

		reset();
	}

	@Override
	protected void onParticleSpawn(Particle p)
	{
		p.setPositionPhysics(new PhysicsValue(transform.getPosition(), new Vector3f(0, Maths.gaussianMM(minCVel, maxCVel), 0), new Vector3f(0, GRAVITY, 0)));
	}

	@Override
	protected void updateSystem()
	{
		boolean explode = chargeBase.getPVelocity().y() < 0;
		float scale = Maths.random(minFVel, maxFVel);

		if (explode && !expPrev)
		{
			for (int i = 0; i < expAmount; i++)
			{
				Particle p = spawnParticleClone(fireBase);
				PhysicsValue pv = p.getPPhysics();
				pv.setStaticVal(chargeBase.getPosition());
				pv.setVelocityVal(Maths.randVec3d().scale(scale));
				pv.setAccelerationVal(new Vector3f(0, GRAVITY - Maths.random(0.0f, 0.01f), 0));
			}
		}

		expPrev = explode;
	}

	public boolean isDone()
	{
		return expPrev;

	}

	public void reset()
	{
		chargeBase = spawnParticleClone(chargeBase);

	}

	class FireParticle extends ColorParticle
	{
		public FireParticle(float lifeSpan, Color c, Vector3f position, Vector3f rotation, Vector3f scale)
		{
			super(lifeSpan, c, position, rotation, scale);
		}

		@Override
		public void update(float time)
		{
			super.update(time);
			this.getPVelocity().scale(decConst);
			this.scale.setStaticVal(new Vector3f(1.0f - ((time - spawnedAt) / lifeSpan)));
		}

		@Override
		public Particle copyParticle()
		{
			ColorParticle copy = new FireParticle(0, null, new Vector3f(), new Vector3f(), new Vector3f());

			copy.setMask(this.mask);
			copy.setLifeSpan(this.lifeSpan);
			copy.setRenderData(this.renderData.copy());
			copy.setPositionPhysics(new PhysicsValue(this.position));
			copy.setRotationPhysics(new PhysicsValue(this.rotation));
			copy.setScalePhysics(new PhysicsValue(this.scale));

			return copy;
		}
	}
}
