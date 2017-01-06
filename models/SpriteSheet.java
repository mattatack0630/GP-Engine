package models;

import resources.ResourceManager;
import resources.TextureResource;

/**
 * Created by mjmcc on 4/13/2016.
 */
public class SpriteSheet
{
	private int sheetId;
	private float rowSize;
	private float colSize;
	private int rows;
	private int cols;

	public SpriteSheet(String sheetFile, int rows, int cols)
	{
		this.sheetId = ResourceManager.loadResource(new TextureResource(sheetFile, sheetFile)).getId();
		this.rows = rows;
		this.cols = cols;
		this.rowSize = 1 / (float) rows;
		this.colSize = 1 / (float) cols;
	}

	public float[] getCellCoords(int index)
	{
		float x = (index % rows) * rowSize;
		float y = (index / rows) * colSize;

		return new float[]{x, y, x + rowSize, y + colSize};
	}

	public int[] getIndicesThrough(int start, int finish, boolean verticle)
	{
		int[] indices = new int[Math.abs(start - finish)];
		if (!verticle)
		{
			for (int i = 0; i < indices.length; i++)
			{
				indices[i] = start + i;
			}
		} else
		{
			for (int i = 0; i < indices.length; i++)
			{
				indices[i] = ((start + (i % rows)) * rows) + (i / cols);
			}
		}

		for (int i : indices)
			System.out.println("i- " + i);

		return indices;
	}

	public int getSheetId()
	{
		return sheetId;
	}

	public float getRowCount()
	{
		return rows;
	}

	public float getColCount()
	{
		return cols;
	}
}
