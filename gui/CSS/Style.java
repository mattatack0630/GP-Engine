package gui.CSS;

import gui.CSS.CssAttribute.CssColor;
import gui.CSS.CssAttribute.CssSize;

/**
 * Created by mjmcc on 10/10/2016.
 */
public class Style
{
	public Identifier applyToIdentifier;

	public CssSize sizeAttrib;
	public CssColor colorAttrib;

	public Style(String identifiers)
	{
		this.applyToIdentifier = new Identifier(identifiers);
		this.sizeAttrib = new CssSize();
		this.colorAttrib = new CssColor();
	}
	public Style(Style src)
	{
		this.applyToIdentifier = new Identifier(src.applyToIdentifier);
		this.sizeAttrib = new CssSize();
		this.colorAttrib = new CssColor();

		sizeAttrib = src.sizeAttrib;
		colorAttrib = src.colorAttrib;
	}

/*	public boolean hasAttribute(String name)
	{
		for (CSSAttribute a : attributes)
			if (a.name.equals(name))
				return true;
		return false;
	}

	public CSSAttribute getAttrib(String name)
	{
		int i = -1;

		for (int j = 0; j < attributes.size(); j++)
		{
			CSSAttribute a = attributes.get(j);
			if (a.name.equals(name))
			{
				i = j;
				break;
			}
		}
		if(i == -1)
		{
			i = attributes.size();
			attributes.add(new CSSAttribute(name, ""));
		}

		return attributes.get(i);
	}
*/
	public static Style compileStyle(Identifier compId, Style one, Style two)
	{
		Style style = new Style(one);

		// Get better
		int OneRel = one.applyToIdentifier.getRelevanceScore(compId);
		int TwoRel = two.applyToIdentifier.getRelevanceScore(compId);

		//if(OneRel > TwoRel)
		//	return style;

		// Set the styles variables to two (if they are set)
		style.applyToIdentifier = new Identifier(compId);
		style.sizeAttrib.set(two.sizeAttrib);
		style.colorAttrib.set(two.colorAttrib);

		return style;
	}

}
