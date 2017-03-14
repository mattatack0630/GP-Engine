package shaders.uniforms;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;

import java.nio.IntBuffer;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class IntegerArrayUniform extends Uniform
{
	private static final int MAX_ARRAY_SIZE = 100;
	private static IntBuffer intBuffer = BufferUtils.createIntBuffer(MAX_ARRAY_SIZE);
	private int[] values;

	public IntegerArrayUniform(ShaderProgram program, String name, int[] values)
	{
		super(program.getId(), name);
		setValues(values);
	}

	public IntegerArrayUniform(IntegerArrayUniform src, int[] values)
	{
		super(src.shaderId, src.location, src.name);
		setValues(values);
	}

	public void setValues(int[] value)
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
		intBuffer.clear();
		intBuffer.put(values);
		intBuffer.flip();
		GL20.glUniform1(location, intBuffer);
	}
}
