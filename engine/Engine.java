package engine;

import audio.AudioManager;
import audio.AudioResourcePool;
import enitities.EntityManager;
import gui.GuiManager;
import gui.Text.FontLoader;
import gui.Text.TextMeshGenerator;
import input.InputManager;
import input.picker.PickingManager;
import org.lwjgl.opengl.Display;
import particles.ParticleManager;
import rendering.DisplayManager;
import rendering.camera.Camera;
import rendering.camera.CameraController;
import rendering.camera.CameraLinearKeyController;
import rendering.camera.PerspectiveCamera;
import rendering.renderers.Gizmos3D;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import resources.*;
import utils.Timer;

import java.util.List;

public class Engine
{
	private static boolean isRunning = false;

	private static InputManager inputManager;
	private static AudioManager audioManager;
	private static EntityManager entityManager;
	private static GameStateManager stateManager;
	private static PickingManager pickingManager;
	private static ResourceManager resourceManager;

	private static MasterRenderer renderer;
	private static CameraController cameraController;
	private static Camera camera;

	private static Timer time;

	/**
	 * Initialization of static util classes, Ordering is key here!
	 **/
	public synchronized static void init()
	{
		DisplayManager.createDisplay();//
		EngineFiles.parseFromFile("res/index.gpm");
		TextMeshGenerator.init();
		FontLoader.init();
		AudioManager.createOpenAL();
		AudioResourcePool.initResources();
		GuiManager.init();//

		resourceManager = new ResourceManager();
		pickingManager = new PickingManager();
		stateManager = new GameStateManager();
		audioManager = new AudioManager();
		inputManager = new InputManager();

		camera = new PerspectiveCamera(0.1f, 1000f, (float) 70);
		cameraController = new CameraLinearKeyController(camera);
		renderer = new MasterRenderer(camera);
		entityManager = new EntityManager();
		time = new Timer();
		time.start();

		resourceManager.loadResourcePackage(getEngineResources(), true);

		Gizmos3D.init(renderer);
		Trinket2D.init(renderer);
	}

	/**
	 * Start game engine, after engine has been intialized and resources are ready.
	 * This method runs a loop that continues until the game is exited.
	 * (With window close or call to {@link Engine#stop()})
	 */
	public static void begin(List<GameState> states, GameState activeState)
	{
		stateManager.addStates(states);
		stateManager.initStates();
		stateManager.setState(activeState);
		isRunning = true;

		while (checkRunning())
		{
			tick();
			render();
			printFramesPerSecond();
		}
	}

	/**
	 * Called once per frame
	 * basic updates like inputFbo, gui, entities, particles
	 **/
	public static void tick()
	{
		inputManager.update();
		stateManager.tick();
		entityManager.tick();
		audioManager.update();

		ParticleManager.tick(camera);//
		GuiManager.tick();//

		cameraController.tick();
		camera.update();

		pickingManager.doPickingCheck(renderer.getPickingFbo(), inputManager.getCursorCoords(), camera);

		resourceManager.runBackgroundManagement();
	}

	/**
	 * Called once per frame
	 **/
	public static void render()
	{
		stateManager.render(renderer);
		entityManager.render(renderer);
		ParticleManager.render(renderer);//
		GuiManager.render(renderer);//

		renderer.render();

		DisplayManager.updateDisplay();
	}

	/**
	 * Clean up all of the resources
	 **/
	private synchronized static void stop()
	{
		isRunning = false;

		renderer.cleanUp();
		stateManager.cleanUp();
		audioManager.cleanUp();
		resourceManager.cleanUp();

		AudioResourcePool.cleanUp();
		AudioManager.destroyAL();

		DisplayManager.closeDisplay();

		System.out.println("Average FPS : " + avFPS);

		// Find better solution soon
		System.exit(0);
	}

	public static boolean checkRunning()
	{
		if (Display.isCloseRequested())
			stop();

		return isRunning;
	}

	public static void setCameraController(CameraController _controller)
	{
		cameraController = _controller;
	}

	public static void setCamera(Camera _camera)
	{
		camera = _camera;
	}

	public static ResourceManager getResourceManager()
	{
		return resourceManager;
	}

	public static PickingManager getPickingManager()
	{
		return pickingManager;
	}

	public static GameStateManager getStateManager()
	{
		return stateManager;
	}

	public static EntityManager getEntityManager()
	{
		return entityManager;
	}

	public static InputManager getInputManager()
	{
		return inputManager;
	}

	public static MasterRenderer getRenderer()
	{
		return renderer;
	}

	public static AudioManager getAudio()
	{
		return audioManager;
	}

	public static float getTime()
	{
		return time.getTime();
	}

	public static Camera getCamera()
	{
		return camera;
	}

	/***
	 * Log frames per second for performance tests
	 */
	private static long then = System.currentTimeMillis();
	private static int framesPerSecond = 0;
	private static float avFPS = 120;

	private static void printFramesPerSecond()
	{
		framesPerSecond++;
		long now = System.currentTimeMillis();
		if (now - then > 1000)
		{
			then = now;
			System.out.println(framesPerSecond);
			avFPS = ((avFPS + framesPerSecond) / 2f);
			framesPerSecond = 0;
		}
	}

	// Initial resources need for the engine to run
	private static ResourcePackage getEngineResources()
	{
		ResourcePackage resPackage = new ResourcePackage();

		//Load defaults before everything
		resPackage.addResource(new StaticModelResource("defualtModel", "defualt_model"));
		resPackage.addResource(new TextureResource("defualtTexture", "diffuse_maps/defualt_texture"));
		resPackage.addResource(new TextureResource("defualtNone", "info_maps/defualt_info"));

		// Load Textures
		resPackage.addResource(new TextureResource("chrome_texture", "diffuse_maps/chrome"));
		resPackage.addResource(new TextureResource("barrel_texture", "diffuse_maps/barrel"));
		resPackage.addResource(new TextureResource("smoke", "particle_sheets/smoke"));

		// Load cube textures
		resPackage.addResource(new CubeTextureResource("sky", "sky/sky"));

		// Load Anim models
		resPackage.addResource(new AnimatedModelResource("barrel", "barrel"));
		resPackage.addResource(new AnimatedModelResource("blockBoy", "blockboy"));
		resPackage.addResource(new AnimatedModelResource("runModel", "character_run_1"));

		// Load Static models
		resPackage.addResource(new StaticModelResource("boneModel", "boneModel"));
		resPackage.addResource(new StaticModelResource("isoSphereModel", "isosphere"));
		resPackage.addResource(new StaticModelResource("planeModel", "planeModel"));
		resPackage.addResource(new StaticModelResource("barrelS", "barrels_1"));
		resPackage.addResource(new StaticModelResource("statue", "statue"));
		resPackage.addResource(new StaticModelResource("pyramid", "tri_prism"));
		resPackage.addResource(new StaticModelResource("teapot", "teapot"));

		// Load fonts
		resPackage.addResource(new FontResource("Arial", "arial"));
		resPackage.addResource(new FontResource("Segoe", "Segoe"));
		resPackage.addResource(new FontResource("SegoeSL", "SegoeSL"));
		resPackage.addResource(new FontResource("VinerHand", "VinerHand"));

		// Load audio
		resPackage.addResource(new SoundResource("knock", "door_converted"));

		// Load Sprite Sheets
		resPackage.addResource(new SpriteSheetResource("cowSheet", "cow_sheet"));
		resPackage.addResource(new SpriteSheetResource("smokeSheet", "smoke_sheet"));
		return resPackage;
	}
}
