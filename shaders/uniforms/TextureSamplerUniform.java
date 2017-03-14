package shaders.uniforms;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import shaders.ShaderProgram;

/**
 * Created by mjmcc on 1/18/2017.
 */
public class TextureSamplerUniform extends Uniform
{
	private int textureId;
	private int place;

	public TextureSamplerUniform(ShaderProgram program, String name, int value, int place)
	{
		super(program.getId(), name);
		this.place = place;
		this.textureId = value;
	}

	public void setValue(int value)
	{
		this.textureId = value;
	}

	@Override
	public void loadToShader()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + place);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		GL20.glUniform1i(location, place);
	}
}
