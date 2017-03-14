package shaders.uniforms;

import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class FloatUniform extends Uniform
{
	private float value;

	public FloatUniform(ShaderProgram program, String name, float value)
	{
		super(program.getId(), name);
		this.value = value;
	}

	public FloatUniform(FloatUniform src, float value)
	{
		super(src.shaderId, src.location, src.name);
		this.value = value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}

	@Override
	public void loadToShader()
	{
		GL20.glUniform1f(location, value);
	}
}
