package gui_m4.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Font Class
 *
 * The font class holds data about the font sheet and its characters
 * */
public class Font
{
	private String name;
	private int fontSheet;
	private int sheetWidth, sheetHeight;
	private int padding;
	private int fontSize;
	private Map<Integer, CharacterData> characterInfo; //	Map of the Ascii code and the respective characterData

	public Font(String name, int fontSheet, List<CharacterData> characterData, int width, int height, int fontSize, int padding)
	{
		this.fontSheet = fontSheet;
		this.fontSize = fontSize;
		this.padding = padding;
		this.characterInfo = new HashMap<Integer, CharacterData>();
		this.name = name;

		sheetWidth = width;
		sheetHeight = height;

		for (CharacterData data : characterData)
			this.characterInfo.put(data.characterASCII, data);
	}

	public CharacterData getData(char c)
	{
		return characterInfo.get((int) c);
	}

	public int getWidth()
	{
		return sheetWidth;
	}

	public int getHeight()
	{
		return sheetHeight;
	}

	public int getFontSheet()
	{
		return fontSheet;
	}

	public int getPadding()
	{
		return padding;
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public String getName()
	{
		return name;
	}
}
