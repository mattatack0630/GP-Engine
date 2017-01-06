package models;

/**
 * Created by mjmcc on 4/13/2016.
 */
public class Sprite2DAnimation
{
	private SpriteSheet spriteSheet;
	private int framePerSecond;
	private int[] sheetCellIndices;
	private float[] currentTextureCoords;
	private float time;
	private int indexOn;

	public Sprite2DAnimation(SpriteSheet sheet, int[] sheetCellIndices, int framePerSecond)
	{
		this.spriteSheet = sheet;
		this.sheetCellIndices = sheetCellIndices;
		this. framePerSecond = framePerSecond;
		time = 0;
		indexOn = 0;
	}

	public void tick()
	{
		if(time % framePerSecond == 0)
		{
			indexOn = (indexOn+1) % sheetCellIndices.length;
			currentTextureCoords = spriteSheet.getCellCoords(sheetCellIndices[indexOn]);
		}

		time++;
	}

	public float[] getTextureCoords()
	{
		return currentTextureCoords;
	}

	public SpriteSheet getSpriteSheet()
	{
		return spriteSheet;
	}
}
