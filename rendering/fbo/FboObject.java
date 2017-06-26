package rendering.fbo;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.Color;
import utils.math.linear.vector.Vector2f;

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

	public void finishSetup()
	{

		resetDrawBuffers();
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

	public void addColorAttachment(Attachment attachment)
	{
		colorAttachments[colorsAttached] = attachment;
		attachment.setId(attachment.generate());
		attachment.attachTo(this);
		colorsAttached++;
	}

	public void setDepthAttachment(Attachment attachment)
	{
		this.depthAttachments = attachment;
		attachment.setId(attachment.generate());
		attachment.attachTo(this);
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

	public void clear(Color clearColor)
	{
		GL11.glClearColor(clearColor.getR(), clearColor.getG(), clearColor.getB(), clearColor.getA());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
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

	// Getters and Setters
	public Vector2f getDimensions()
	{

		return dimensions;
	}

	public int getDepthAttachment()
	{

		return depthAttachments.getId();
	}

	public int getColorAttachment(int i)
	{

		return colorAttachments[i].getId();
	}

	public int getColorAttachmentAmt()
	{

		return colorsAttached;
	}
}
