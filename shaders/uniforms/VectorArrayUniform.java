package shaders.uniforms;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;
import utils.math.linear.vector.Vector;
import utils.math.linear.vector.Vector3f;

import java.nio.FloatBuffer;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class VectorArrayUniform extends Uniform
{
	private static final int MAX_ARRAY_SIZE = 4 * 100;
	private static FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(MAX_ARRAY_SIZE);
	private float[] values;

	public VectorArrayUniform(ShaderProgram program, String name, Vector3f[] values, int elSize)
	{
		super(program.getId(), name);
		this.setValues(values, elSize);
	}

	public VectorArrayUniform(VectorArrayUniform src, Vector3f[] values, int elSize)
	{
		super(src.shaderId, src.location, src.name);
		this.setValues(values, elSize);
	}

	public void setValues(Vector[] vecValues, int elSize)
	{
		float[] values = new float[vecValues.length * elSize];

		for (int i = 0; i < vecValues.length; i++)
		{
			for (int j = 0; j < elSize; j++)
			{
				values[i * elSize + j] = vecValues[i].getEl(j);
			}
		}

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

		this.values = values;
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
