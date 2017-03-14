package rendering.camera;

import engine.Engine;
import input.InputManager;
import org.lwjgl.input.Keyboard;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/1/2016.
 */
public abstract class CameraKeyController extends CameraController
{
	// speed values
	private float linearSpeed;
	private float rotationalSpeed;

	// the keys used to move
	// [forward, back, up, down, left, right, upR, downR, leftR, rightR]
	private int[] keyMap;

	public CameraKeyController(Camera camera)
	{
		super(camera);

		rotationalSpeed = 0.02f;
		linearSpeed = 0.2f;

		// Setting default for now
		keyMap = new int[]{Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_NUMPAD1, Keyboard.KEY_NUMPAD0, Keyboard.KEY_A,
				Keyboard.KEY_D, Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT};
	}

	@Override
	public void tick()
	{
		InputManager inputManager = Engine.getInputManager();

		if (inputManager.isKeyDown(keyMap[0]))
			translate(new Vector3f(0, 0, -linearSpeed));
		if (inputManager.isKeyDown(keyMap[1]))
			translate(new Vector3f(0, 0, linearSpeed));
		if (inputManager.isKeyDown(keyMap[2]))
			translate(new Vector3f(0, linearSpeed, 0));
		if (inputManager.isKeyDown(keyMap[3]))
			translate(new Vector3f(0, -linearSpeed, 0));
		if (inputManager.isKeyDown(keyMap[4]))
			translate(new Vector3f(-linearSpeed, 0, 0));
		if (inputManager.isKeyDown(keyMap[5]))
			translate(new Vector3f(linearSpeed, 0, 0));
		if (inputManager.isKeyDown(keyMap[6]))
			rotate(new Vector3f(-rotationalSpeed, 0, 0));
		if (inputManager.isKeyDown(keyMap[7]))
			rotate(new Vector3f(rotationalSpeed, 0, 0));
		if (inputManager.isKeyDown(keyMap[8]))
			rotate(new Vector3f(0, -rotationalSpeed, 0));
		if (inputManager.isKeyDown(keyMap[9]))
			rotate(new Vector3f(0, rotationalSpeed, 0));
	}

	public abstract void translate(Vector3f translation);

	public abstract void rotate(Vector3f rotation);
}
