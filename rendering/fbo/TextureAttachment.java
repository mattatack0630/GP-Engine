package rendering.fbo;

import org.lwjgl.opengl.GL11;

/**
 * Created by mjmcc on 12/9/2016.
 */
public class TextureAttachment extends Attachment
{
	public TextureAttachment(int id)
	{
		super(id);
	}

	@Override
	public void cleanUp()
	{
		GL11.glDeleteTextures(id);
	}
}
