package shaders;

import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector4f;

public class ParticleShader extends ShaderProgram{
	private static String vertexFile = "ParticleShaderV11.vp";
	private static String fragmentFile = "ParticleShaderV11.fp";
	
	public ParticleShader() {
		super(vertexFile, fragmentFile);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix("transformation", matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix("projection", matrix);
	}
	
	public void loadModelViewMatrix(Matrix4f viewMatrix){
		super.loadMatrix("ModelView", viewMatrix);	
	}

	public void loadWVP(Matrix4f WVPMatrix) {
		super.loadMatrix("WVP", WVPMatrix);
	}
	
	public void loadBlendFactor(float blend){
		super.loadFloat("progression", blend);
	}
	
	public void loadNumberOfRows(int rowNum){
		super.loadInteger("numberOfRows", rowNum);
	}	
	
	public void loadIndicies(int indexNow, int indexNext){
		super.loadInteger("indexOn", indexNow);
		super.loadInteger("nextIndexOn", indexNext);
	}

	public void loadViewMatrix(Matrix4f viewMatrix)
	{
		super.loadMatrix("view", viewMatrix);
	}

	public void loadCurrStage(Vector4f currStage)
	{
		super.loadVector4("stageCoords0", currStage);
	}

	public void loadPostStage(Vector4f currStage)
	{
		super.loadVector4("stageCoords1", currStage);
	}
}
