package shaders;

import rendering.Light;
import rendering.camera.Camera;
import rendering.renderers.MasterRenderer;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

import java.util.List;

/**
 * Created by mjmcc on 4/14/2016.
 */
public class Sprite2DShader extends ShaderProgram
{
	private static final String vertexFile = "Sprite2DShader.vp";
	private static final String fragmentFile = "Sprite2DShader.fp";
	private static final int MAX_LIGHTS = MasterRenderer.MAX_LIGHTS;

	public Sprite2DShader()
	{
		super(vertexFile, fragmentFile);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}

	public void loadLights(List<Light> lightList)
	{
		int lightNumber = Math.min(lightList.size(), MAX_LIGHTS);
		Vector3f[] lightColorArray = new Vector3f[MAX_LIGHTS];
		Vector3f[] lightPosArray = new Vector3f[MAX_LIGHTS];
		Vector3f[] lightAttArray = new Vector3f[MAX_LIGHTS];

		for (int i = 0; i < MAX_LIGHTS; i++)
		{
			if (i < lightNumber)
			{
				lightColorArray[i] = lightList.get(i).getColor();
				lightPosArray[i] = lightList.get(i).getPosition();
				lightAttArray[i] = lightList.get(i).getAttenuation();
			} else
			{
				lightColorArray[i] = new Vector3f(-999);
				lightPosArray[i] = new Vector3f(-999);
				lightAttArray[i] = new Vector3f(-999);
			}
		}

		super.loadVectorArray("lightColors", lightColorArray);
		super.loadVectorArray("lightPositions", lightPosArray);
		super.loadVectorArray("lightAttenuation", lightAttArray);
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
		super.loadMatrix("view", camera.getViewMatrix());
	}

	public void loadTexture()
	{
		super.loadInteger("textureSampler", 0);
	}

	public void loadAnimationCoords(Vector4f coords)
	{
		super.loadVector4("animationCoords", coords);
	}

	public void loadFlip(boolean x, boolean y)
	{
		super.loadVector2("flip", new Vector2f(x ? 0 : 1, y ? 0 : 1));
	}

	public void loadRangeLog(float logRange)
	{
		super.loadFloat("rangeLog", logRange);
	}
}