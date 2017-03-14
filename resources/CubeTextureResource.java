package resources;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import rendering.post.CubeMap;
import utils.TextureLoader;

/**
 * Created by mjmcc on 11/25/2016.
 */
public class CubeTextureResource extends Resource
{
	private static final String[] FACE_SIDES_LOC = new String[]{"_r", "_l", "_t", "_bt", "_bk", "_f"};
	private static final String CUBE_TEX_LOC = "res/textures/cube_textures/";
	private static final String TEX_EXT = ".png";

	private CubeMap cubeMap;

	public CubeTextureResource(String name, String location)
	{
		super(name, CUBE_TEX_LOC + location);// Stored in a folder
	}

	@Override
	public void load(ResourceManager resManager)
	{
		int id = GL11.glGenTextures();
		int size = 0;

		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, id);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		for (int i = 0; i < FACE_SIDES_LOC.length; i++)
		{
			TextureData data = TextureLoader.decodeTextureFile(location + FACE_SIDES_LOC[i] + TEX_EXT);
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, (int) data.getWidth(),
					(int) data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getByteBuffer());
			size = (int) (data.getWidth());
		}

		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		cubeMap = new CubeMap(id, size);
	}

	@Override
	public void setId()
	{
		id = cubeMap.getId();
	}

	public CubeMap getCubeMap()
	{
		return cubeMap;
	}

	@Override
	public void cleanUp()
	{
		GL11.glDeleteTextures(cubeMap.getId());
	}
}
