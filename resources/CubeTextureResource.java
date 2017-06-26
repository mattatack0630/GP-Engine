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

	private String[] textureLocations;
	private CubeMap cubeMap;

	public CubeTextureResource(String name, String location, String ext)
	{
		super(name, location);

		textureLocations = new String[6];

		for (int i = 0; i < textureLocations.length; i++)
			textureLocations[i] = location + FACE_SIDES_LOC[i] + ext;
	}

	public CubeTextureResource(String name, String locR, String locL, String locT, String locBT, String locBK, String locF)
	{
		super(name, locR);
		textureLocations = new String[6];
		textureLocations[0] = locR;
		textureLocations[1] = locL;
		textureLocations[2] = locT;
		textureLocations[3] = locBT;
		textureLocations[4] = locBK;
		textureLocations[5] = locF;
	}

	@Override
	public void preloadOnDaemon()
	{

	}

	@Override
	public void load(ResourceManager resManager)
	{
		int id = GL11.glGenTextures();
		int size = 0;

		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, id);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		for (int i = 0; i < textureLocations.length; i++)
		{
			TextureData data = TextureLoader.decodeTextureFile(textureLocations[i]);
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, (int) data.getWidth(),
					(int) data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getByteBuffer());
			size = (int) (data.getWidth());
		}

		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		cubeMap = new CubeMap(id, size);
	}

	@Override
	public void unload()
	{
		GL11.glDeleteTextures(cubeMap.getId());
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

}
