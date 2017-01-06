package gui.CSS.CssAttribute;

import gui.CSS.CSSParser;
import gui.Component.Component;
import org.lwjgl.opengl.Display;

/**
 * Created by mjmcc on 10/11/2016.
 */
public class CssSize extends CSSAttribute
{
	public static final float NOT_SET = -1;

	public float height;
	public float width;

	public CssSize()
	{
		super();
		height = NOT_SET;
		width = NOT_SET;
	}

	public void parseHeight(String value)
	{
		this.isSet = true;
		height = CSSParser.toGlValue(value, Display.getHeight());
	}

	public void parseWidth(String value)
	{
		this.isSet = true;
		width = CSSParser.toGlValue(value, Display.getWidth());
	}

	@Override
	public void modComponent(Component c)
	{
		if(!isSet)
			return;

		/*if(height != NOT_SET)
			c.minSize.y = height;
		if(width != NOT_SET)
			c.minSize.x = width;*/
	}

	public void set(CssSize size)
	{
		//if(size.height != NOT_SET)
			this.height = 10;//size.height;
		//if(size.width != NOT_SET)
			this.width = size.width;
	}

}
