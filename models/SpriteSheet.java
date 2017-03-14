package models;

import org.lwjgl.opengl.GL11;
import resources.TextureResource;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 12/27/2016.
 */
public class SpriteSheet
{
	// Sprite Sheet Texture
	public TextureResource sheetTexture;
	private TextureResource normalTexture;
	// How many horizontal and vertical tiles there are
	public Vector2f tilesCount;
	// Each tiles dimensions
	public Vector2f tileSize;
	// Amount of tiles
	public int tilesLength;

	public SpriteSheet(TextureResource sheetTexture, TextureResource normalTexture, Vector2f tilesCount)
	{
		this.sheetTexture = sheetTexture;
		this.normalTexture = normalTexture;
		this.tilesCount = tilesCount;
		this.tileSize = new Vector2f(1.0f / tilesCount.x(), 1.0f / tilesCount.y());
		this.tilesLength = (int) tilesCount.x() * (int) tilesCount.y();

		setMagFilter(GL11.GL_NEAREST);
	}

	public void setMagFilter(int filter)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, sheetTexture.getId());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
	}

	public Vector2f getTileCoord(int index)
	{
		int x = (index % (int) tilesCount.x());
		int y = (index / (int) tilesCount.x()) % (int) (tilesCount.y());
		return new Vector2f(x, y);
	}

	public Vector4f getTileMinMax(int index)
	{
		Vector2f coord = getTileCoord(index);
		return getTileMinMax(coord);
	}

	public Vector4f getTileMinMax(Vector2f tileCoord)
	{
		Vector2f min = Vector2f.multElements(tileCoord, tileSize, null);
		Vector2f max = Vector2f.add(min, tileSize, null);
		return new Vector4f(min.x(), min.y(), max.x(), max.y());
	}

	public int getTilesLength()
	{
		return tilesLength;
	}

	public Vector2f getRowColCount()
	{
		return tilesCount;
	}

	public int getTextureID()
	{
		return sheetTexture.getId();
	}

	public int getNormalMapID()
	{
		return normalTexture.getId();
	}

	public Vector2f getTileSize()
	{
		return tileSize;
	}

}
