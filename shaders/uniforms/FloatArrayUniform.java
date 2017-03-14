package shaders.uniforms;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;

import java.nio.FloatBuffer;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class FloatArrayUniform extends Uniform
{
	private static final int MAX_ARRAY_SIZE = 100;
	private static FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(MAX_ARRAY_SIZE);
	private float[] values;

	public FloatArrayUniform(ShaderProgram program, String name, float[] values)
	{
		super(program.getId(), name);
		this.values = values;
	}

	public FloatArrayUniform(FloatArrayUniform src, float[] values)
	{
		super(src.shaderId, src.location, src.name);
		this.values = values;
	}

	public void setValues(float[] value)
	{
		if (values.length > MAX_ARRAY_SIZE)
		{
			try
			{
				throw new UniformArraySizeException();
			} catch (UniformArraySizeException e)
			{
				e.printStackTrace();
			}
		}

		this.values = value;
	}

	@Override
	public void loadToShader()
	{
		floatBuffer.clear();
		floatBuffer.put(values);
		floatBuffer.flip();
		GL20.glUniform1(location, floatBuffer);
	}
}
