package rendering.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

/**
 * Created by mjmcc on 11/16/2016.
 */
public class MSFboObject extends FboObject
{
	private static final int SAMPLE_COUNT = 3;

	public MSFboObject(int width, int height)
	{
		super(width, height);
		bindFrameBuffer();
		unbindFrameBuffer();
	}

	public MSFboObject(int width, int height, int attachmentBits)
	{
		super(width, height, attachmentBits);
		/*colorAttachments[colorsAttached] = new BufferAttachment(createRenderBuffer());
		depthAttachments = new BufferAttachment(createDepthBuffer());
		colorsAttached++;*/
		//colorBuffer = new BufferAttachment(createRenderBuffer());
		//depthBuffer = createDepthBuffer();
	}

	@Override
	public int createRenderBuffer()
	{
		int renderBuffer = GL30.glGenRenderbuffers();

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBuffer);

		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, SAMPLE_COUNT, GL11.GL_RGBA8,
				(int) dimensions.x(), (int) dimensions.y());

		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, (GL30.GL_COLOR_ATTACHMENT0 + colorsAttached),
				GL30.GL_RENDERBUFFER, renderBuffer);

		GL11.glEnable(GL13.GL_MULTISAMPLE);

		return renderBuffer;
	}

	@Override
	protected int createDepthBuffer()
	{
		int depthBuffer = GL30.glGenRenderbuffers();

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);

		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, SAMPLE_COUNT, GL14.GL_DEPTH_COMPONENT24,
				(int) dimensions.x(), (int) dimensions.y());

		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBuffer);

		GL11.glEnable(GL13.GL_MULTISAMPLE);

		return depthBuffer;
	}

	public void resolveTo(int attachIndex, FboObject f)
	{
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, f.frameBuffer);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, frameBuffer);
		GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0 + attachIndex);
		GL30.glBlitFramebuffer(
				0, 0, (int) dimensions.x(), (int) dimensions.y(),
				0, 0, (int) f.dimensions.x(), (int) f.dimensions.y(),
				GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
	}
}
