package shaders;

import gui_m4.text.TextAttributes;
import utils.math.linear.matrix.Matrix4f;

public class FontShader extends ShaderProgram
{
	public static final String VERTEX_SHADER = "FontShaderV11.vp";
	public static final String FRAGMENT_SHADER = "FontShaderV11.fp";

	public FontShader()
	{
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix("transformation", matrix);
	}

	public void loadTextAttribs(TextAttributes t){
		super.loadVector4("textColor", t.getColor().getVector4f());
		super.loadVector4("outLineColor", t.getOutLineColor().getVector4f());
		super.loadFloat("textSharpness", t.getSharpness());
		super.loadVector2("offset", t.getShadowOff());
		super.loadFloat("textWidth", t.getLetterWidth());
		super.loadFloat("boarderWidth", t.getOutLineWidth());
		super.loadFloat("borderSharpness", t.getOutlineSharpness());

	}

	public void loadOverallAlpha(float opacity)
	{
		super.loadFloat("overallAlpha", opacity);
	}
}
