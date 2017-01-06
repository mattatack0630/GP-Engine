package particles;

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
	// How many horizontal and vertical tiles there are
	public Vector2f tilesCount;
	// Each tiles dimensions
	public Vector2f tileSize;
	public int tilesLength;

	public SpriteSheet(TextureResource sheetTexture, Vector2f tilesCount)
	{
		this.sheetTexture = sheetTexture;
		this.tilesCount = tilesCount;
		this.tileSize = new Vector2f(1.0f / tilesCount.x(), 1.0f / tilesCount.y());
		this.tilesLength = (int) tilesCount.x() * (int) tilesCount.y();
	}

	public Vector2f getTileCoord(int index)
	{
		int x = (index % (int) tilesCount.x());
		int y = (index / (int) tilesCount.x()) % (int) (tilesCount.y());
		return new Vector2f(x, y);
	}

	public Vector4f getTileMinMax(int index)
	{
		Vector2f coord = getTileCoord(index).multElements(tileSize);
		return getTileMinMax(coord);
	}

	public Vector4f getTileMinMax(Vector2f tileCoord)
	{
		Vector2f min = tileCoord;
		Vector2f max = Vector2f.add(tileCoord, tileSize, null);
		return new Vector4f(min.x(), min.y(), max.x(), max.y());
	}

	public int getTilesLength()
	{
		return tilesLength;
	}

	public int getTextureID()
	{
		return sheetTexture.getId();
	}
}
