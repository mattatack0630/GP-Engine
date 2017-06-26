package rendering.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 4/4/2017.
 */
public class ColorBufferAttachmentMS extends ColorBufferAttachment
{
	private static final int SAMPLE_COUNT = 3;

	public ColorBufferAttachmentMS(Vector2f dimensions)
	{
		super(dimensions);
	}

	@Override
	public int generate()
	{
		int renderBuffer = GL30.glGenRenderbuffers();

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBuffer);

		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, SAMPLE_COUNT, GL11.GL_RGBA8,
				(int) dimensions.x(), (int) dimensions.y());

		GL11.glEnable(GL13.GL_MULTISAMPLE);

		return renderBuffer;
	}
}
