package input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import utils.math.linear.vector.Vector2f;

import java.util.ArrayList;

/**
 * Created by mjmcc on 10/27/2016.
 */
public class InputManager
{
	public static final int KEY_NONE = -1;
	public static final int MOUSE_NONE = -2;

	private static ArrayList<Integer> keysDown;
	private static ArrayList<Integer> keysReleasing;
	private static ArrayList<Integer> keysBeganPressing;
	private static int lastKeyPressed;

	private static int mouseWheelVal;
	private static Vector2f mouseCoords;
	private static ArrayList<Integer> mouseDown;
	private static ArrayList<Integer> mouseReleasing;
	private static ArrayList<Integer> mouseBeganPressing;
	private static int lastMousePressed;

	public static void init()
	{
		keysDown = new ArrayList<>();
		keysReleasing = new ArrayList<>();
		keysBeganPressing = new ArrayList<>();
		lastKeyPressed = KEY_NONE;


		mouseDown = new ArrayList<>();
		mouseReleasing = new ArrayList<>();
		mouseBeganPressing = new ArrayList<>();
		lastMousePressed = MOUSE_NONE;
	}

	public static void update()
	{
		// Keyboard Updates
		keysBeganPressing.clear();
		keysReleasing.clear();

		while (Keyboard.next())
		{
			int k = Keyboard.getEventKey();
			boolean state = Keyboard.getEventKeyState();
			if (state)
			{
				keysBeganPressing.add(k);
				keysDown.add(k);
				lastKeyPressed = k;
			} else
			{
				keysReleasing.add(k);
				keysDown.remove(keysDown.indexOf(k));
			}
		}

		// Mouse Updates
		mouseReleasing.clear();
		mouseBeganPressing.clear();
		mouseCoords = new Vector2f(Mouse.getX(), Mouse.getY());
		while (Mouse.next())
		{
			int m = Mouse.getEventButton();
			boolean state = Mouse.getEventButtonState();
			if (state)
			{
				mouseBeganPressing.add(m);
				mouseDown.add(m);
				lastMousePressed = m;
			} else
			{
				mouseReleasing.add(m);
				int index = mouseDown.indexOf(m);
				if (index != -1)
					mouseDown.remove(index);
			}
		}
	}

	// Key Queries
	public static boolean isKeyDown(int k)
	{
		return keysDown.contains(k);
	}

	public static boolean isKeyClicked(int k)
	{
		return keysBeganPressing.contains(k);
	}

	public static boolean isKeyReleased(int k)
	{
		return keysReleasing.contains(k);
	}

	public static int getLastKeyPressed()
	{
		return lastKeyPressed;
	}

	public static int getKeyCount()
	{
		return keysDown.size();
	}

	public static ArrayList<Integer> getKeysDown()
	{
		return keysDown;
	}

	public static ArrayList<Integer> getKeysReleased()
	{
		return keysReleasing;
	}

	public static ArrayList<Integer> getKeysClicked()
	{
		return keysBeganPressing;
	}

	// Button Queries
	public static Vector2f getCursorCoords()
	{
		return mouseCoords;
	}

	public static boolean isMouseButtonDown(int i)
	{
		return mouseDown.contains(i);
	}

	public static boolean isMouseButtonClicked(int i)
	{
		return mouseBeganPressing.contains(i);
	}

	public static ArrayList<Integer> getMouseClicked()
	{
		return mouseBeganPressing;
	}

	public static ArrayList<Integer> getMouseDown()
	{
		return mouseDown;
	}

	public static ArrayList<Integer> getMouseReleasing()
	{
		return mouseReleasing;
	}

	public static char getCharOfKey(int k)
	{
		return ' ';
	}
}
