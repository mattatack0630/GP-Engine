package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mjmcc on 4/25/2017.
 */
public class IOUtils
{
	public static byte[] getBytes(String filepath)
	{
		byte[] data = null;

		try
		{
			Path path = Paths.get(filepath);
			data = Files.readAllBytes(path);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return data;
	}

	public static void writeBytes(String filepath, byte[] bytes)
	{
		writeBytes(filepath, bytes, 0, bytes.length);
	}

	public static void writeBytes(String filepath, byte[] bytes, int start, int end)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(filepath);
			fos.write(bytes, start, end);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
