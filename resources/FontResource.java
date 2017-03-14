package resources;

import gui.Text.Font;
import gui.Text.FontLoader;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class FontResource extends Resource
{

	private static final String FONT_FILE_FOLDER = "res/fonts/";
	private static final String FONT_SHEET_FOLDER = "font_sheets/";
	private static final String FONT_EXTENSION = ".fnt";

	private Font font;

	public FontResource(String name, String location)
	{
		super(name, location);
	}

	@Override
	public void load(ResourceManager resManager)
	{
		font = new Font(name,
				resManager.loadResource(new TextureResource(name + "_fontsheet", FONT_SHEET_FOLDER + location)).getId(),
				FontLoader.loadFontData(FONT_FILE_FOLDER + location + FONT_EXTENSION),
				FontLoader.sheetWidth, FontLoader.sheetHeight, 8, 10);
	}

	@Override
	public void setId()
	{
		id = hashCode() / 1000;
	}

	@Override
	public void cleanUp()
	{

	}

	public Font getFont()
	{
		return font;
	}
}
