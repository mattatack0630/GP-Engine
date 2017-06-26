package particles;

import resources.TextureResource;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 3/26/2017.
 */
public class TextureParticle extends Particle
{
	TextureResource textureResource;

	public TextureParticle(float lifeSpan, TextureResource texture, Vector3f position, Vector3f rotation, Vector3f scale)
	{
		super(lifeSpan, position, rotation, scale);
		this.textureResource = texture;
		this.renderData.setCurrStage(new Vector4f(0, 0, 1, 1));
		this.renderData.setPostStage(new Vector4f(0, 0, 1, 1));
		this.renderData.setStageProgression(0);
	}

	@Override
	public void updateTileStage()
	{

	}

	@Override
	public Particle copyParticle()
	{
		TextureParticle copy = new TextureParticle(0, textureResource, new Vector3f(), new Vector3f(), new Vector3f());

		copy.lifeSpan = this.lifeSpan;
		copy.renderData = this.renderData.copy();
		copy.position = new PhysicsValue(this.position);
		copy.rotation = new PhysicsValue(this.rotation);
		copy.scale = new PhysicsValue(this.scale);

		return copy;
	}

	@Override
	public int getTextureId()
	{
		return textureResource.getId();
	}
}
