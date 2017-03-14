package shaders.uniforms;

import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;
import utils.math.linear.vector.Vector;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class VectorUniform extends Uniform
{
	private Vector value;

	public VectorUniform(ShaderProgram program, String name, Vector value)
	{
		super(program.getId(), name);
		this.value = value;
	}

	public VectorUniform(VectorUniform src, Vector value)
	{
		super(src.shaderId, src.location, src.name);
		this.value = value;
	}

	public void setValue(Vector value)
	{
		this.value = value;
	}

	@Override
	public void loadToShader()
	{
		int elCount = value.elementsSize();

		if (elCount == 2)
			GL20.glUniform2f(location, value.getEl(0), value.getEl(1));
		if (elCount == 3)
			GL20.glUniform3f(location, value.getEl(0), value.getEl(1), value.getEl(2));
		if (elCount == 4)
			GL20.glUniform4f(location, value.getEl(0), value.getEl(1), value.getEl(2), value.getEl(3));
	}
}
