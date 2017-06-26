package shaders;

import models.RenderMaterial;
import rendering.Color;
import rendering.Light;
import rendering.camera.Camera;
import rendering.fbo.FboObject;
import shaders.uniforms.BooleanUniform;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

import java.util.List;

public class StaticShader extends ShaderProgram
{

	public static final String VERTEX_SHADER = "StaticShaderV11.vp";
	public static final String FRAGMENT_SHADER = "GlobalLightingShader.fp";
	public static final int MAX_LIGHTS = 5;

	private BooleanUniform shadowsOn;
	private BooleanUniform texMapOn;
	private BooleanUniform infoMapOn;
	private BooleanUniform normalMapOn;
	private BooleanUniform environmentMapOn;
	private BooleanUniform lightingOn;

	public StaticShader()
	{
		super(VERTEX_SHADER, FRAGMENT_SHADER);
		shadowsOn = new BooleanUniform(this, "shadowsOn", true);
		texMapOn = new BooleanUniform(this, "texMapOn", true);
		infoMapOn = new BooleanUniform(this, "infoMapOn", true);
		normalMapOn = new BooleanUniform(this, "normalMapOn", true);
		environmentMapOn = new BooleanUniform(this, "enviroMapOn", true);
		lightingOn = new BooleanUniform(this, "lightingOn", true);
	}

	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
		super.bindAttribute(3, "tangent");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix("transformation", matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix)
	{
		super.loadMatrix("projection", matrix);
	}

	public void loadViewMatrix(Camera camera)
	{
		super.loadMatrix("view", camera.getViewMatrix());
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

	public void loadTexture()
	{
		super.loadInteger("textureSampler", 0);
	}

	public void loadDebugColor(Color debugColor)
	{
		super.loadVector4("debugTint", debugColor.getVector4f());
	}

	public void loadMaterial(RenderMaterial material)
	{
		super.loadFloat("material.reflectFactor", material.getReflectiveFactor());
		super.loadFloat("material.refractFactor", material.getRefractiveFactor());
		super.loadFloat("material.refractIndex", material.getRefractiveIndex());
		super.loadFloat("material.specularD", material.getSpecularDampening());
		super.loadFloat("material.specularR", material.getSpecularFactor());
	}

	public void loadRangeLog(float logRange)
	{
		super.loadFloat("rangeLog", logRange);
	}

	public void loadShadowMap(FboObject shadowMap, Matrix4f shadowMapConversion, int place)
	{
		super.loadTexture("shadowMap", shadowMap.getColorAttachment(0), place);
		super.loadFloat("shadowMapSize", shadowMap.getDimensions().x());
		super.loadMatrix("shadowSpaceConversion", shadowMapConversion);
	}

	public void loadShadowOn(boolean on)
	{
		shadowsOn.setValue(on);
		shadowsOn.loadToShader();
	}

	public void loadTexMapOn(boolean on)
	{
		texMapOn.setValue(on);
		texMapOn.loadToShader();
	}

	public void loadInfoMapOn(boolean on)
	{
		infoMapOn.setValue(on);
		infoMapOn.loadToShader();
	}

	public void loadNormalMapOn(boolean on)
	{
		normalMapOn.setValue(on);
		normalMapOn.loadToShader();
	}

	public void loadEnvironmentMapOn(boolean on)
	{
		environmentMapOn.setValue(on);
		environmentMapOn.loadToShader();
	}

	public void loadLightingOn(boolean on)
	{
		lightingOn.setValue(on);
		lightingOn.loadToShader();
	}
}
