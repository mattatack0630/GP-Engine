package rendering.renderers;

import models.SpriteAnimation;
import models.SpriteModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.Light;
import rendering.SpriteRenderObject;
import rendering.VaoObject;
import rendering.camera.Camera;
import shaders.Sprite2DShader;
import utils.VaoLoader;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

import java.util.List;

/**
 * Created by mjmcc on 4/14/2016.
 */
public class Sprite2DRenderer
{
	private static final float[] POSITIONS = new float[]{-.5f, .5f, 0, -.5f, -.5f, 0, .5f, .5f, 0, .5f, -.5f, 0};
	private static final VaoObject QUAD = VaoLoader.loadModel(3, POSITIONS, 4);

	private Sprite2DShader shader = new Sprite2DShader();

	public void render(List<SpriteRenderObject> renderObjects, List<Light> lights, Camera camera)
	{
		shader.start();
		prepareRender(lights, camera);

		SpriteModel lastModel = null;
		for (SpriteRenderObject renderObject : renderObjects)
		{
			SpriteModel model = renderObject.getSpriteModel();
			if (model != lastModel)
				prepareModel(model);

			prepareInstance(model, renderObject.getRenderData());

			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);

			lastModel = model;
		}

		finishRender();
		shader.stop();
	}

	private void prepareModel(SpriteModel model)
	{
		shader.loadTexture("spriteSheetTexture", model.getSpriteSheet().getTextureID(), 0);
		shader.loadTexture("spriteSheetNormal", model.getSpriteSheet().getNormalMapID(), 1);
	}

	private void prepareRender(List<Light> lights, Camera camera)
	{
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		shader.loadProjectionMatrix(camera.getProjection());
		shader.loadRangeLog(camera.getLogRange());
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);

		GL30.glBindVertexArray(QUAD.getId());
		GL20.glEnableVertexAttribArray(0);
	}

	private void prepareInstance(SpriteModel sprite, SpriteRenderData renderData)
	{
		renderData.updateMatrix();
		Matrix4f transform = renderData.getTransformMatrix();

		Vector2f tileSize = new Vector2f(sprite.getSpriteSheet().getTileSize()).normalize();
		transform.scale(new Vector3f(tileSize.x(), tileSize.y(), 1.0f));

		SpriteAnimation animation = sprite.getAnimation(renderData.getAnimationOn());
		Vector4f coords = animation.getCurrAnimationCoords(renderData.getTimeAt());

		shader.loadVector2("pickingId", renderData.getObjectId());
		shader.loadTransformationMatrix(transform);
		shader.loadAnimationCoords(coords);
		shader.loadFlip(false, false);
	}

	private void finishRender()
	{
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void cleanUp()
	{
		shader.cleanUp();
	}
}
