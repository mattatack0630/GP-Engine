package shaders;

import utils.math.linear.matrix.Matrix4f;

/**
 * Created by mjmcc on 1/15/2017.
 */
public class ShadowMapShader extends ShaderProgram
{
	private static final String FRAG_PATH = "ShadowMapShader.fp";
	private static final String VERTEX_PATH = "ShadowMapShader.vp";

	public ShadowMapShader()
	{
		super(VERTEX_PATH, FRAG_PATH);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}

	public void loadMVP(Matrix4f mvp)
	{
		super.loadMatrix("MVP", mvp);
	}
}
