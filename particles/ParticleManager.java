package particles;

import rendering.camera.Camera;
import rendering.renderers.MasterRenderer;
import utils.Sorter;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * particles.Particle Manager Class
 * <p>
 * this class manages all of the particles on the screen
 * receives particles from the particle system class
 */
public class ParticleManager
{
	private static List<Particle> particles = new ArrayList<>();

	public static void render(MasterRenderer renderer)
	{
		for (Particle p : particles)
			renderer.processParticle(p);
	}

	public static void tick(Camera camera)
	{
		// for each particle
		for (int i = particles.size() - 1; i >= 0; i--)
		{
			Particle p = particles.get(i);
			p.update();

			Vector3f sub = Vector3f.sub(p.getPosition(), camera.getPosition(), null);
			p.setCameraDist(sub.lengthSquared());

			// remove dead particles
			if (p.getLife() >= p.getLifeSpan())
				removeParticle(p);
		}

		Sorter.insertionSort(particles);
	}

	public static void removeParticle(Particle p)
	{
		particles.remove(p);
	}

	public static void addParticle(Particle p)
	{
		particles.add(p);
	}
}
