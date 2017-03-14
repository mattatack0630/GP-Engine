package shaders.uniforms;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;
import utils.math.linear.matrix.Matrix;

import java.nio.FloatBuffer;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class MatrixArrayUniform extends Uniform
{
	private static final int MAX_ARRAY_SIZE = 16 * 100;
	private static FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(MAX_ARRAY_SIZE);
	private float[] values;
	private int elSize;

	public MatrixArrayUniform(ShaderProgram program, String name, Matrix[] values, int elSize)
	{
		super(program.getId(), name);
		this.setValues(values, elSize);
	}

	public MatrixArrayUniform(MatrixArrayUniform src, Matrix[] values, int elSize)
	{
		super(src.shaderId, src.location, src.name);
		this.setValues(values, elSize);
	}

	public void setValues(Matrix[] matValues, int elSize)
	{
		float[] values = new float[matValues.length * (elSize * elSize)];
		int off = 0;

		for (int i = 0; i < matValues.length; i++)
		{
			Matrix mat = matValues[i];
			off = Matrix.store(mat, values, off);
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
		this.elSize = elSize;
	}

	@Override
	public void loadToShader()
	{
		floatBuffer.clear();
		floatBuffer.put(values);
		floatBuffer.flip();

		if (elSize == 2)
			GL20.glUniformMatrix2(location, false, floatBuffer);
		if (elSize == 3)
			GL20.glUniformMatrix3(location, false, floatBuffer);
		if (elSize == 4)
			GL20.glUniformMatrix4(location, false, floatBuffer);


	}
}
