package shaders;

import shaders.uniforms.FloatUniform;
import shaders.uniforms.MatrixUniform;
import shaders.uniforms.VectorUniform;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector4f;

public class ParticleShader extends ShaderProgram
{
	private static String vertexFile = "ParticleShaderV11.vp";
	private static String fragmentFile = "ParticleShaderV11.fp";

	private MatrixUniform projectionUniform;
	private MatrixUniform modelViewUniform;
	private VectorUniform currStageUniform;
	private VectorUniform postStageUniform;
	private FloatUniform progressUniform;

	public ParticleShader()
	{
		super(vertexFile, fragmentFile);

		projectionUniform = new MatrixUniform(this, "projection", new Matrix4f());
		modelViewUniform = new MatrixUniform(this, "ModelView", new Matrix4f());
		currStageUniform = new VectorUniform(this, "stageCoords0", new Vector4f());
		postStageUniform = new VectorUniform(this, "stageCoords1", new Vector4f());
		progressUniform = new FloatUniform(this, "progression", 0);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	public void loadProjectionMatrix(Matrix4f matrix)
	{
		projectionUniform.setValue(matrix);
		projectionUniform.loadToShader();
	}

	public void loadModelViewMatrix(Matrix4f viewMatrix)
	{
		modelViewUniform.setValue(viewMatrix);
		modelViewUniform.loadToShader();
	}

	public void loadBlendFactor(float blend)
	{
		progressUniform.setValue(blend);
		progressUniform.loadToShader();
	}

	public void loadCurrStage(Vector4f currStage)
	{
		currStageUniform.setValue(currStage);
		currStageUniform.loadToShader();
	}

	public void loadPostStage(Vector4f postStage)
	{
		postStageUniform.setValue(postStage);
		postStageUniform.loadToShader();
	}
}
