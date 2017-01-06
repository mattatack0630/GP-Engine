package particles;

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
	private SpriteSheet spriteSheet;

	private float lifeTime;
	private float lifeSpan;

	private PhysicsValue position;
	private PhysicsValue rotation;
	private PhysicsValue scale;

	private float cameraDist;

	public Particle(float lifeSpan, SpriteSheet spriteSheet, Vector3f position, Vector3f rotation, Vector3f scale)
	{
		this.lifeTime = 0.0001f;
		this.lifeSpan = lifeSpan;
		this.spriteSheet = spriteSheet;
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

			float nlife = (lifeTime / lifeSpan);
			float ntile = (lifeSpan / spriteSheet.getTilesLength());

			int currTile = Math.min((int) (nlife * spriteSheet.getTilesLength()), spriteSheet.getTilesLength());
			int postTile = Math.min((currTile + 1), spriteSheet.getTilesLength());

			float progress = Maths.map(lifeTime, currTile * ntile, postTile * ntile, 0.0f, 1.0f);

			renderData.setStageProgression(progress);
			renderData.setCurrStage(spriteSheet.getTileMinMax(currTile));
			renderData.setPostStage(spriteSheet.getTileMinMax(postTile));

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
		return spriteSheet;
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
		copy.spriteSheet = this.spriteSheet;
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
