package rendering.fbo;

import org.lwjgl.opengl.GL11;
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
