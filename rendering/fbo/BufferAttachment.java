package rendering.fbo;

import org.lwjgl.opengl.GL30;

/**
 * Created by mjmcc on 12/9/2016.
 */
public class BufferAttachment extends Attachment
{
	public BufferAttachment(int id)
	{
		super(id);
	}

	@Override
	public void cleanUp()
	{
		GL30.glDeleteRenderbuffers(id);
	}
}
