package particles;

import models.SpriteSequence;
import models.SpriteSheet;
import rendering.renderers.MasterRenderer;
import utils.math.Maths;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/27/2016.
 */
public class Particle implements Comparable
{
	private ParticleRenderData renderData;
	private SpriteSequence spriteSequence;

	private float lifeTime;
	private float lifeSpan;

	private PhysicsValue position;
	private PhysicsValue rotation;
	private PhysicsValue scale;

	private float cameraDist;

	public Particle(float lifeSpan, SpriteSequence spriteSequence, Vector3f position, Vector3f rotation, Vector3f scale)
	{
		this.lifeTime = 0.0001f;
		this.lifeSpan = lifeSpan;
		this.spriteSequence = spriteSequence;
		this.position = new PhysicsValue(position);
		this.rotation = new PhysicsValue(rotation);
		this.scale = new PhysicsValue(scale);
		this.renderData = new ParticleRenderData(position, new Euler(rotation), scale);
		this.cameraDist = Float.MAX_VALUE;
	}

	public void update()
	{
		if (lifeTime < lifeSpan)
		{
			lifeTime++;
			scale.update();
			position.update();
			rotation.update();

			int tileCount = spriteSequence.getTileCount() - 1;
			float nlife = (lifeTime / lifeSpan);
			float ntile = (lifeSpan / tileCount);
			int currTile = Maths.clamp((int) (nlife * tileCount), 0, tileCount);
			int postTile = Maths.clamp((currTile + 1), 0, tileCount);
			float progress = Maths.map(lifeTime, currTile * ntile, postTile * ntile, 0.0f, 1.0f);

			renderData.setCurrStage(spriteSequence.getTileMinMax(currTile));
			renderData.setPostStage(spriteSequence.getTileMinMax(postTile));
			renderData.setStageProgression(progress);

			renderData.setScale(scale.getStaticVal());
			renderData.setPosition(position.getStaticVal());
			renderData.setRotation(new Euler(rotation.getStaticVal()));

			renderData.updateMatrix();
		}
	}

	public void render(MasterRenderer renderer)
	{
		renderer.processParticle(this);
	}

	public void setCameraDist(float cameraDist)
	{
		this.cameraDist = cameraDist;
	}

	public float getLife()
	{
		return lifeTime;
	}

	public float getLifeSpan()
	{
		return lifeSpan;
	}

	public SpriteSheet getParticleTexture()
	{
		return spriteSequence.getSheet();
	}

	public ParticleRenderData getRenderData()
	{
		return renderData;
	}

	public void setPositionPhysics(PhysicsValue val)
	{
		this.position = val;
	}

	public void setScalePhysics(PhysicsValue scalePhysics)
	{
		this.scale = scalePhysics;
	}

	public Particle copy()
	{
		Particle copy = new Particle(0, null, new Vector3f(), new Vector3f(), new Vector3f());

		copy.lifeSpan = this.lifeSpan;
		copy.lifeTime = this.lifeTime;
		copy.spriteSequence = this.spriteSequence;
		copy.renderData = this.renderData.copy();
		copy.position = new PhysicsValue(this.position);
		copy.rotation = new PhysicsValue(this.rotation);
		copy.scale = new PhysicsValue(this.scale);

		return copy;
	}

	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Particle)
		{
			Particle other = (Particle) o;
			if (other.cameraDist > this.cameraDist)
				return -1;
			if (other.cameraDist < this.cameraDist)
				return 1;
		}
		return 0;
	}

	public Vector3f getPosition()
	{
		return position.getStaticVal();
	}
}
