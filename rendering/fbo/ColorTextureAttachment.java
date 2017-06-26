package rendering.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import utils.math.linear.vector.Vector2f;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 4/4/2017.
 */
public class ColorTextureAttachment extends TextureAttachment
{
	private int colorBitSize = GL11.GL_RGB;

	public ColorTextureAttachment(Vector2f dimensions)
	{
		super(dimensions);
	}

	public ColorTextureAttachment(Vector2f dimensions, int colorBitSize)
	{
		super(dimensions);
		this.colorBitSize = colorBitSize;
	}

	@Override
	public int generate()
	{
		int texture = GL11.glGenTextures();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, colorBitSize, (int) dimensions.x(), (int) dimensions.y(),
				0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		return texture;
	}

	@Override
	public void attachTo(FboObject fbo)
	{
		fbo.bindFrameBuffer();
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, (GL30.GL_COLOR_ATTACHMENT0 + fbo.getColorAttachmentAmt()), id, 0);
		fbo.unbindFrameBuffer();
	}
}
