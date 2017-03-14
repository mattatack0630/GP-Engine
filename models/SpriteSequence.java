package models;

import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 1/6/2017.
 */
public class SpriteSequence
{
	private SpriteSheet sheet;
	private int cellOnIndex;
	private int[] tiles;

	public SpriteSequence(SpriteSheet sheet, int[] tiles)
	{
		this.sheet = sheet;
		this.tiles = tiles;
		this.cellOnIndex = 0;
	}

	public void setTile(int[] cells)
	{
		this.tiles = cells;
	}

	public int getNextTile()
	{
		return tiles.length > 0 ? tiles[(cellOnIndex++) % tiles.length] : -1;
	}

	public int getTile(int index)
	{
		return tiles.length > 0 ? tiles[(index) % tiles.length] : -1;
	}

	public Vector4f getNextTileMinMax()
	{
		int tiles = getNextTile();

		Vector2f coord = sheet.getTileCoord(tiles);

		return sheet.getTileMinMax(coord);
	}

	public Vector4f getTileMinMax(int i)
	{
		int tiles = getTile(i);

		Vector2f coord = sheet.getTileCoord(tiles);

		return sheet.getTileMinMax(coord);
	}

	public SpriteSheet getSheet()
	{
		return sheet;
	}

	public int getTileCount()
	{
		return tiles.length;
	}

	public static SpriteSequence fromHRange(SpriteSheet sheet, int i, int j)
	{
		int[] tiles = new int[Math.abs(j - i) + 1];

		for (int x = i; x <= j; x++)
			tiles[x - i] = x;

		return new SpriteSequence(sheet, tiles);
	}

	public static SpriteSequence fromVRange(int i, int j)
	{
		return null;
	}
}
