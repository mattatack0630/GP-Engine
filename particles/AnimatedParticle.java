package particles;

import engine.Engine;
import models.SpriteSequence;
import utils.math.Maths;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 3/26/2017.
 */
public class AnimatedParticle extends Particle
{
	private SpriteSequence spriteSequence;

	public AnimatedParticle(float lifeSpan, SpriteSequence spriteSequence, Vector3f position, Vector3f rotation, Vector3f scale)
	{
		super(lifeSpan, position, rotation, scale);
		this.spriteSequence = spriteSequence;
	}

	@Override
	public void updateTileStage()
	{
		int tileCount = spriteSequence.getTileCount() - 1;
		float lifeTime = Engine.getTime() - spawnedAt;
		float nlife = (lifeTime / lifeSpan);
		float ntile = (lifeSpan / tileCount);
		int currTile = Maths.clamp((int) (nlife * tileCount), 0, tileCount);
		int postTile = Maths.clamp((currTile + 1), 0, tileCount);
		float progress = Maths.map(lifeTime, currTile * ntile, postTile * ntile, 0.0f, 1.0f);

		renderData.setCurrStage(spriteSequence.getTileMinMax(currTile));
		renderData.setPostStage(spriteSequence.getTileMinMax(postTile));
		renderData.setStageProgression(progress);
	}

	@Override
	public Particle copyParticle()
	{
		AnimatedParticle copy = new AnimatedParticle(0, null, new Vector3f(), new Vector3f(), new Vector3f());

		copy.lifeSpan = this.lifeSpan;
		copy.spriteSequence = this.spriteSequence;
		copy.renderData = this.renderData.copy();
		copy.position = new PhysicsValue(this.position);
		copy.rotation = new PhysicsValue(this.rotation);
		copy.scale = new PhysicsValue(this.scale);

		return copy;
	}

	@Override
	public int getTextureId()
	{

		return spriteSequence.getSheet().getTextureID();
	}
}
