package engine;

import audio.*;
import enitities.Entity;
import enitities.components.AnimatedModelComponent;
import enitities.components.MovementComponent;
import enitities.components.ParticleEmitterComponent;
import enitities.components.PickingComponent;
import gui.GuiManager;
import input.InputManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import particles.Particle;
import particles.ParticleSystem;
import particles.SpriteSheet;
import rendering.Color;
import rendering.Light;
import rendering.renderers.Gizmos3D;
import resources.AnimatedModelResource;
import resources.EngineFiles;
import resources.ResourceManager;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Engine Test Driver Class
 */

public class GameManagingThread
{
	private static long then = System.currentTimeMillis();
	private static int framesPerSecond = 0;

	/**
	 * Main engine loop
	 */
	public static void main(String[] args)
	{
		Engine.init();
		Engine.getCamera().setPosition(new Vector3f(0, 5, 50));
		Light light = new Light(new Vector3f(0, 0, 10), Color.WHITE, Light.NO_ATTENUATION);

		String SCREEN_ONE = "S0";
		DebugGui gui = new DebugGui();

		GuiManager.switchScreen(SCREEN_ONE);
		GuiManager.addSceneToScreen(SCREEN_ONE, gui);

		///////////////////////////////////////NEW STUFF//////////////////////////////////////

		Particle p = new Particle(800,
				new SpriteSheet(ResourceManager.getResource("smoke"), new Vector2f(8)),
				new Vector3f(0, 10, 0), new Vector3f(), new Vector3f(1));
		ParticleSystem system = new ParticleSystem(p);

		AnimatedModelResource resource = ResourceManager.getResource("runModel");

		Entity e = new Entity();

		AnimatedModelComponent modelComponent = new AnimatedModelComponent(e, resource.getAnimatedModel());
		ParticleEmitterComponent particleComponent = new ParticleEmitterComponent(e, system);
		MovementComponent movementComponent = new MovementComponent(e);
		PickingComponent pickingComponent = new PickingComponent(e);

		e.addComponent(modelComponent);
		e.addComponent(pickingComponent);
		e.addComponent(movementComponent);
		//e.addComponent(particleComponent);

		Engine.getEntityManager().addEntity(e);

		Listener listener = new Listener(new Vector3f(), new Vector3f());
		Source source = Source.generatePointSource(new Vector3f(), 10);
		StreamSound sound = StreamSound.fromWavFile(EngineFiles.AUDIO_PATH + "door_converted.wav");
		sound.setLooping(false);
		AudioManager.setListener(listener);

		MusicPlayer player = new MusicPlayer();
		player.addTrackToEnd("res/audio/door_converted.wav");
		//player.addTrackToEnd("res/audio/thunder.wav");
		//player.addTrackToEnd("res/audio/battleSong.wav");

		//AudioManager.play(sound, source);
		//////////////////////////////////////////////////////////////////////////////////////

		while (!Display.isCloseRequested())
		{

			//////////////////////////////////NEW STUFF///////////////////////////////////////

			modelComponent.getAnimatedRenderData().setAnimationOn("armatureaction");
			modelComponent.getAnimatedRenderData().setTime((Engine.getTime() * 15) % 20);

			if (InputManager.isKeyDown(Keyboard.KEY_L))
				movementComponent.translate(Vector3f.FORWARD, -0.1f);

			if (gui.t0.isClicked())
				AudioManager.play(sound, source);
			if (gui.t1.isClicked())
				AudioManager.stop(sound, source);
			if (gui.t2.isClicked())
				AudioManager.pause(sound, source);
			if (gui.t3.isClicked())
				AudioManager.resume(sound, source);

			player.updatePlayer();
			listener.setPosition(Engine.getCamera().getPosition());
			//////////////////////////////////////////////////////////////////////////////////

			AudioManager.setListener(listener);
			light.setPosition(Engine.getCamera().getPosition());
			Engine.getRenderer().processLightSource(light);
			Gizmos3D.renderAxis();

			Engine.tick();
			Engine.render();

			//printFramesPerSecond();
		}

		System.out.println("Average FPS : " + avFPS);
		Engine.stop();
	}

	/***
	 * Log frames per second for performance tests
	 */
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
}










/*

		MeshGenerator generator = new MeshGenerator();
		VaoObject tvao = VaoLoader.loadModel(new float[0], new float[0], new float[0], new float[0], new int[0]);
		generator.generateMesh(20, 20, 20, tvao, 200, .25f, 1, 2);
		StaticModel m = new StaticModel(tvao, ResourceManager.getTextureResource("chrome_texture"),
				ResourceManager.getTextureResource("defualt_normal"),
				ResourceManager.getTextureResource("defualt_texture"));

* 		Entity testEntity = new Entity(new Vector3f(), new Vector3f(), new Vector3f(1, 1, 1));
		StaticModelComponent sc = new StaticModelComponent(testEntity, ResourceManager.getStaticModel("barrelS").getModel());
		MovementComponent mc = new MovementComponent(testEntity);
		testEntity.addComponent(sc);
		testEntity.addComponent(mc);


* 			mc.translate(MovementComponent.UP, 0.1f);
			if (InputManager.isKeyDown(Keyboard.KEY_J))
				mc.rotate(new Vector3f(0, 0, 0.05f));
			if (InputManager.isKeyDown(Keyboard.KEY_L))
				mc.rotate(new Vector3f(0, 0, -0.05f));
			testEntity.tick();
			testEntity.render(Engine.getRenderer());

			Engine.getRenderer().processTexturedModel(m, new RenderData(new Vector3f()));

			int v = (int)(gui.s2.getValue()*3+1);
			int v1 = (int)(gui.s3.getValue()*3+1);
			generator.generateMesh(15+v, 15+v, 5+v1, tvao, 200, gui.s1.getValue()/10f, 1, 2);
*
*
* **/