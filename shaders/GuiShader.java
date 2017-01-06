package shaders;


import rendering.Color;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

public class GuiShader extends ShaderProgram{

	public static final String VERTEX_SHADER = "GuiShaderV11.vp";
	public static final String FRAGMENT_SHADER = "GuiShaderV11.fp";
	
	public GuiShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix("transformation", matrix);
	}
	public void loadTexture() {
		super.loadInteger("textureSampler", 0);
	}
	public void loadColor(Color color) {
		float r = color.getR();
		float g = color.getG();
		float b = color.getB();
		float a = color.getA();
		Vector3f tintColor = new Vector3f(r,g,b);
		
		super.loadVector3("tintColor", tintColor);
		super.loadFloat("tintAlpha", a);
	}
	public void loadHasTexture(boolean hasTexture) {
		super.loadBoolean("hasTexture", hasTexture);
	}

	public void loadOverallAlpha(float opacity)
	{
		super.loadFloat("overallAlpha", opacity);
	}
}
