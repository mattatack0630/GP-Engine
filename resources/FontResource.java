package resources;

import gui.text.Font;
import gui.text.FontLoader;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class FontResource extends Resource
{
	private Font font;
	private String fontDataLocation;
	private String fontTextureLocation;

	public FontResource(String name, String fontDataLocation, String fontSheetLocation)
	{
		super(name, fontDataLocation);
		this.fontDataLocation = fontDataLocation;
		this.fontTextureLocation = fontSheetLocation;
	}

	@Override
	public void preloadOnDaemon()
	{

	}

	@Override
	public void load(ResourceManager resManager)
	{
		font = new Font(name,
				resManager.directLoadResource(new TextureResource(name + "_fontsheet", fontTextureLocation)).getId(),
				FontLoader.loadFontData(fontDataLocation),
				FontLoader.sheetWidth, FontLoader.sheetHeight, 8, 10);
	}


	@Override
	public void unload()
	{

	}

	@Override
	public void setId()
	{
		id = hashCode() / 1000;
	}

	public Font getFont()
	{
		return font;
	}
}
