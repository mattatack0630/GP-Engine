package rendering.renderers;

import engine.Engine;
import gui.AnimatedGuiTexture;
import gui.GuiRenderable;
import gui.GuiTexture;
import gui.text.GuiText;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import particles.Particle;
import particles.ParticleRenderData;
import rendering.*;
import rendering.camera.Camera;
import rendering.fbo.*;
import rendering.post.CubeMap;
import rendering.post.PostAffectInstance;
import rendering.post.PostProcessor;
import resources.CubeTextureResource;
import utils.Sorter;
import utils.math.geom.AABB;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

import java.util.*;

public class MasterRenderer
{
	// counts vertices rendered per frame
	public static int verticesRendered;
	public static int modelsRendered;

	// Post Processing Stuff
	private PostProcessor postProcessor = new PostProcessor();

	// MultiSampled screen fbo, for anti-aliasing affect
	private MSFboObject msScreen;

	// Main screen texture fbos, used to apply post processing before rendering to screen
	private FboObject screen0;
	private FboObject screen1;

	// Picking screen used in picking Manager
	private FboObject objectFbo;

	// Shadow Map fbo to render a shadow map
	public static final boolean RENDER_SHADOWS = false;
	public static final int SHADOW_MAP_SIZE = 512;
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
	private GuiRenderer guiRenderer = new GuiRenderer();

	// Renderable Object lists
	private List<SpriteRenderObject> spriteRenderables = new ArrayList<>();
	private List<StaticRenderObject> staticRenderables = new ArrayList<>();
	private List<AnimatedRenderObject> animatedRenderables = new ArrayList<>();
	private Map<Integer, List<ParticleRenderData>> particles = new HashMap<>();

	private List<PostAffectInstance> postAffects = new ArrayList<>();
	private LinkedList<GuiRenderable> guiRenderables = new LinkedList<>();
	private List<Light> lights = new ArrayList<>();

	public MasterRenderer(Camera camera)
	{
		msScreen = new MSFboObject(Display.getWidth(), Display.getHeight());
		msScreen.addColorAttachment(new ColorBufferAttachmentMS(msScreen.getDimensions()));
		msScreen.addColorAttachment(new ColorBufferAttachmentMS(msScreen.getDimensions()));
		msScreen.setDepthAttachment(new DepthBufferAttachmentMS(msScreen.getDimensions()));
		msScreen.finishSetup();

		screen0 = new FboObject(Display.getWidth(), Display.getHeight());
		screen0.addColorAttachment(new ColorTextureAttachment(screen0.getDimensions()));
		screen0.setDepthAttachment(new DepthTextureAttachment(screen0.getDimensions()));
		screen0.finishSetup();

		screen1 = new FboObject(Display.getWidth(), Display.getHeight());
		screen1.addColorAttachment(new ColorTextureAttachment(screen1.getDimensions()));
		screen1.setDepthAttachment(new DepthTextureAttachment(screen1.getDimensions()));
		screen1.finishSetup();

		objectFbo = new FboObject(Display.getWidth(), Display.getHeight());
		objectFbo.addColorAttachment(new ColorTextureAttachment(objectFbo.getDimensions()));
		objectFbo.finishSetup();

		shadowFbo = new FboObject(SHADOW_MAP_SIZE, SHADOW_MAP_SIZE);
		shadowFbo.addColorAttachment(new ColorTextureAttachment(shadowFbo.getDimensions(), GL30.GL_RG32F));
		shadowFbo.setDepthAttachment(new DepthTextureAttachment(shadowFbo.getDimensions()));
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
		if (RENDER_SHADOWS) shadowMapRenderer.render(renderCamera, lights.get(0)); // set sun later
		Matrix4f shadowMapConversion = shadowMapRenderer.getConversionMat();

		/*
		postProcessor.prepare();
		PostProcessor.blurAffect.setAffectVars(.2f);
		PostProcessor.blurAffect.processAffect(shadowFbo, shadowFbo);
		postProcessor.finish();
		*/

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
		msScreen.resolveTo(0, screen0);

		// Resolves msFbo and copies picking attachment to the picking texture
		msScreen.resolveTo(1, objectFbo);

		// Do Post processing on screen texture
		doPostProcessing();

		// Render gui
		guiRenderer.renderGuis(guiRenderables);

		// Clear lists for next render, possibly change later?
		postAffects.clear();
		animatedRenderables.clear();
		staticRenderables.clear();
		spriteRenderables.clear();
		guiRenderables.clear();
		particles.clear();
		lights.clear();

		//if (Engine.getTime() % 1.0f < 0.01f)
		//	System.out.println("Rendered : " + verticesRendered + " vertices, " + modelsRendered + " models");
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

		FboObject inScreen = screen1;
		FboObject outScreen = screen0;

		for (PostAffectInstance instance : postAffects)
		{
			inScreen = inScreen == screen0 ? screen1 : screen0;
			outScreen = outScreen == screen0 ? screen1 : screen0;
			instance.callAffect(inScreen, outScreen);
		}

		postProcessor.renderToDisplay(outScreen);
		postProcessor.finish();
	}

	public void cleanUp()
	{
		screen0.cleanUp();
		screen1.cleanUp();
		msScreen.cleanUp();
		objectFbo.cleanUp();
		guiRenderer.cleanUp();
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
		List<ParticleRenderData> batch = particles.get(p.getTextureId());

		if (batch == null)
		{
			batch = new ArrayList<>();
			particles.put(p.getTextureId(), batch);
		}

		batch.add(p.getRenderData());
	}

	public void processGuiText(GuiText text)
	{

		Sorter.dynamicSort(guiRenderables, text);
	}

	public void processLightSource(Light light)
	{
		lights.add(light);

	}

	public void processGuiTexture(GuiTexture guiTexture)
	{

		Sorter.dynamicSort(guiRenderables, guiTexture);
	}


	public void processAnimatedGuiTexture(AnimatedGuiTexture guiTexture)
	{
		guiTexture.updateTexture();
		processGuiTexture(guiTexture.getTexture());
	}

	public void processPostAffect(PostAffectInstance instance)
	{
		postAffects.add(instance);

	}

	public void processSpriteModel(SpriteRenderObject renderObject)
	{
		spriteRenderables.add(renderObject);

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

	public FboObject getObjectFbo()
	{
		return objectFbo;
	}

	public void setRenderCamera(Camera renderCamera)
	{
		this.renderCamera = renderCamera;
	}
}