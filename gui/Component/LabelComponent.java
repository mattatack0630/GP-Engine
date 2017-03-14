package gui.Component;

import com.sun.istack.internal.Nullable;
import gui.Component.Content.TextContent;
import gui.Text.TextAttributes;
import rendering.Color;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/8/2016.
 */
public class LabelComponent extends Component
{
	private TextContent textContent;

	public LabelComponent(String text, String font, TextAttributes attributes)
	{
		super(new Vector2f());

		attributes.setFont(font);
		textContent = new TextContent(this, new Vector2f(), text, attributes);
		setContent(textContent);
	}

	public void setText(String text, @Nullable TextAttributes attribs)
	{
		textContent.updateText(text, attribs);
		updateContent();
	}

	public void setText(String text)
	{
		textContent.updateText(text, textContent.text.getAttribs());
		updateContent();
	}

	@Override
	public void applyAttrib(String name, String value)
	{
		super.applyAttrib(name, value);
		switch (name)
		{
			case "text":
				textContent.text.setText(value);
				break;
			case "font":
				textContent.text.getAttribs().setFont(value);
				break;
			case "font-size":
				textContent.text.getAttribs().setFontSize(Float.parseFloat(value));
				break;
			case "font-thickness":
				textContent.text.getAttribs().setLetterWidth(Float.parseFloat(value));
				break;
			case "font-linelength":
				textContent.text.getAttribs().setMaxLineLength(Float.parseFloat(value));
				break;
			case "font-color":
				textContent.text.getAttribs().setColor(new Color(value));
				break;
			case "font-sharpness":
				textContent.text.getAttribs().setSharpness(Float.parseFloat(value));
				break;

			case "font-shadowoffset":
				Vector2f v = (Vector2f) ExtraUtils.parseVector(value);
				textContent.text.getAttribs().setShadowOffX(v.x());
				textContent.text.getAttribs().setShadowOffY(v.y());
				break;
			case "font-shadowcolor":
				textContent.text.getAttribs().setShadowColor(new Color(value));
				break;

			case "font-outlinewidth":
				textContent.text.getAttribs().setOutLineWidth(Float.parseFloat(value));
				break;
			case "font-outlinecolor":
				textContent.text.getAttribs().setOutLineColor(new Color(value));
				break;
			case "font-outlinesharpness":
				textContent.text.getAttribs().setOutLineSharpness(Float.parseFloat(value));
				break;
		}
	}
}
