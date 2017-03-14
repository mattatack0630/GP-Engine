package shaders.uniforms;

import org.lwjgl.opengl.GL20;

/**
 * Created by mjmcc on 1/18/2017.
 */
public abstract class Uniform
{
	private static final int UNKNOWN_LOC = -1;

	protected String name;
	protected int location;
	protected int shaderId;

	public Uniform(int program, String name)
	{
		this.name = name;
		this.shaderId = program;
		this.location = GL20.glGetUniformLocation(program, name);
	}

	public Uniform(int shaderId, int location, String name)
	{
		this.name = name;
		this.shaderId = shaderId;
		this.location = location;
	}

	public abstract void loadToShader();
}
