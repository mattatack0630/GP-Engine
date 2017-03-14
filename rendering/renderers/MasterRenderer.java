package rendering.renderers;

import engine.Engine;
import gui.GuiTexture;
import gui.Text.GuiText;
import models.SpriteSheet;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import particles.Particle;
import particles.ParticleRenderData;
import rendering.*;
import rendering.camera.Camera;
import rendering.fbo.FboObject;
import rendering.fbo.MSFboObject;
import rendering.post.CubeMap;
import rendering.post.PostAffectInstance;
import rendering.post.PostProcessor;
import resources.CubeTextureResource;
import utils.Sorter;
import utils.math.geom.AABB;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer
{
	// counts vertices rendered per frame
	public static int verticesRendered;
	public static int modelsRendered;

	// Post Processing Stuff
	private PostProcessor postProcessor = new PostProcessor();

	// MultiSampled screen fbo, for anti-aliasing affect
	private MSFboObject msScreen;

	// Main screen texture fbo, used to apply post processing before rendering to screen
	private FboObject screen;

	// Picking screen used in picking Manager
	private FboObject pickingFbo;

	// Shadow Map fbo to render a shadow map
	public static final boolean RENDER_SHADOWS = false;
	public static final int SHADOW_MAP_SIZE = 1024;
	private ShadowMapRenderer shadowMapRenderer;
	public FboObject shadowFbo;

	// Screen frame clear color
	public static final Color CLEAR_COLOR = Color.BLACK;

	// Maximum amount of lights that can affect an object
	public static final int MAX_LIGHTS = 5;

	private Camera renderCamera;

	// renderers
	private ParticleRenderer particleRenderer = new ParticleRenderer();
	private Sprite2DRenderer spriteRenderer = new Sprite2DRenderer();
	private CubeMapRenderer cubeMapRenderer = new CubeMapRenderer();
	private ModelRenderer modelRenderer = new ModelRenderer();
	private GuiRenderer GuiRenderer = new GuiRenderer();

	// Renderable Object lists
	private List<SpriteRenderObject> spriteRenderables = new ArrayList<>();
	private List<StaticRenderObject> staticRenderables = new ArrayList<>();
	private List<AnimatedRenderObject> animatedRenderables = new ArrayList<>();

	private Map<SpriteSheet, List<ParticleRenderData>> particles = new HashMap<>();

	private List<PostAffectInstance> postAffects = new ArrayList<>();
	private List<GuiTexture> guiTextures = new ArrayList<>();
	private List<GuiText> guiFont = new ArrayList<>();
	private List<Light> lights = new ArrayList<>();

	public MasterRenderer(Camera camera)
	{
		msScreen = new MSFboObject(Display.getWidth(), Display.getHeight(), FboObject.DEPTH_COLOR_BUFFERS);
		msScreen.addAttachments(FboObject.RENDER_BUFFER);
		msScreen.finishSetup();

		screen = new FboObject(Display.getWidth(), Display.getHeight(), FboObject.DEPTH_COLOR_TEXTURES);
		screen.finishSetup();

		pickingFbo = new FboObject(Display.getWidth(), Display.getHeight(), FboObject.COLOR_TEXTURE);
		pickingFbo.finishSetup();

		shadowFbo = new FboObject(SHADOW_MAP_SIZE, SHADOW_MAP_SIZE, FboObject.DEPTH_TEXTURE);
		shadowFbo.finishSetup();

		shadowMapRenderer = new ShadowMapRenderer(shadowFbo);

		renderCamera = camera;

		// OpenGL prep
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	/***
	 * Main rendering method
	 ***/
	public void render()
	{
		// Render shadow map
		if (RENDER_SHADOWS)
			shadowMapRenderer.render(renderCamera, lights.get(0)); // setElements sun later
		Matrix4f shadowMapConversion = shadowMapRenderer.getConversionMat();

		// Get/Generate environment map(s)
		CubeTextureResource environmentRes = Engine.getResourceManager().getResource("sky");
		CubeMap environmentMap = environmentRes.getCubeMap();

		// Renders to a anti-aliasing msFbo
		prepare();

		cubeMapRenderer.renderCubeMap(environmentMap, renderCamera);
		modelRenderer.renderAnimatedModels(animatedRenderables, lights, environmentMap, renderCamera, shadowFbo, shadowMapConversion);
		modelRenderer.renderStaticModels(staticRenderables, lights, environmentMap, renderCamera, shadowFbo, shadowMapConversion);
		spriteRenderer.render(spriteRenderables, lights, renderCamera);
		particleRenderer.renderParticles(particles, renderCamera);

		// Resolves msFbo and copies it to the screen texture
		msScreen.resolveTo(0, screen);

		// Resolves msFbo and copies picking attachment to the picking texture
		msScreen.resolveTo(1, pickingFbo);

		// Do Post processing on screen texture
		doPostProcessing();

		// Render gui
		GuiRenderer.renderTextures(guiTextures);
		GuiRenderer.renderGuiText(guiFont);

		// Clear lists for next render, possibly change later?
		animatedRenderables.clear();
		staticRenderables.clear();
		spriteRenderables.clear();
		guiTextures.clear();
		postAffects.clear();
		particles.clear();
		guiFont.clear();
		lights.clear();

		if (Engine.getTime() % 1.0f < 0.01f)
			System.out.println("Rendered : " + verticesRendered + " vertices, " + modelsRendered + " models");
		verticesRendered = 0;
		modelsRendered = 0;
	}

	private void prepare()
	{
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		msScreen.bindFrameBuffer();
		msScreen.clear(CLEAR_COLOR);
	}

	private void doPostProcessing()
	{

		postProcessor.prepare();
		FboObject mutableScreen = screen;

		for (PostAffectInstance instance : postAffects)
			mutableScreen = instance.callAffect(mutableScreen);

		postProcessor.renderToDisplay(mutableScreen);
		postProcessor.finish();
	}

	public void cleanUp()
	{
		screen.cleanUp();
		msScreen.cleanUp();
		pickingFbo.cleanUp();
		GuiRenderer.cleanUp();
		modelRenderer.cleanUp();
		spriteRenderer.cleanUp();
		particleRenderer.cleanUp();
		shadowMapRenderer.cleanUp();
	}

	/***
	 * Render Object Processing
	 ***/

	public void processParticle(Particle p)
	{
		List<ParticleRenderData> batch = particles.get(p.getParticleTexture());
		if (batch == null)
		{
			batch = new ArrayList<>();
			particles.put(p.getParticleTexture(), batch);
		}
		batch.add(p.getRenderData());
	}

	public void processStaticModel(StaticRenderObject renderObject)
	{
		RenderData renderData = renderObject.getRenderData();
		renderData.updateMatrix();

		AABB bounds = renderObject.getUpdatedBoundingBox();

		if (!renderData.shouldCheckBounds() || renderCamera.inView(bounds))
		{
			Vector3f subDist = Vector3f.sub(renderCamera.getPosition(), renderData.getPosition(), null);
			renderObject.setDistToCamera(subDist.lengthSquared());
			Sorter.dynamicSort(staticRenderables, renderObject);
		}

		if (RENDER_SHADOWS)
			shadowMapRenderer.addShadowable(renderObject);
	}

	public void processAnimatedModel(AnimatedRenderObject renderObject)
	{
		RenderData renderData = renderObject.getRenderData();
		renderData.updateMatrix();

		AABB bounds = renderObject.getUpdatedBoundingBox();

		if (!renderData.shouldCheckBounds() || renderCamera.inView(bounds))
		{
			Vector3f subDist = Vector3f.sub(renderCamera.getPosition(), renderData.getPosition(), null);
			renderObject.setDistToCamera(subDist.lengthSquared());
			Sorter.dynamicSort(animatedRenderables, renderObject);
		}

		if (RENDER_SHADOWS)
			shadowMapRenderer.addShadowable(renderObject);
	}

	public void processSpriteModel(SpriteRenderObject renderObject)
	{
		spriteRenderables.add(renderObject);
	}

	public void processGuiTexture(GuiTexture guiTexture)
	{
		guiTextures.add(guiTexture);
	}

	public void processPostAffect(PostAffectInstance instance)
	{
		postAffects.add(instance);
	}

	public void processLightSource(Light light)
	{
		lights.add(light);
	}

	public void processGuiText(GuiText text)
	{
		guiFont.add(text);
	}

	/**
	 * Getters and Setters
	 **/

	public FboObject getPickingFbo()
	{
		return pickingFbo;
	}

	public void setRenderCamera(Camera renderCamera)
	{
		this.renderCamera = renderCamera;
	}
}