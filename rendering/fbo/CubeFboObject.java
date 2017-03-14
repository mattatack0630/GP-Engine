package rendering.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import rendering.post.CubeMap;

/**
 * Created by mjmcc on 11/25/2016.
 */
public class CubeFboObject extends FboObject
{
	private CubeMap cubeMap;

	public CubeFboObject(int size)
	{
		super(size, size, FboObject.DEPTH_BUFFER);
		this.cubeMap = new CubeMap(size);
		this.colorAttachments[colorsAttached++] = new TextureAttachment(cubeMap.getId());

		bindFrameBuffer();
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		unbindFrameBuffer();
	}

	public void bindFaceTexture(int face)
	{
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, CubeMap.FACES[face], cubeMap.getId(), 0);
	}

	public CubeMap getCubeMap()
	{
		return cubeMap;
	}
}
