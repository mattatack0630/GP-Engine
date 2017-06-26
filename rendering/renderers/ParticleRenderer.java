package rendering.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL31;
import particles.ParticleRenderData;
import rendering.VAOInstanceData;
import rendering.VAOInstancePartition;
import rendering.VaoObject;
import rendering.camera.Camera;
import shaders.ParticleShader;
import utils.VaoLoader;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParticleRenderer
{
	private static final int MAX_PARTICLES = 5000;
	private static final int PARTICLE_DATA_SIZE = 29;
	private static final float[] POSITIONS = new float[]{-.5f, .5f, 0, -.5f, -.5f, 0, .5f, .5f, 0, .5f, -.5f, 0};
	public static final VaoObject QUAD = VaoLoader.loadModel(3, POSITIONS, 4);

	private ParticleShader shader = new ParticleShader();
	private VAOInstanceData particleInstanceData;
	private VAOInstancePartition modelView;
	private VAOInstancePartition progression;
	private VAOInstancePartition stageCoords0;
	private VAOInstancePartition stageCoords1;
	private VAOInstancePartition particleColors;

	public ParticleRenderer()
	{
		modelView = new VAOInstancePartition(1, 0, 16);
		stageCoords0 = new VAOInstancePartition(5, 16, 4);
		stageCoords1 = new VAOInstancePartition(6, 20, 4);
		progression = new VAOInstancePartition(7, 24, 1);
		particleColors = new VAOInstancePartition(8, 25, 4);
		particleInstanceData = new VAOInstanceData(PARTICLE_DATA_SIZE, MAX_PARTICLES);
		particleInstanceData.setPartitions(modelView, stageCoords0, stageCoords1, progression, particleColors);
		QUAD.setInstanceAttribute(particleInstanceData);
	}

	public void prepareRender()
	{
		shader.start();
		QUAD.bind();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
	}

	public void renderParticles(Map<Integer, List<ParticleRenderData>> particleMap, Camera camera)
	{
		prepareRender();
		shader.loadProjectionMatrix(camera.getProjection());
		Matrix4f viewMatrix = MatrixGenerator.genViewMatrix(camera);

		for (Integer texture : particleMap.keySet())
		{
			List<ParticleRenderData> particleBatch = particleMap.get(texture);

			prepareBatch(texture, particleBatch, camera, viewMatrix);

			GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, 4, particleBatch.size());
		}

		finishRender();
	}

	private void prepareBatch(int texture, List<ParticleRenderData> renderDatas, Camera camera, Matrix4f view)
	{
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		List<Vector4f> stage0List = new ArrayList<>(renderDatas.size());
		List<Vector4f> stage1List = new ArrayList<>(renderDatas.size());
		List<Float> progressionList = new ArrayList<>(renderDatas.size());
		List<Matrix4f> modelViewList = new ArrayList<>(renderDatas.size());
		List<Vector4f> particleColorsList = new ArrayList<>(renderDatas.size());

		for (ParticleRenderData renderData : renderDatas)
		{
			stage0List.add(renderData.getCurrStage());
			stage1List.add(renderData.getPostStage());
			progressionList.add(renderData.getStageProgression());
			modelViewList.add(getModelViewMat(renderData, view));
			particleColorsList.add(renderData.getParticleColor().rgba());
		}

		particleInstanceData.loadMatrix4fs(modelView, modelViewList);
		particleInstanceData.loadVector4fs(stageCoords0, stage0List);
		particleInstanceData.loadVector4fs(stageCoords1, stage1List);
		particleInstanceData.loadFloats(progression, progressionList);
		particleInstanceData.loadVector4fs(particleColors, particleColorsList);
		QUAD.updateInstanceAttribute(particleInstanceData);
	}

	private Matrix4f getModelViewMat(ParticleRenderData renderData, Matrix4f view)
	{
		Matrix4f modelMatrix = new Matrix4f().setIdentity();

		modelMatrix.translate(renderData.getPosition());
		modelMatrix.setElement(0, 0, view.getEl(0, 0));
		modelMatrix.setElement(1, 0, view.getEl(0, 1));
		modelMatrix.setElement(2, 0, view.getEl(0, 2));
		modelMatrix.setElement(0, 1, view.getEl(1, 0));
		modelMatrix.setElement(1, 1, view.getEl(1, 1));
		modelMatrix.setElement(2, 1, view.getEl(1, 2));
		modelMatrix.setElement(0, 2, view.getEl(2, 0));
		modelMatrix.setElement(1, 2, view.getEl(2, 1));
		modelMatrix.setElement(2, 2, view.getEl(2, 2));
		modelMatrix.scale(new Vector3f(renderData.getScale()));
		Matrix4f modelViewMatrix = Matrix4f.mult(view, modelMatrix, null);

		return modelViewMatrix;
	}

	private void finishRender()
	{
		QUAD.unbind();
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		shader.stop();
	}

	public void cleanUp()
	{
		shader.cleanUp();
	}
}
