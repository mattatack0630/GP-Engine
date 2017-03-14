package parsing.lisf;

import models.SpriteAnimation;
import parsing.utils.ParsingUtils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by mjmcc on 1/11/2017.
 */
public class LisfParser
{
	private final static String VERSION = "lisf_version";
	private final static String SHEET_NAME = "sheet_name";
	private final static String MODEL_TYPE = "model_type";
	private final static String TEXTURE_PATH = "texture_map";
	private final static String NORMAL_MAP_PATH = "normal_map";
	private final static String COL_COUNT = "spritesheet_columns";
	private final static String ROW_COUNT = "spritesheet_rows";
	private final static String ROW_COL_COUNT = "spritesheet_dimensions";
	private final static String SMOOTHING_ON = "texture_smoothing";
	private final static String SPRITE_ANIM = "-sa";
	private final static String SPRITE_MODEL = "-sm";

	public static LisfData parseLISF(String filePath)
	{
		LisfData data = new LisfData();

		BufferedReader reader = ParsingUtils.openReader(filePath);

		String line = "";
		while (true)
		{
			try
			{
				line = reader.readLine();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			if (line == null)
				break;

			parseLine(line, data);
		}

		ParsingUtils.closeReader(reader);

		return data;
	}

	private static void parseLine(String line, LisfData data)
	{
		String[] lineData = line.split(":");
		if (lineData.length == 2)
		{
			String name = lineData[0];
			String value = lineData[1];
			switch (name)
			{
				case VERSION:
					break;
				case SHEET_NAME:
					data.modelName = value.replaceAll("\"| ", "");
					break;
				case MODEL_TYPE:
					break;
				case TEXTURE_PATH:
					data.texturePath = value.replaceAll("\"| ", "");
					break;
				case NORMAL_MAP_PATH:
					data.normalPath = value.replaceAll("\"| ", "");
					break;
				case COL_COUNT:
					data.rowColumn.setX(Integer.parseInt(value.replaceAll("\"| ", "")));
					break;
				case ROW_COUNT:
					data.rowColumn.setY(Integer.parseInt(value.replaceAll("\"| ", "")));
					break;
				case ROW_COL_COUNT:
					break;
				case SPRITE_ANIM:
					parseAnimationLine(value, data);
					break;
				case SPRITE_MODEL:
					parseModelLine(value, data);
					break;
				case SMOOTHING_ON:
					data.textureSmoothing = Boolean.parseBoolean(value.replaceAll("\"| ", ""));
			}
		}
	}

	private static void parseAnimationLine(String value, LisfData data)
	{
		//"testAnim1"; 15,14,13; 1; 1; 1;
		String[] valueData = value.replaceAll("\"| ", "").split(";");
		if (valueData.length == 5)
		{
			String animName = valueData[0].trim();
			int[] seqData = ParsingUtils.toIntArray(valueData[1].split(","));
			int fps = Integer.parseInt(valueData[2]);
			int endMode = Integer.parseInt(valueData[3]);
			if (endMode == 0) endMode = SpriteAnimation.END_LOOP;
			if (endMode == 1) endMode = SpriteAnimation.END_STOP;
			int loopPoint = Integer.parseInt(valueData[4]);
			data.addSpriteAnimation(animName, seqData, fps, endMode, loopPoint);
		}
	}

	private static void parseModelLine(String value, LisfData data)
	{
		//"SpriteModel0"; "testAnim0", "testAnim1"
		String[] valueData = value.replace("\"", "").split(";");
		if (valueData.length == 2)
		{
			String name = valueData[0].trim();
			String[] animNames = valueData[1].split(",");
			for (int i = 0; i < animNames.length; i++)
				animNames[i] = animNames[i].trim();
			data.addSprite(name, animNames);
		}
	}
}
