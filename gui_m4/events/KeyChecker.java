package gui_m4.events;

import engine.Engine;
import gui_m4.events.KeyUsable;
import input.InputManager;

import java.util.Map;

/**
 * Created by mjmcc on 3/13/2017.
 */
public class KeyChecker
{
	private static final float DEF_REPRESS_TIME = 1.0f;
	private static final float DEF_REPRESS_RATE = 0.1f;

	private float repressTime = DEF_REPRESS_TIME;
	private float repressRate = DEF_REPRESS_RATE;
	private KeyUsable keyUser;

	public KeyChecker(KeyUsable user)
	{
		this.keyUser = user;
	}

	public void checkKey()
	{
		InputManager inputManager = Engine.getInputManager();
		Map<Integer, Character> keysPressed = inputManager.getKeysDown();
		Map<Integer, Character> keysClicked = inputManager.getKeysClicked();
		Map<Integer, Character> keysReleased = inputManager.getKeysReleased();
		Map<Integer, Float> keyDownTimeMap = inputManager.getKeyDownTimeMap();


		for (Integer i : keysPressed.keySet())
		{
			char c = keysPressed.get(i);
			keyUser.onKeyPress(i, c);

			float downTime = Engine.getTime() - keyDownTimeMap.get(i);
			if (downTime > repressTime && downTime % repressRate < 0.01)
			{
				keyUser.onKeyRepress(i, c);
			}
		}

		for (Integer i : keysClicked.keySet())
			keyUser.onKeyClick(i, keysClicked.get(i));


		for (Integer i : keysReleased.keySet())
			keyUser.onKeyRelease(i, keysReleased.get(i));
	}

	public void setRepressTime(float repressTime)
	{
		this.repressTime = repressTime;
	}

	public void setRepressRate(float repressRate)
	{
		this.repressRate = repressRate;
	}
}
