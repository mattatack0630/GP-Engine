package parsing.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by mjmcc on 11/8/2016.
 */
public class FileStreamPrinter
{
	private File outFile;
	private BufferedWriter writer;

	public FileStreamPrinter(String filePath)
	{
		this.outFile = new File(filePath);
		if (!outFile.exists())
		{
			try
			{
				outFile.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			this.writer = new BufferedWriter(new FileWriter(outFile));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void printLine(String line)
	{
		try
		{
			writer.write(line + "\n");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void close()
	{
		try
		{
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
