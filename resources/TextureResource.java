package resources;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class TextureResource extends Resource
{
	public TextureData texData;

	public TextureResource(String name, String location)
	{
		super(name, location);
	}

	@Override
	public void preloadOnDaemon()
	{

	}

	public void setTextureParam(int paramName, int paramValue)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getId());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, paramName, paramValue);
	}

	@Override
	public void load(ResourceManager resManager)
	{
		try
		{
			Texture texture = TextureLoader.getTexture("PNG", new FileInputStream(location));
			texData = new TextureData(texture.getImageWidth(), texture.getImageHeight(), texture.getTextureID());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void unload()
	{
		GL11.glDeleteTextures(texData.getId());
	}

	@Override
	public void setId()
	{
		id = texData.getId();
	}

}
