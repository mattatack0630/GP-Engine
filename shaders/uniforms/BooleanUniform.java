package shaders.uniforms;

import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class BooleanUniform extends Uniform
{
	private boolean value;

	public BooleanUniform(ShaderProgram program, String name, boolean value)
	{
		super(program.getId(), name);
		this.value = value;
	}

	public BooleanUniform(BooleanUniform src, boolean value)
	{
		super(src.shaderId, src.name);
		this.value = value;
	}

	public void setValue(boolean value)
	{
		this.value = value;
	}

	@Override
	public void loadToShader()
	{
		GL20.glUniform1i(location, value ? 1 : 0);
	}
}
