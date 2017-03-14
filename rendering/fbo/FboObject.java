package rendering.fbo;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import rendering.Color;
import utils.math.linear.vector.Vector2f;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by mjmcc on 9/27/2016.
 */
public class FboObject
{
	private static final int MAX_ATTACHMENT = 10;
	private static int boundFbo = 0;

	public static final int NO_INDEX = -1;
	public static final FboObject NONE = new FboObject(0, 0);
	public static final FboObject SCREEN = new FboObject();

	public static final int RENDER_BUFFER = 1;
	public static final int DEPTH_BUFFER = 2;
	public static final int COLOR_TEXTURE = 4;
	public static final int DEPTH_TEXTURE = 8;
	public static final int DEPTH_COLOR_TEXTURES = 12;
	public static final int DEPTH_COLOR_BUFFERS = 3;

	protected Vector2f dimensions;

	protected int frameBuffer;

	public Attachment[] colorAttachments;
	public Attachment depthAttachments;
	public int colorsAttached;

	private FboObject()
	{
		this.dimensions = new Vector2f(Display.getWidth(), Display.getHeight());

		colorAttachments = new Attachment[MAX_ATTACHMENT];
		colorsAttached = 1;

		frameBuffer = 0;
	}

	public FboObject(int width, int height)
	{
		this.dimensions = new Vector2f(width, height);

		colorAttachments = new Attachment[MAX_ATTACHMENT];
		colorsAttached = 0;

		frameBuffer = createFrameBuffer();
	}

	public FboObject(int width, int height, int attachmentBits)
	{
		this(width, height);

		addAttachments(attachmentBits);
	}

	public void finishSetup()
	{
		resetDrawBuffers();
	}

	public void addAttachments(int attachBits)
	{
		if ((RENDER_BUFFER & attachBits) == RENDER_BUFFER)
		{
			colorAttachments[colorsAttached] = new BufferAttachment(createRenderBuffer());
			colorsAttached++;
		}

		if ((COLOR_TEXTURE & attachBits) == COLOR_TEXTURE)
		{
			colorAttachments[colorsAttached] = new TextureAttachment(createTexture());
			colorsAttached++;
		}

		if ((DEPTH_TEXTURE & attachBits) == DEPTH_TEXTURE)
			depthAttachments = new TextureAttachment(createDepthTexture());

		if ((DEPTH_BUFFER & attachBits) == DEPTH_BUFFER)
			depthAttachments = new BufferAttachment(createDepthBuffer());
	}

	public int getFrameBuffer()
	{
		return frameBuffer;
	}

	public void bindFrameBuffer()
	{
		if (boundFbo != frameBuffer)
		{
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
			GL11.glViewport(0, 0, (int) dimensions.x(), (int) dimensions.y());
		}

		boundFbo = frameBuffer;
	}

	public void unbindFrameBuffer()
	{
		if (boundFbo == frameBuffer)
		{
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}

		boundFbo = 0;
	}

	protected int createFrameBuffer()
	{
		//generate name for frame buffer
		int frameBuffer = GL30.glGenFramebuffers();
		//create the framebuffer
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
		//init draw buffer to none
		GL20.glDrawBuffers(GL11.GL_NONE);

		return frameBuffer;
	}

	public int getDepthAttachment()
	{
		return depthAttachments.getId();
	}

	public int getColorAttachment(int i)
	{
		return colorAttachments[i].getId();
	}

	public int createRenderBuffer()
	{
		return 0;
	}

	protected int createDepthBuffer()
	{
		bindFrameBuffer();
		int depthBuffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, (int) dimensions.x(), (int) dimensions.y());
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBuffer);
		return depthBuffer;
	}

	protected int createTexture()
	{
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, (int) dimensions.x(), (int) dimensions.y(),
				0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, (GL30.GL_COLOR_ATTACHMENT0 + colorsAttached), texture, 0);
		return texture;
	}

	protected int createDepthTexture()
	{
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, (int) dimensions.x(), (int) dimensions.y(),
				0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, texture, 0);

		return texture;
	}

	protected void resetDrawBuffers()
	{
		bindFrameBuffer();
		IntBuffer drawBuffs = BufferUtils.createIntBuffer(2);

		//for (int i = 0; i < colorsAttached; i++)
		//	drawBuffs.put(GL30.GL_COLOR_ATTACHMENT0 + i);
		drawBuffs.put(GL30.GL_COLOR_ATTACHMENT0);
		drawBuffs.put(GL30.GL_COLOR_ATTACHMENT1);
		drawBuffs.flip();
		GL20.glDrawBuffers(drawBuffs);

		unbindFrameBuffer();
	}

	public void cleanUp()
	{
		for (Attachment c : colorAttachments)
			if (c != null) c.cleanUp();

		if (depthAttachments != null)
			depthAttachments.cleanUp();

		if (frameBuffer != NO_INDEX)
			GL30.glDeleteFramebuffers(frameBuffer);

		frameBuffer = NO_INDEX; // Set all to no index

	}

	public Vector2f getDimensions()
	{
		return dimensions;
	}

	public void clear(Color clearColor)
	{
		GL11.glClearColor(clearColor.getR(), clearColor.getG(), clearColor.getB(), clearColor.getA());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
	}
}
