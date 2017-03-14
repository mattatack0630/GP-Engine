package input;

import engine.Engine;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import rendering.DisplayManager;
import utils.math.linear.vector.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 10/27/2016.
 */
public class InputManager
{
	public static final int KEY_NONE = -1;
	public static final int MOUSE_NONE = -2;

	private Map<Integer, Character> keysDown;
	private Map<Integer, Character> keysReleasing;
	private Map<Integer, Character> keysBeganPressing;
	private Map<Integer, Float> keyDownTimeMap;
	private int lastKeyPressed;

	private Vector2f mouseCoordsPix;
	private Vector2f mouseCoordsGL;
	private ArrayList<Integer> mouseDown;
	private ArrayList<Integer> mouseReleasing;
	private ArrayList<Integer> mouseBeganPressing;

	public InputManager()
	{
		lastKeyPressed = KEY_NONE;
		keysDown = new HashMap<>();
		keysReleasing = new HashMap<>();
		keysBeganPressing = new HashMap<>();
		keyDownTimeMap = new HashMap<>();

		mouseCoordsGL = new Vector2f();
		mouseCoordsPix = new Vector2f();

		mouseDown = new ArrayList<>();
		mouseReleasing = new ArrayList<>();
		mouseBeganPressing = new ArrayList<>();
	}

	public void update()
	{
		// Keyboard Updates
		keysBeganPressing.clear();
		keysReleasing.clear();

		while (Keyboard.next())
		{
			int k = Keyboard.getEventKey();
			char c = Keyboard.getEventCharacter();
			boolean state = Keyboard.getEventKeyState();
			if (state)
			{
				keyDownTimeMap.put(k, Engine.getTime());
				keysBeganPressing.put(k, c);
				keysDown.put(k, c);
				lastKeyPressed = k;
			} else
			{
				keysReleasing.put(k, c);
				keysDown.remove(k);
				keyDownTimeMap.remove(k);
			}
		}

		// Mouse Updates
		mouseReleasing.clear();
		mouseBeganPressing.clear();
		mouseCoordsPix = new Vector2f(Mouse.getX(), Mouse.getY());
		mouseCoordsGL = DisplayManager.pixelToGlConversion(mouseCoordsPix);
		while (Mouse.next())
		{
			int m = Mouse.getEventButton();
			boolean state = Mouse.getEventButtonState();
			if (state)
			{
				mouseBeganPressing.add(m);
				mouseDown.add(m);
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
	public boolean isKeyDown(int k)
	{
		return keysDown.containsKey(k);
	}

	public boolean isKeyClicked(int k)
	{
		return keysBeganPressing.containsKey(k);
	}

	public boolean isKeyReleased(int k)
	{
		return keysReleasing.containsKey(k);
	}

	public int getLastKeyPressed()
	{
		return lastKeyPressed;
	}

	public int getKeyCount()
	{
		return keysDown.size();
	}

	public Map<Integer, Character> getKeysDown()
	{
		return keysDown;
	}

	public Map<Integer, Character> getKeysReleased()
	{
		return keysReleasing;
	}

	public Map<Integer, Character> getKeysClicked()
	{
		return keysBeganPressing;
	}

	public Map<Integer, Float> getKeyDownTimeMap()
	{
		return keyDownTimeMap;
	}

	// Button Queries
	public Vector2f getCursorCoords()
	{
		return mouseCoordsPix;
	}

	public Vector2f getGLCursorCoords()
	{
		return mouseCoordsGL;
	}

	public boolean isMouseButtonDown(int i)
	{
		return mouseDown.contains(i);
	}

	public boolean isMouseButtonClicked(int i)
	{
		return mouseBeganPressing.contains(i);
	}

	public ArrayList<Integer> getMouseClicked()
	{
		return mouseBeganPressing;
	}

	public ArrayList<Integer> getMouseDown()
	{
		return mouseDown;
	}

	public ArrayList<Integer> getMouseReleasing()
	{
		return mouseReleasing;
	}
}
