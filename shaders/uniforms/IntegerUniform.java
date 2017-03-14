package shaders.uniforms;

import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class IntegerUniform extends Uniform
{
	private int value;

	public IntegerUniform(ShaderProgram program, String name, int value)
	{
		super(program.getId(), name);
		this.value = value;
	}

	public IntegerUniform(IntegerUniform src, int value)
	{
		super(src.shaderId, src.location, src.name);
		this.value = value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	@Override
	public void loadToShader()
	{
		GL20.glUniform1i(location, value);
	}
}
