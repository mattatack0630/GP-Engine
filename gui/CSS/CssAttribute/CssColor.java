package gui.CSS.CssAttribute;

import gui.Component.Component;
import rendering.Color;

/**
 * Created by mjmcc on 10/11/2016.
 */
public class CssColor extends CSSAttribute
{
	private static final Color NOT_SET = Color.NONE;

	public Color foreground;
	public Color background;

	public CssColor()
	{
		foreground = NOT_SET;
		background = NOT_SET;
	}

	public void parseForeground(String value)
	{
		this.isSet = true;
		foreground = new Color(value);
	}

	public void parseBackground(String value)
	{
		this.isSet = true;
		background = new Color(value);
	}

	@Override
	public void modComponent(Component c)
	{
		if(!isSet)
			return;

		if(foreground != NOT_SET)
			c.contentColor = foreground;
		if(background != NOT_SET)
			c.backgroundColor = background;
	}

	public void set(CssColor color)
	{
		if(color.foreground != NOT_SET)
			this.foreground = color.foreground ;
		if(color.background != NOT_SET)
			this.background = color.background ;
	}

}
