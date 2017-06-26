package rendering.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 4/4/2017.
 */
public class ColorBufferAttachment extends BufferAttachment
{
	public ColorBufferAttachment(Vector2f dimensions)
	{
		super(dimensions);
	}

	@Override
	public int generate()
	{
		int colorBuffer = GL30.glGenRenderbuffers();

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, colorBuffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_RGBA8, (int) dimensions.x(), (int) dimensions.y());

		return colorBuffer;
	}

	@Override
	public void attachTo(FboObject fbo)
	{
		fbo.bindFrameBuffer();
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0 + fbo.getColorAttachmentAmt(), GL30.GL_RENDERBUFFER, id);
		fbo.unbindFrameBuffer();
	}
}