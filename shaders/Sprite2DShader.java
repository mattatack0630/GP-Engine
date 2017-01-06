package shaders;

import rendering.Light;
import rendering.camera.Camera;
import rendering.renderers.MasterRenderer;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

import java.util.List;

/**
 * Created by mjmcc on 4/14/2016.
 *
 */
public class Sprite2DShader extends ShaderProgram
{
	private static final String vertexFile = "Sprite2DShader.vp";
	private static final String fragmentFile = "Sprite2DShader.fp";

	public Sprite2DShader()
	{
		super(vertexFile, fragmentFile);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}

	public void loadLights(List<Light> lights)
	{
		Vector3f[] lightPositions = new Vector3f[MasterRenderer.MAX_LIGHTS];
		Vector3f[] lightColors = new Vector3f[MasterRenderer.MAX_LIGHTS];

		int index = 0;
		while (index < MasterRenderer.MAX_LIGHTS)
		{
			lightPositions[index] = lights.size() > index ? lights.get(index).getPosition() : new Vector3f(-999, -999, -999);
			lightColors[index] = lights.size() > index ? lights.get(index).getColor() : new Vector3f(-999, -999, -999);
			index++;
		}

		super.loadVectorArray("lightPositions", lightPositions);
		super.loadVectorArray("lightColors", lightColors);
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		loadMatrix("transformation", matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix)
	{
		loadMatrix("projection", matrix);
	}

	public void loadViewMatrix(Camera camera)
	{
		Matrix4f matrix = MatrixGenerator.genViewMatrix(camera);
		//Maths.createViewMatrix(camera);
		super.loadMatrix("view", matrix);
	}

	public void loadAnimationStage(float[] stageCoordnates)
	{
		super.loadVector2("off", stageCoordnates);
	}

	public void loadDimentions(float rowCount, float colCount)
	{
		super.loadFloat("numOfRows", rowCount);
		super.loadFloat("numOfCols", colCount);
	}


	public void loadTexture()
	{
		super.loadInteger("textureSampler", 0);
	}

}