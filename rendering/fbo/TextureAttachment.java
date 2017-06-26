package rendering.fbo;

import org.lwjgl.opengl.GL11;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 12/9/2016.
 */
public abstract class TextureAttachment extends Attachment
{
	public TextureAttachment(Vector2f dimensions)
	{
		super(dimensions);
	}

	@Override
	public void cleanUp()
	{
		GL11.glDeleteTextures(id);
	}
}
