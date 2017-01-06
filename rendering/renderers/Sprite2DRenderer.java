package rendering.renderers;

import models.Sprite2D;
import models.SpriteSheet;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.Light;
import rendering.camera.Camera;
import shaders.Sprite2DShader;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 4/14/2016.
 */
public class Sprite2DRenderer
{
	private Sprite2DShader shader = new Sprite2DShader();

	public void render(Map<SpriteSheet, List<Sprite2D>> SpriteMap, Camera camera, List<Light> lights)
	{
		shader.start();
		prepareRender(lights, camera);

		for (SpriteSheet sheet : SpriteMap.keySet())
		{
			prepareSheet(sheet);

			List<Sprite2D> spriteBatch = SpriteMap.get(sheet);
			for (Sprite2D sprite : spriteBatch)
			{
				prepareSprite(sprite);

				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			}
		}

		finishRender();
		shader.stop();
	}

	private void finishRender()
	{
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void prepareRender(List<Light> lights, Camera camera)
	{
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		shader.loadProjectionMatrix(camera.getProjection());
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);

		//GL30.glBindVertexArray(ResourceManager.getQuadModel().vaoId);
		GL20.glEnableVertexAttribArray(0);
	}

	private void prepareSheet(SpriteSheet sheet)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, sheet.getSheetId());
		shader.loadDimentions(sheet.getRowCount(), sheet.getColCount());
		shader.loadTexture();
	}

	private void prepareSprite(Sprite2D sprite)
	{
		Matrix4f transform = sprite.getTransform().getTransformMatrix();

		// Ajust to get correct acpect ratio
		// (2 = width of glScreenQuad -1 to 1)
		//Matrix4f.scale(, transform, transform);
		transform.scale(new Vector3f(sprite.getSheet().getColCount() / 2, sprite.getSheet().getRowCount() / 2, 1));

		shader.loadTransformationMatrix(transform);
		shader.loadAnimationStage(sprite.getStageCoordnates());
	}

	public void cleanUp()
	{
		shader.cleanUp();
	}
}
