package resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 1/5/2017.
 */
public class EngineFiles
{
	public static String FONTS_PATH;
	public static String AUDIO_PATH;
	public static String GUI_PATH;
	public static String STATIC_MODELS_PATH;
	public static String ANIMATED_MODELS_PATH;
	public static String CUBE_TEXTURES_PATH;
	public static String FONT_TEXTURES_PATH;
	public static String DIFFUSE_MAPS_PATH;
	public static String NORMAL_MAPS_PATH;
	public static String INFO_MAPS_PATH;
	public static String PARTICLE_SHEETS_PATH;
	public static String SPRITE_SHEETS_PATH;
	public static String SAVES_PATH;
	public static String SHADERS_PATH;

	public static Map<String, String> locationsMap = new HashMap<>();

	public static String getLocation(String s)
	{
		return locationsMap.get(s);
	}

	public static void parseFromFile(String filename)
	{
		try
		{
			FileReader fr = new FileReader(filename);
			BufferedReader reader = new BufferedReader(fr);
			String line = "";

			while (true)
			{
				line = reader.readLine();
				if (line == null)
					break;

				parseLine(line);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	private static void parseLine(String line)
	{
		String[] splits = line.split("=");

		if (splits.length >= 2)
		{
			String name = splits[0];
			String value = splits[1].replace("\"", "");

			switch (name)
			{
				case "audio":
					AUDIO_PATH = value;
					locationsMap.put("audio", value);
					break;
				case "fonts":
					FONTS_PATH = value;
					locationsMap.put("fonts", value);
					break;
				case "guis":
					GUI_PATH = value;
					locationsMap.put("guis", value);
					break;
				case "static_models":
					STATIC_MODELS_PATH = value;
					locationsMap.put("staticModels", value);
					break;
				case "animated_models":
					ANIMATED_MODELS_PATH = value;
					locationsMap.put("animatedModels", value);
					break;
				case "cube_textures":
					CUBE_TEXTURES_PATH = value;
					locationsMap.put("cubeTextures", value);
					break;
				case "font_textures":
					FONT_TEXTURES_PATH = value;
					locationsMap.put("fontSheets", value);
					break;
				case "diffuse_textures":
					DIFFUSE_MAPS_PATH = value;
					locationsMap.put("diffuseMaps", value);
					break;
				case "normal_textures":
					NORMAL_MAPS_PATH = value;
					locationsMap.put("normalTextures", value);
					break;
				case "info_textures":
					INFO_MAPS_PATH = value;
					locationsMap.put("infoTextures", value);
					break;
				case "particle_textures":
					PARTICLE_SHEETS_PATH = value;
					locationsMap.put("particleSheets", value);
					break;
				case "sprite_textures":
					SPRITE_SHEETS_PATH = value;
					locationsMap.put("spriteSheets", value);
					break;
				case "saves":
					SAVES_PATH = value;
					locationsMap.put("saves", value);
					break;
				case "shaders":
					SHADERS_PATH = value;
					locationsMap.put("shaders", value);
					break;
			}
		}

	}
}
