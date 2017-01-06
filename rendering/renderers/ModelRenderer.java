package rendering.renderers;

import animation.AnimatedRenderData;
import models.AnimatedModel;
import models.StaticModel;
import org.lwjgl.opengl.GL11;
import rendering.AnimatedRenderObject;
import rendering.Light;
import rendering.RenderData;
import rendering.StaticRenderObject;
import rendering.camera.Camera;
import rendering.post.CubeMap;
import resources.TextureResource;
import shaders.AnimatedShader;
import shaders.StaticShader;
import shaders.WireFrameShader;
import utils.math.linear.matrix.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/**
 * The Model Renderer
 * <p>
 * used to render 3D textured models
 */
public class ModelRenderer
{
	private StaticShader staticShader;
	private AnimatedShader animationShader;
	private WireFrameShader wireFrameShader;

	public ModelRenderer()
	{
		this.staticShader = new StaticShader();
		this.animationShader = new AnimatedShader();
		this.wireFrameShader = new WireFrameShader();
	}

	/*Static Model Stuff*/

	public void renderStaticModels(List<StaticRenderObject> renderObjects, List<Light> lights, CubeMap environmentMap, Camera camera)
	{
		staticShader.start();

		prepareStaticRender(lights, environmentMap, camera);

		StaticModel lastModel = null;
		for (StaticRenderObject renderObject : renderObjects)
		{
			StaticModel model = renderObject.getStaticModel();

			if (model != lastModel) prepareStaticModel(model);

			prepareStaticInstance(renderObject.getRenderData());
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVaoObject().vertexCount, GL11.GL_UNSIGNED_INT, 0);

			lastModel = model;
		}

		staticShader.stop();
	}

	private void prepareStaticRender(List<Light> lights, CubeMap environmentMap, Camera camera)
	{
		staticShader.loadLights(lights);
		staticShader.loadViewMatrix(camera);
		staticShader.loadTextureCube("", environmentMap.getId(), 1);
		staticShader.loadProjectionMatrix(camera.getProjection());
		staticShader.loadRangeLog(camera.getLogRange());
		staticShader.loadFlatShading(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	private void prepareStaticModel(StaticModel model)
	{
		TextureResource texture = model.getTexture();
		TextureResource infoMap = model.getInfoMap();
		TextureResource normalMap = model.getNormalMap();

		model.getVaoObject().bind();

		staticShader.loadMaterial(model.getMaterial());
		staticShader.loadTexture("textureSampler", texture.getId(), 0);
		staticShader.loadTexture("normalSampler", normalMap.getId(), 1);
		staticShader.loadTexture("infoSampler", infoMap.getId(), 2);
	}

	private void prepareStaticInstance(RenderData modelInstance)
	{
		staticShader.loadTransformationMatrix(modelInstance.getTransformMatrix());
		staticShader.loadDebugColor(modelInstance.getDebugColor());
		staticShader.loadVector2("pickingId", modelInstance.getPickingId());
	}


	/*Animated Stuff!*/

	public void renderAnimatedModels(List<AnimatedRenderObject> renderObjects, List<Light> lights, CubeMap environmentMap, Camera camera)
	{
		animationShader.start();

		prepareAnimationRender(lights, environmentMap, camera);

		AnimatedModel lastModel = null;
		for (AnimatedRenderObject renderObject : renderObjects)
		{
			AnimatedModel model = renderObject.getStaticModel();
			if (model != lastModel)
				prepareAnimatedModel(model);

			prepareAnimatedInstance(model, renderObject.getRenderData());
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVaoObject().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

			lastModel = model;
		}

		animationShader.stop();
	}

	private void prepareAnimationRender(List<Light> lights, CubeMap environmentMap, Camera camera)
	{
		animationShader.loadLights(lights);
		animationShader.loadViewMatrix(camera);
		animationShader.loadTextureCube("", environmentMap.getId(), 1);
		animationShader.loadProjectionMatrix(camera.getProjection());
		animationShader.loadRangeLog(camera.getLogRange());
		animationShader.loadFlatShading(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	private void prepareAnimatedModel(AnimatedModel animatedModel)
	{
		StaticModel model = animatedModel;

		TextureResource texture = model.getTexture();
		TextureResource infoMap = model.getInfoMap();
		TextureResource normalMap = model.getNormalMap();

		model.getVaoObject().bind();

		animationShader.loadMaterial(model.getMaterial());
		animationShader.loadTexture("textureSampler", texture.getId(), 0);
		animationShader.loadTexture("normalSampler", normalMap.getId(), 1);
		animationShader.loadTexture("infoSampler", infoMap.getId(), 2);
	}

	private void prepareAnimatedInstance(AnimatedModel model, AnimatedRenderData renderData)
	{
		renderData.updateMatrix();
		ArrayList<Matrix4f> boneMats = model.getAnimationData().getBoneAnimationMats(renderData.animationOn, renderData.time);

		animationShader.loadDebugColor(renderData.tempColor);
		animationShader.loadVector2("pickingId", renderData.getPickingId());

		animationShader.loadTransformationMatrix(renderData.getTransformMatrix());
		animationShader.loadBoneMatrices(boneMats);
	}


	public void cleanUp()
	{
		staticShader.cleanUp();
		animationShader.cleanUp();
		wireFrameShader.cleanUp();
	}
}
