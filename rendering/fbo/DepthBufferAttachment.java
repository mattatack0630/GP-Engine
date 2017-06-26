package rendering.fbo;

import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 4/4/2017.
 */
public class DepthBufferAttachment extends BufferAttachment
{
	public DepthBufferAttachment(Vector2f dimensions)
	{
		super(dimensions);
	}

	@Override
	public int generate()
	{
		int depthBuffer = GL30.glGenRenderbuffers();

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, (int) dimensions.x(), (int) dimensions.y());

		return depthBuffer;
	}

	@Override
	public void attachTo(FboObject fbo)
	{
		fbo.bindFrameBuffer();
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, id);
		fbo.unbindFrameBuffer();
	}
}
