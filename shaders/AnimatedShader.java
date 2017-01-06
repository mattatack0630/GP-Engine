package shaders;

import models.RenderMaterial;
import rendering.Color;
import rendering.Light;
import rendering.camera.Camera;
import utils.VaoObject;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 4/20/2016.
 */
public class AnimatedShader extends ShaderProgram
{

	public static final String VERTEX_SHADER = "AnimatedShaderV11.vp";
	public static final String FRAGMENT_SHADER = "GlobalLightingShader.fp";
	public static final String GEOMETRY_SHADER = "GlobalLightingShader.gp";
	public static final int MAX_LIGHTS = 5;

	public AnimatedShader()
	{
		super(VERTEX_SHADER, GEOMETRY_SHADER, FRAGMENT_SHADER);
	}

	protected void bindAttributes()
	{
		super.bindAttribute(VaoObject.POSITIONS, "position");
		super.bindAttribute(VaoObject.TEXTURE_COORDS, "textureCoords");
		super.bindAttribute(VaoObject.NORMALS, "normal");
		super.bindAttribute(VaoObject.TANGENTS, "tangent");
		super.bindAttribute(VaoObject.BONE_INDEX, "boneIndices");
		super.bindAttribute(VaoObject.BONE_WEIGHT, "boneWeights");
	}

	public void loadWireFrameBoolean(boolean wireMode)
	{
		super.loadBoolean("isInWireMode", wireMode);
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
		Matrix4f matrix = MatrixGenerator.genViewMatrix(camera);
		super.loadMatrix("view", matrix);
	}

	public void loadLights(List<Light> lightList)
	{
		int lightNumber = Math.min(lightList.size(), MAX_LIGHTS);
		Vector3f[] lightPosArray = new Vector3f[MAX_LIGHTS];
		Vector3f[] lightColorArray = new Vector3f[MAX_LIGHTS];
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

	public void loadSpecularVariables(float reflectivity, float dampening)
	{
		super.loadFloat("surfaceReflectivity", reflectivity);
		super.loadFloat("surfaceDampening", dampening);
	}

	public void loadFlatShading(boolean b)
	{
		super.loadBoolean("flatShading", b);
	}

	public void loadTexture()
	{
		super.loadInteger("textureSampler", 0);
	}

	public void loadDebugColor(Color debugColor)
	{
		super.loadVector4("debugTint", debugColor.getVector4f());
	}

	public void loadBoneMatrices(ArrayList<Matrix4f> boneMats)
	{
		Matrix4f[] boneTransforms = new Matrix4f[boneMats.size()];

		int i = 0;
		for (Matrix4f b : boneMats)
			boneTransforms[i++] = b;

		super.loadMatrixArray("bones", boneTransforms);
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
}
