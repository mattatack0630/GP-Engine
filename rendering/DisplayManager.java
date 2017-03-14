package rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import utils.math.linear.vector.Vector2f;

/**
 * The DisplayManager Class
 * <p>
 * Class that creates, controls, and destroys the display
 */
public class DisplayManager
{
	private static SharedDrawable sharedGLContext;
	private static int FPS_CAP = 120;
	public static int HEIGHT = 600;
	public static int WIDTH = 1000;
	private static float aspectRatio;

	/**
	 * Create a display on initialization, called by Engine class
	 */
	public static void createDisplay()
	{
		ContextAttribs attribs = new ContextAttribs(3, 3) // Use openGL version 3.3
				.withForwardCompatible(true)
				.withProfileCore(true);

		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			//Display.setIcon(IconBuffer.load(ICON_FILE));
			Display.create(new PixelFormat(), attribs);

		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);

		try
		{
			sharedGLContext = new SharedDrawable(Display.getDrawable());
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}

		aspectRatio = (float) HEIGHT / (float) WIDTH;
	}

	/**
	 * Update called once per frame by the Engine
	 */
	public static void updateDisplay()
	{
		Display.sync(FPS_CAP);
		Display.update();
	}

	/**
	 * Called when close is requested
	 */
	public static void closeDisplay()
	{
		Display.destroy();
	}

	/**
	 * Call this on another thread to share this opengl context with that
	 * thread
	 */
	public static void shareGlContextOnThread()
	{
		try
		{
			sharedGLContext.makeCurrent();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	public static float getAspect()
	{
		return aspectRatio;
	}

	//meh
	public static Vector2f pixelToGlConversion(Vector2f pix)
	{
		Vector2f gl = new Vector2f(pix);
		gl.set((gl.x() / WIDTH * 2.0f) - 1.0f, 1.0f - (gl.y() / HEIGHT * 2.0f));
		return gl;
	}

	public static Vector2f glToPixelConversion()
	{
		return null;
	}
}
