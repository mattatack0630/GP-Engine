package particles;

import engine.Engine;
import rendering.Color;
import resources.TextureResource;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 3/26/2017.
 */
public class ColorParticle extends Particle
{
	// Load somewhere else
	private static TextureResource rectTexture = Engine.getResourceManager().directLoadResource(new TextureResource("rectangleTexture", "res/textures/trinket_textures/rectangle_texture.png"));

	protected int mask;

	public ColorParticle(float lifeSpan, Color color, Vector3f position, Vector3f rotation, Vector3f scale)
	{
		super(lifeSpan, position, rotation, scale);
		renderData.setCurrStage(new Vector4f(0, 0, 1, 1));
		renderData.setPostStage(new Vector4f(0, 0, 1, 1));
		renderData.setStageProgression(0);
		renderData.setColor(color);
		mask = rectTexture.getId();
	}

	@Override
	public void updateTileStage()
	{
	}

	@Override
	public Particle copyParticle()
	{
		ColorParticle copy = new ColorParticle(0, null, new Vector3f(), new Vector3f(), new Vector3f());

		copy.mask = this.mask;
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
		return mask;
	}

	public void setMask(TextureResource res)
	{
		mask = res.getId();

	}

	public void setMask(int id)
	{
		mask = id;

	}
}
