package shaders;

import gui_m4.text.TextAttributes;
import rendering.Color;
import rendering.DisplayManager;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 4/5/2017.
 */
public class GuiShader extends ShaderProgram
{
	public static final String VERTEX_SHADER = "GuiShaderV12.vp";
	public static final String FRAGMENT_SHADER = "GuiShaderV12.fp";

	public static final int TEXT_TYPE = 0;
	public static final int TEXTURE_TYPE = 1;
	public static final int NO_TEXTURE_TYPE = 2;

	public GuiShader()
	{
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "v_text_tc");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{

		super.loadMatrix("transformation", matrix);
	}

	public void loadTexture()
	{

		super.loadInteger("textureSampler", 0);
	}

	public void loadGtColor(Color color)
	{

		super.loadVector4("gt_color", color.getVector4f());
	}

	public void loadOverallAlpha(float opacity)
	{

		super.loadFloat("overallAlpha", opacity);
	}

	public void loadType(int type)
	{

		super.loadInteger("type", type);
	}

	public void loadTextAttribs(TextAttributes t)
	{
		super.loadFloat("t_fontWidth", t.getLetterWidth());
		super.loadFloat("t_fontSharpness", t.getSharpness());
		super.loadVector4("t_fontColor", t.getColor().getVector4f());

		super.loadFloat("t_outlineWidth", t.getOutLineWidth());
		super.loadFloat("t_outlineSharpness", t.getOutlineSharpness());
		super.loadVector4("t_outlineColor", t.getOutLineColor().getVector4f());

		super.loadVector2("t_shadowOffset", t.getShadowOff());

	}

	public void loadClippingBounds(Vector4f clippingBounds)
	{
		Vector4f glBounds = new Vector4f();
		glBounds.setX((clippingBounds.x() + 1.0f) / 2.0f * DisplayManager.WIDTH);
		glBounds.setY((clippingBounds.y() + 1.0f) / 2.0f * DisplayManager.HEIGHT);
		glBounds.setZ((clippingBounds.z() + 1.0f) / 2.0f * DisplayManager.WIDTH);
		glBounds.setW((clippingBounds.w() + 1.0f) / 2.0f * DisplayManager.HEIGHT);
		loadVector4("clippingBounds", glBounds);
	}
}
