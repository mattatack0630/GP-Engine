package rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

/**
 * The DisplayManager Class
 *
 * Class that creates, controls, and destroys the display
 * */
public class DisplayManager {
	private static int FPS_CAP = 120;
	public static int HEIGHT = 600;
	public static int WIDTH = 800;

	/**
	 * Create a display on initialization, called by Engine class
	 * */
	public static void createDisplay(){
		ContextAttribs attribs = new ContextAttribs(3,3) // Use openGL version 3.3
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			//Display.setIcon(IconBuffer.load(ICON_FILE));
			Display.create(new PixelFormat(), attribs);

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}

	/**
	 * Update called once per frame by the Engine
	 * */
	public static void updateDisplay()
	{
		Display.sync(FPS_CAP);
		Display.update();
	}

	/**
	 * Called when close is requested
	 * */
	public static void closeDisplay(){
		Display.destroy();
	}
}
