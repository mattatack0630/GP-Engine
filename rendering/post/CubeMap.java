package rendering.post;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 11/25/2016.
 */
public class CubeMap
{
	public static final int[] FACES = new int[]{
			GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X, GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
			GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
			GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z};

	private int id;
	private int size;

	public CubeMap(int size)
	{
		this.size = size;
		this.id = generateEmptyMap(size);
	}

	public CubeMap(int id, int size)
	{
		this.size = size;
		this.id = id;
	}

	private int generateEmptyMap(int size)
	{
		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, id);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		for (int i = 0; i < FACES.length; i++)
		{
			GL11.glTexImage2D(FACES[i], 0, GL11.GL_RGBA, size,
					size, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		}

		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		return id;
	}

	public int getId()
	{
		return id;
	}

	public int getSize()
	{
		return size;
	}

	public void cleanUp()
	{
		GL11.glDeleteTextures(id);
	}
}
