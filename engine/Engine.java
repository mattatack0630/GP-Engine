package engine;

import audio.AudioManager;
import audio.AudioResourcePool;
import audio.AudioStreamer;
import enitities.systems.EntityManager;
import gui.GuiManager;
import gui.Text.FontLoader;
import gui.Text.TextMeshGenerator;
import input.InputManager;
import input.KeyHandler;
import input.MouseRay;
import input.picker.PickingManager;
import particles.ParticleManager;
import rendering.DisplayManager;
import rendering.camera.Camera;
import rendering.camera.CameraController;
import rendering.camera.CameraLinearKeyController;
import rendering.renderers.Gizmos3D;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import resources.EngineFiles;
import resources.ResourceManager;
import utils.Timer;
import utils.math.linear.vector.Vector3f;

public class Engine
{
	private static boolean isRunning = false;

	private static MasterRenderer renderer;
	private static EntityManager entityManager;
	private static CameraController cameraController;
	private static Camera camera;
	public static Timer time;

	public static MouseRay ray;

	/**
	 * Initialization of static util classes, Ordering is key here!
	 **/
	public synchronized static void init()
	{
		isRunning = true;

		DisplayManager.createDisplay();
		EngineFiles.parseFromFile("res/index.gpm");
		TextMeshGenerator.init();
		ResourceManager.init();
		PickingManager.init();
		InputManager.init();
		FontLoader.init();
		GuiManager.init();
		AudioManager.init();
		AudioResourcePool.initResources();

		camera = new Camera(new Vector3f(), new Vector3f(), 0.1f, 1000f, (float) 70);
		cameraController = new CameraLinearKeyController(camera);
		renderer = new MasterRenderer(camera);
		entityManager = new EntityManager();
		time = new Timer();
		time.start();

		Gizmos3D.init(renderer);
		Trinket2D.init(renderer);

		ray = new MouseRay();
	}

	/**
	 * Called once per frame
	 * basic updates like inputFbo, gui, entities, particles
	 **/
	public static void tick()
	{
		InputManager.update();
		KeyHandler.update();
		ParticleManager.tick(camera);
		AudioManager.cleanUnusedResources();
		AudioStreamer.updateStreams();// TODO put this on a separate thread once update res manager
		GuiManager.tick();
		entityManager.tick();

		cameraController.tick();
		camera.update();

		ray.calculateRay(camera);

		PickingManager.doPickingCheck(renderer.getPickingFbo(), InputManager.getCursorCoords(), camera);
	}

	/**
	 * Called once per frame
	 **/
	public static void render()
	{
		ParticleManager.render(renderer);
		GuiManager.render(Engine.getRenderer());

		entityManager.render(renderer);
		renderer.render();

		DisplayManager.updateDisplay();
	}

	/**
	 * Clean up all of the resources
	 **/
	public synchronized static void stop()
	{
		isRunning = false;

		renderer.cleanUp();
		AudioResourcePool.cleanUp();
		AudioManager.cleanUp();
		ResourceManager.cleanUp();
		DisplayManager.closeDisplay();
	}

	/**
	 * @param camera to render from
	 **/
	public static void setCamera(Camera camera)
	{
		Engine.camera = camera;
	}

	public static EntityManager getEntityManager()
	{
		return entityManager;
	}

	public static MasterRenderer getRenderer()
	{
		return renderer;
	}

	public static float getTime()
	{
		return time.getTime();
	}

	public static boolean isRunning()
	{
		return isRunning;
	}

	public static Camera getCamera()
	{
		return camera;
	}

}
