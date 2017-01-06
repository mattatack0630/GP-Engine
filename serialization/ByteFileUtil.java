package serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mjmcc on 12/25/2016.
 */
public class ByteFileUtil
{
	public static void writeToFile(String path, byte[] bytes)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(path);
			try
			{
				fos.write(bytes);
			} finally
			{
				fos.close();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static byte[] readFromFile(String path)
	{
		byte[] result = null;
		try
		{
			FileInputStream fos = new FileInputStream(path);
			try
			{
				result = new byte[fos.available()];
				fos.read(result);
			} finally
			{
				fos.close();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
