package particles;

import utils.math.Maths;
import utils.math.Transform;
import utils.math.linear.vector.Vector3f;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mjmcc on 12/27/2016.
 */
public class ParticleSystem
{
	private Transform transform;
	private Particle baseParticle;
	private List<Particle> particles;
	private int spawnAmount;
	private int spawnRate;
	private int lastSpawn;

	public ParticleSystem(Particle particle)
	{
		this.transform = new Transform();
		this.baseParticle = particle;
		this.spawnAmount = 1;
		this.spawnRate = 50;
		this.particles = new LinkedList<>();
	}

	public void update()
	{
		lastSpawn++; // maybe use seconds

		if (lastSpawn > spawnRate)
		{
			lastSpawn = 0;

			for (int i = 0; i < spawnAmount; i++)
			{
				Particle p = baseParticle.copy();

				p.setPositionPhysics(new PhysicsValue(new Vector3f(transform.getPosition()), new Vector3f(),
						new Vector3f(Maths.random(-0.0005f, 0.0005f), Maths.random(0, 0.0005f), Maths.random(-0.0005f, 0.0005f))));
				p.setScalePhysics(new PhysicsValue(new Vector3f(1), new Vector3f(Maths.random(0.0f, 0.25f)), new Vector3f()));

				ParticleManager.addParticle(p);
			}
		}
	}

	public void setPosition(Vector3f position)
	{
		this.transform.setPosition(position);
	}
}
