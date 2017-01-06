package utils;

import utils.math.linear.vector.Vector;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 10/16/2016.
 */
public class ExtraUtils
{
	public static String getFileAsString(String filePath)
	{
		String string = "";
		try
		{
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			while (true)
			{
				String line = reader.readLine();
				if (line == null)
					break;

				string += line + "\n";
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return string;
	}

	public static String stripChars(String s, char... chars)
	{
		String ns = "";

		for (int i = 0; i < s.length(); i++)
		{
			boolean add = true;
			char c = s.charAt(i);
			for (char c1 : chars)
				if (c == c1)
					add = false;
			if (add)
				ns += c;
		}

		return ns;
	}

	public static String stripString(String s, String... del)
	{
		String result = s;

		for (String d : del)
		{
			int i = result.indexOf(d);
			if (i == -1)
				continue;
			result = result.substring(0, i) + result.substring(i + d.length());
		}

		return result;
	}

	public static utils.math.linear.vector.Vector parseVector(String vecString)
	{
		utils.math.linear.vector.Vector result = new utils.math.linear.vector.Vector2f();

		String[] data1 = vecString.split("\\(");
		String[] content = stripChars(data1[1], ')', '(').split(",");
		float[] fArray = toFloatArray(content);

		if (fArray.length == 2)
			result = new utils.math.linear.vector.Vector2f(fArray[0], fArray[1]);
		if (fArray.length == 3)
			result = new utils.math.linear.vector.Vector3f(fArray[0], fArray[1], fArray[2]);
		if (fArray.length == 4)
			result = new utils.math.linear.vector.Vector4f(fArray[0], fArray[1], fArray[2], fArray[3]);

		return result;
	}

	private static float[] toFloatArray(String[] content)
	{
		float[] fArray = new float[content.length];
		// Validate inputFbo later!
		for (int i = 0; i < content.length; i++)
		{
			fArray[i] = Float.parseFloat(content[i].trim());
		}

		return fArray;
	}

	public static void printArray(Object[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + ((i == array.length - 1) ? "\n" : ", "));
		}
	}

	public static boolean compareVecTol(Vector v0, Vector v1, float t)
	{
		boolean e = false;
		return e;
	}

	public static <E> List<E> cullSameValues(List<E> list)
	{
		List<E> newList = new ArrayList<>();

		for (E e : list)
			if (!newList.contains(e))
				newList.add(e);

		return newList;
	}
}
