package rendering.fbo;

import org.lwjgl.opengl.*;
import utils.math.linear.vector.Vector2f;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 4/4/2017.
 */
public class DepthTextureAttachment extends TextureAttachment
{
	public DepthTextureAttachment(Vector2f dimensions)
	{
		super(dimensions);
	}

	@Override
	public int generate()
	{
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, (int) dimensions.x(), (int) dimensions.y(),
				0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		return texture;
	}

	@Override
	public void attachTo(FboObject fbo)
	{
		fbo.bindFrameBuffer();
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, id, 0);
		fbo.unbindFrameBuffer();
	}
}
