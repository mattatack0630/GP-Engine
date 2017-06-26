package engine;

import audio.AudioManager;
import audio.AudioResourcePool;
import enitities.EntityManager;
import gui.GuiManager;
import gui.text.FontLoader;
import gui.text.TextMeshGenerator;
import gui_m3.GuiElementParser;
import input.InputManager;
import input.picker.PickingManager;
import org.lwjgl.opengl.Display;
import rendering.DisplayManager;
import rendering.camera.Camera;
import rendering.camera.CameraController;
import rendering.camera.CameraLinearKeyController;
import rendering.camera.PerspectiveCamera;
import rendering.renderers.Gizmos3D;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import resources.*;
import states.GameState;
import states.GameStateManager;
import utils.Timer;

import java.util.List;

public class Engine
{
	private static boolean isRunning = false;

	private static MasterRenderer renderer;

	private static GuiManager guiManager;
	private static InputManager inputManager;
	private static AudioManager audioManager;
	private static EntityManager entityManager;
	private static GameStateManager stateManager;
	private static PickingManager pickingManager;
	private static ResourceManager resourceManager;

	private static CameraController cameraController;
	private static Camera camera;
	private static Timer time;

	/**
	 * Initialization of static util classes, Ordering is key here!
	 **/
	public synchronized static void init()
	{
		isRunning = true;

		DisplayManager.createDisplay();//
		EngineFiles.parseFromFile("res/index.gpm");
		TextMeshGenerator.init();
		FontLoader.init();
		AudioManager.createOpenAL();
		AudioResourcePool.initResources();
		GuiElementParser.init();

		resourceManager = new ResourceManager();
		pickingManager = new PickingManager();
		stateManager = new GameStateManager();
		audioManager = new AudioManager();
		inputManager = new InputManager();
		guiManager = new GuiManager();

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
		stateManager.set(activeState);

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
		stateManager.update();
		entityManager.update();
		audioManager.update();
		guiManager.update();

		cameraController.update();
		camera.update();

		pickingManager.doPickingCheck(renderer.getObjectFbo(), inputManager.getCursorCoords(), camera);

		resourceManager.loadFromQueue();
		resourceManager.unloadFromQueue();
	}

	/**
	 * Called once per frame
	 **/
	public static void render()
	{
		stateManager.render(renderer);
		entityManager.render(renderer);
		guiManager.render(renderer);
		renderer.render();

		DisplayManager.updateDisplay();
		Gizmos3D.releaseRenderedObjs();
		Trinket2D.releaseRenderedTextures();
	}

	/**
	 * Clean up all of the resources
	 **/
	public synchronized static void stop()
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
	}

	public static boolean checkRunning()
	{
		if (Display.isCloseRequested())
			stop();

		return isRunning;
	}

	public static boolean isCloseRequested()
	{
		return Display.isCloseRequested();
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

	public static AudioManager getAudioManager()
	{
		return audioManager;
	}

	public static MasterRenderer getRenderManager()
	{
		return renderer;
	}

	public static GuiManager getGuiManager()
	{
		return guiManager;
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
		resPackage.addResource(new StaticModelResource("defualtModel", EngineFiles.STATIC_MODELS_PATH + "defualt_model.lime"));
		resPackage.addResource(new TextureResource("defualtTexture", EngineFiles.DIFFUSE_MAPS_PATH + "defualt_texture.png"));
		resPackage.addResource(new TextureResource("defualtNone", EngineFiles.INFO_MAPS_PATH + "defualt_info.png"));

		// Load Textures
		resPackage.addResource(new TextureResource("chrome_texture", EngineFiles.DIFFUSE_MAPS_PATH + "chrome.png"));
		resPackage.addResource(new TextureResource("barrel_texture", EngineFiles.DIFFUSE_MAPS_PATH + "barrel.png"));
		resPackage.addResource(new TextureResource("smoke", EngineFiles.PARTICLE_SHEETS_PATH + "smoke.png"));

		// Load cube textures
		resPackage.addResource(new CubeTextureResource("sky", EngineFiles.CUBE_TEXTURES_PATH + "sky/sky", ".png"));

		// Load Anim models
		resPackage.addResource(new AnimatedModelResource("barrel", EngineFiles.ANIMATED_MODELS_PATH + "barrel.lime"));
		resPackage.addResource(new AnimatedModelResource("blockBoy", EngineFiles.ANIMATED_MODELS_PATH + "blockboy.lime"));
		resPackage.addResource(new AnimatedModelResource("runModel", EngineFiles.ANIMATED_MODELS_PATH + "character_run_1.lime"));

		// Load Static models
		resPackage.addResource(new StaticModelResource("boneModel", EngineFiles.STATIC_MODELS_PATH + "boneModel.lime"));
		resPackage.addResource(new StaticModelResource("isoSphereModel", EngineFiles.STATIC_MODELS_PATH + "isosphere.lime"));
		resPackage.addResource(new StaticModelResource("planeModel", EngineFiles.STATIC_MODELS_PATH + "planeModel.lime"));
		resPackage.addResource(new StaticModelResource("barrelS", EngineFiles.STATIC_MODELS_PATH + "barrels_1.lime"));
		resPackage.addResource(new StaticModelResource("statue", EngineFiles.STATIC_MODELS_PATH + "statue.lime"));
		resPackage.addResource(new StaticModelResource("pyramid", EngineFiles.STATIC_MODELS_PATH + "tri_prism.lime"));
		resPackage.addResource(new StaticModelResource("teapot", EngineFiles.STATIC_MODELS_PATH + "teapot.lime"));
		resPackage.addResource(new StaticModelResource("quadModel", EngineFiles.STATIC_MODELS_PATH + "quad_model.lime"));

		// Load fonts
		resPackage.addResource(new FontResource("Geo", EngineFiles.FONTS_PATH + "Geo.fnt", EngineFiles.FONT_TEXTURES_PATH + "Geo.png"));
		resPackage.addResource(new FontResource("Arial", EngineFiles.FONTS_PATH + "arial.fnt", EngineFiles.FONT_TEXTURES_PATH + "arial.png"));
		resPackage.addResource(new FontResource("Segoe", EngineFiles.FONTS_PATH + "Segoe.fnt", EngineFiles.FONT_TEXTURES_PATH + "Segoe.png"));
		resPackage.addResource(new FontResource("SegoeSL", EngineFiles.FONTS_PATH + "SegoeSL.fnt", EngineFiles.FONT_TEXTURES_PATH + "SegoeSL.png"));
		resPackage.addResource(new FontResource("VinerHand", EngineFiles.FONTS_PATH + "VinerHand.fnt", EngineFiles.FONT_TEXTURES_PATH + "VinerHand.png"));

		return resPackage;
	}
}
