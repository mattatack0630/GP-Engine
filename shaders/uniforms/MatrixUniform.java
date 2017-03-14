package shaders.uniforms;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;
import utils.math.linear.matrix.Matrix;

import java.nio.FloatBuffer;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class MatrixUniform extends Uniform
{
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	private Matrix value;

	public MatrixUniform(ShaderProgram program, String name, Matrix value)
	{
		super(program.getId(), name);
		this.value = value;
	}

	public MatrixUniform(MatrixUniform src, Matrix value)
	{
		super(src.shaderId, src.location, src.name);
		this.value = value;
	}

	public void setValue(Matrix value)
	{
		this.value = value;
	}

	@Override
	public void loadToShader()
	{
		int elCount = value.getHeight();

		matrixBuffer.clear();
		Matrix.store(value, matrixBuffer);
		matrixBuffer.flip();

		if (elCount == 2)
			GL20.glUniformMatrix2(location, false, matrixBuffer);
		if (elCount == 3)
			GL20.glUniformMatrix3(location, false, matrixBuffer);
		if (elCount == 4)
			GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
}
