package gui_m4.text;

import rendering.Color;
import utils.math.linear.vector.Vector2f;

/**
 * text Attributes class
 * <p>
 * This class is used to add various effects and attributes to A text
 */
public class TextAttributes
{
	public static final float X_LEFT = 0.0f;    // position + (width * 0)
	public static final float X_CENTER = -0.5f; // position + (width * -0.5)
	public static final float X_RIGHT = -1.0f;  // position + (width * -1.0)

	public static final float Y_TOP = 0.0f;  // position + (height * 0.0)
	public static final float Y_BOTTOM = 1.0f;  // position + (height * 1.0)
	public static final float Y_CENTER = 0.5f;  // position + (height * 0.5)

	private String font;
	private float fontSize;
	private Color color;
	private Color outLineColor;
	private Color shadowColor;
	private Vector2f shadowVec;
	private float letterWidth;
	private float outLineWidth;
	private float outlineSharpness;
	private float maxLineLength; // Max line util line wraps
	private float lineSpacing;
	private float sharpness;
	private Vector2f alignment;

	public TextAttributes()
	{
		font = "Arial";
		color = new Color();
		fontSize = 1;
		letterWidth = .5f;
		outLineWidth = 0;
		outLineColor = new Color();
		shadowVec = new Vector2f();
		shadowColor = new Color();
		maxLineLength = 50;
		lineSpacing = 50;
		sharpness = .10f;
		outlineSharpness = .10f;
		alignment = new Vector2f(X_CENTER, Y_CENTER);
	}

	public TextAttributes(Font font, float fontSize, Color textColor)
	{
		this.font = font.getName();
		this.color = textColor;
		this.fontSize = fontSize;
		letterWidth = .5f;
		outLineWidth = 0;
		outLineColor = new Color();
		shadowVec = new Vector2f();
		shadowColor = new Color();
		maxLineLength = 50;
		lineSpacing = 50;
		sharpness = .10f;
		outlineSharpness = .10f;
		alignment = new Vector2f(X_CENTER, Y_CENTER);
	}

	public TextAttributes(TextAttributes src)
	{
		font = src.getFont();
		color = src.getColor();
		fontSize = src.getFontSize();
		letterWidth = src.getLetterWidth();
		outLineWidth = src.getOutLineWidth();
		outLineColor = src.getOutLineColor();
		shadowVec = src.getShadowOff();
		shadowColor = src.getShadowColor();
		maxLineLength = src.getMaxLineLength();
		lineSpacing = src.getLineSpacing();
		sharpness = src.getSharpness();
		outlineSharpness = src.getOutlineSharpness();
		alignment = new Vector2f(src.getAlignment());
	}

	public void setFont(String font)
	{
		this.font = font;
	}

	public String getFont()
	{
		return font;
	}

	public Color getColor()
	{
		return color;
	}

	public TextAttributes setColor(Color color)
	{
		this.color.set(color);
		return this;
	}

	public float getFontSize()
	{
		return fontSize;
	}

	public TextAttributes setFontSize(float fontSize)
	{
		this.fontSize = fontSize;
		return this;
	}

	public TextAttributes setSharpness(float sharpness)
	{
		this.sharpness = 1.0f - sharpness;
		return this;
	}

	public float getSharpness()
	{
		return sharpness;
	}

	public float getLetterWidth()
	{
		return letterWidth;
	}

	public TextAttributes setLetterWidth(float letterWidth)
	{
		this.letterWidth = letterWidth;
		return this;
	}

	public float getOutLineWidth()
	{
		return outLineWidth;
	}

	public TextAttributes setOutLineWidth(float outLineWidth)
	{
		this.outLineWidth = outLineWidth;
		return this;
	}

	public Color getOutLineColor()
	{
		return outLineColor;
	}

	public TextAttributes setOutLineColor(Color outLineColor)
	{
		this.outLineColor = outLineColor;
		return this;
	}

	public Vector2f getShadowOff()
	{
		return shadowVec;
	}

	public float getShadowOffX()
	{
		return shadowVec.x();
	}

	public TextAttributes setShadowOffX(float shadowOffX)
	{
		this.shadowVec.setX(shadowOffX);
		return this;
	}

	public float getShadowOffY()
	{
		return shadowVec.y();
	}

	public TextAttributes setShadowOffY(float shadowOffY)
	{
		this.shadowVec.setY(shadowOffY);
		return this;

	}

	public Color getShadowColor()
	{
		return shadowColor;
	}

	public TextAttributes setShadowColor(Color shadowColor)
	{
		this.shadowColor = shadowColor;
		return this;
	}

	public float getMaxLineLength()
	{
		return maxLineLength;
	}

	public TextAttributes setMaxLineLength(float maxLineLength)
	{
		this.maxLineLength = maxLineLength;
		return this;
	}

	public TextAttributes setLineSpacing(float lineSpacing)
	{
		this.lineSpacing = lineSpacing;
		return this;
	}

	public float getLineSpacing()
	{
		return lineSpacing;
	}

	public TextAttributes setOutLineSharpness(float s)
	{
		this.outlineSharpness = s;
		return this;
	}

	public float getOutlineSharpness()
	{
		return outlineSharpness;
	}

	public Vector2f getAlignment()
	{
		return alignment;
	}

    public void setShadowOffset(Vector2f shadowOffset)
    {
        this.shadowVec.set(shadowOffset);
    }
}
