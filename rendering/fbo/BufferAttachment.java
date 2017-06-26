package rendering.fbo;

import org.lwjgl.opengl.GL30;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 12/9/2016.
 */
public abstract class BufferAttachment extends Attachment
{

	public BufferAttachment(Vector2f dimensions)
	{
		super(dimensions);
	}

	@Override
	public void cleanUp()
	{
		GL30.glDeleteRenderbuffers(id);
	}
}
