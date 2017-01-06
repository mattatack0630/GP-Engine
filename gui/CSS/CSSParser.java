package gui.CSS;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by mjmcc on 10/10/2016.
 */
public class CSSParser
{
	private static BufferedReader reader;

	public static ArrayList<Style> parseCSS(String fileName)
	{
		ArrayList<Style> styles = new ArrayList<>();

		String cssString = getFileString(fileName);

		String[] stringNodes = cssString.split("}");

		ArrayList<CssNode> cssNodes = getNodes(stringNodes);

		for (CssNode n : cssNodes)
			styles.add(decodeCssNode(n));

		return styles;
	}

	public static Style decodeCssNode(CssNode node)
	{
		Style style = new Style(node.identifiers);

		for (String a : node.innerAttribs)
		{
			String[] data = a.split(":");
			if (data.length < 2)
				continue;

			data[0] = data[0].trim();
			data[1] = data[1].trim();

			switch (data[0])
			{
				case "width":
					style.sizeAttrib.parseWidth(data[1]);
					break;

				case "height":
					style.sizeAttrib.parseHeight(data[1]);
					break;

				case "margin":

					break;

				case "padding":

					break;

				case "color":
					style.colorAttrib.parseForeground(data[1]);
					break;

				case "background-color":
					style.colorAttrib.parseBackground(data[1]);
					break;
			}
		}

		return style;
	}

	public static float toGlValue(String s, int screenSize)
	{
		float f = 0;
		String justDigits = "";
		String justUnits = "";

		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (Character.isDigit(c) || c == '.')
				justDigits += c;
			if (Character.isAlphabetic(c))
				justUnits += c;
		}

		float number = Float.parseFloat(justDigits);

		switch (justUnits)
		{
			case "px":
				f = ((2f / screenSize) * number);
				break;
			case "dpx":

				break;
			case "gx":
				f = number;
				break;
		}


		return f;
	}

	public static ArrayList<CssNode> getNodes(String[] strings)
	{
		ArrayList<CssNode> cssNodes = new ArrayList<>();

		for (String s : strings)
		{
			String[] data = s.split("\\{");
			if (data.length < 2)
				continue;

			String identifiers = data[0];
			String[] innerAttribs = data[1].split(";");

			cssNodes.add(new CssNode(identifiers, innerAttribs));
		}

		return cssNodes;
	}

	public static String getFileString(String fileName)
	{
		String cssString = "";
		try
		{
			File file = new File("res" + fileName + ".css");
			FileReader fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);

			while (true)
			{
				String line = reader.readLine();
				if (line == null)
					break;

				cssString += line + "\n";
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return cssString;
	}
}
