package parsing.lime;

import parsing.utils.Validator;

import java.io.*;

/**
 * Created by mjmcc on 10/29/2016.
 */
public class LimeParser
{
	private final static String RES_PATH = "res/";
	private final static String LIME_EXTENSION = ".lime";

	private final static String VERTEX_LINE = "v";
	private final static String TEXTURE_LINE = "t";
	private final static String NORMAL_LINE = "n";
	private final static String FACE_LINE = "f";
	private final static String BONE_INIT_LINE = "b";
	private final static String BONE_SKINNING_LINE = "bsk";
	private final static String KEYFRAME_LINE = "kf";

	private final static String SPECULAR_MAP_LINE = "specular_map";
	private final static String NORMAL_MAP_LINE = "normal_map";
	private final static String TEXTURE_LOC_LINE = "texture_map";
	private final static String MODEL_NAME_LINE = "model_name";
	private final static String VERTEX_FLEX_LINE = "vertex_flex";
	private final static String MATERIAL_LINE = "material";

	public static LimeData parseLimeFile(String path)
	{
		LimeData limeData = new LimeData();
		BufferedReader reader = initReader(path);
		String line;

		while (true)
		{
			line = getNextLine(reader);
			if (line == null)
				break;

			parseLine(line, limeData);
		}

		finishReading(reader);

		return limeData;
	}

	private static void parseLine(String line, LimeData data)
	{
		if (!Validator.containsRegex(line, ":"))
			return;

		String[] splitData = line.split(":");
		String lineType = splitData[0].trim().toLowerCase();
		String lineContent = splitData[1].trim();

		switch (lineType)
		{
			// Model meta-data
			case MODEL_NAME_LINE:
				data.modelName = lineContent.replace("\"", "");
				break;
			case SPECULAR_MAP_LINE:
				data.setSpecFile(lineContent.replace("\"", ""));
				break;
			case NORMAL_MAP_LINE:
				data.setNormalFile(lineContent.replace("\"", ""));
				break;
			case TEXTURE_LOC_LINE:
				data.setTextureFile(lineContent.replace("\"", ""));
				break;
			case VERTEX_FLEX_LINE:
				break;
			case MATERIAL_LINE:
				data.addMaterial(lineContent.toLowerCase());
				break;

			// Model Building data
			case VERTEX_LINE:
				data.addVertex(lineContent.toLowerCase());
				break;
			case TEXTURE_LINE:
				data.addTextureCoord(lineContent.toLowerCase());
				break;
			case NORMAL_LINE:
				data.addNormal(lineContent.toLowerCase());
				break;
			case FACE_LINE:
				data.buildFace(lineContent.toLowerCase());
				break;
			case BONE_INIT_LINE:
				data.addBone(lineContent.toLowerCase());
				break;
			case BONE_SKINNING_LINE:
				data.addSkin(lineContent.toLowerCase());
				break;
			case KEYFRAME_LINE:
				data.addKeyframe(lineContent.toLowerCase());
				break;
		}
	}

	public static BufferedReader initReader(String fileName)
	{
		BufferedReader reader = null;
		try
		{
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return reader;
	}

	public static String getNextLine(BufferedReader reader)
	{
		String line = null;
		try
		{
			line = reader.readLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return line;
	}

	public static void finishReading(BufferedReader reader)
	{
		try
		{
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
