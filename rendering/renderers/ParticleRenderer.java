package rendering.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import particles.ParticleRenderData;
import particles.SpriteSheet;
import rendering.camera.Camera;
import resources.ResourceManager;
import shaders.ParticleShader;
import utils.VaoLoader;
import utils.VaoObject;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.AxisAngle;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;

import java.util.List;
import java.util.Map;

public class ParticleRenderer
{
	private ParticleShader shader = new ParticleShader();

	private VaoObject particleVao;

	public ParticleRenderer()
	{
		float[] quadPositions = new float[]{-.5f, .5f, 0, -.5f, -.5f, 0, .5f, .5f, 0, .5f, -.5f, 0};
		particleVao = VaoLoader.loadModel(3, quadPositions);
	}

	public void prepareRender()
	{
		shader.start();
		GL30.glBindVertexArray(ResourceManager.getGuiQuad().vaoId);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
	}

	public void renderParticles(Map<SpriteSheet, List<ParticleRenderData>> particleMap, Camera camera)
	{
		prepareRender();
		shader.loadProjectionMatrix(camera.getProjection());
		Matrix4f viewMatrix = MatrixGenerator.genViewMatrix(camera);

		for (SpriteSheet texture : particleMap.keySet())
		{
			List<ParticleRenderData> particleBatch = particleMap.get(texture);
			prepareBatch(texture);

			for (ParticleRenderData particle : particleBatch)
			{
				prepareInstance(particle, camera, viewMatrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
				//GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, 4, particleBatch.size());
			}
		}
		finishRender();
	}

	private void finishRender()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		shader.stop();
	}

	private void prepareBatch(SpriteSheet texture)
	{
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}

	private void prepareInstance(ParticleRenderData renderData, Camera camera, Matrix4f view)
	{
		Matrix4f modelMatrix = new Matrix4f().setIdentity();

		modelMatrix.translate(renderData.getPosition());

		AxisAngle a = new Euler(camera.getRotation()).toAxisAngle();
		modelMatrix.rotate(a.getAngleRad(), a.getAxis().negate());

		modelMatrix.scale(new Vector3f(renderData.getScale()));
		Matrix4f modelViewMatrix = Matrix4f.mult(view, modelMatrix, null);

		shader.loadModelViewMatrix(modelViewMatrix);
		shader.loadBlendFactor(renderData.getStageProgression());
		shader.loadCurrStage(renderData.getCurrStage());
		shader.loadPostStage(renderData.getPostStage());
	}

	public void addModelViewMatrix()
	{
	}

	public void addBlendFactor()
	{
	}

	public void addCurrStage()
	{
	}

	public void addPostStage()
	{
	}

	public void cleanUp()
	{
		shader.cleanUp();
	}
}
