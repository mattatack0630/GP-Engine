package rendering.renderers;

import gui.GuiTexture;
import gui.Text.GuiText;
import gui.Text.TextAttributes;
import rendering.Color;
import utils.math.linear.vector.Vector2f;

import java.util.ArrayList;

/**
 * A Basic UI render Engine interface.
 * <p>
 * Created by mjmcc on 9/22/2016.
 */
public class Trinket2D
{
	private static MasterRenderer renderer;

	/**
	 * gui Fields
	 */
	private static final int MAX_TEXTURES = 15;
	// ready to be reused
	private static ArrayList<GuiText> openTextures;
	// currently in use
	private static ArrayList<GuiText> closedTextures;


	/**
	 * Text Fields
	 */
	//Open and closed lists to reuse the same initialized guiText objects
	private static final int MAX_TEXTS = 15;
	// ready to be reused
	private static ArrayList<GuiText> openTexts;
	// currently in use
	private static ArrayList<GuiText> closedTexts;
	private static TextAttributes globalAttribs;
	private static String globalFont;

	public static void init(MasterRenderer _renderer)
	{
		renderer = _renderer;

		openTexts = new ArrayList<>();
		closedTexts = new ArrayList<>();
		globalAttribs = new TextAttributes();
		globalFont = "Arial";

		// Initialize MAX_TEXTS amount of GuiText objects
		for (int i = 0; i < MAX_TEXTS; i++)
			openTexts.add(new GuiText("", globalFont, new Vector2f()));
	}

	/*****************Text Rendering methods***************/

	/**
	 * Set the global font type
	 *
	 * @param fontName the font name, or font family name
	 */
	public static void setFont(String fontName)
	{
		globalFont = fontName;
	}

	/**
	 * set the global text-attribs color
	 *
	 * @param color the color to set to
	 */
	public static void setFontColor(Color color)
	{
		globalAttribs.setColor(color);
	}

	/**
	 * Copy these attribs to the global text attributes
	 *
	 * @param attribs the attribs to copy from
	 */
	public static void setGlobalAttribs(TextAttributes attribs)
	{
		globalAttribs = new TextAttributes(attribs);
	}

	/**
	 * Render text using a string, generates the mesh if cant find in the textList.
	 *
	 * @param text the string to render on screen
	 * @return the GuiText object created or found
	 */
	public static GuiText drawText(String text, float x, float y)
	{
		GuiText guiText = null;
		for (GuiText gt : closedTexts)
		{
			if (globalFont.compareToIgnoreCase(gt.getFont().getName()) == 0)
			{
				if (gt.getText().equals(text))
				{
					guiText = gt;
					guiText.setAttribs(new TextAttributes(globalAttribs));
					break;
				}
			}
		}

		if (guiText == null)
			guiText = generateText(text);

		guiText.setPosition(new Vector2f(x, y));
		renderer.processGuiText(guiText);

		return guiText;
	}

	/**
	 * Generate a new text, reusing one of the open guiTexts
	 *
	 * @param text the string to generate
	 * @return the guitext generated
	 */
	private static GuiText generateText(String text)
	{
		// if there are no texts to reuse, move some
		if (openTexts.size() == 0)
		{
			GuiText oldest = closedTexts.get(0);
			closedTexts.remove(oldest);
			openTexts.add(oldest);
		}

		GuiText openText = openTexts.get(0);
		openText.setText(text);
		openText.setFont(globalFont);
		openText.setAttribs(new TextAttributes(globalAttribs));
		openText.update(); // reuse the object to generate text
		closedTexts.add(openText);
		openTexts.remove(openText);

		return openText;
	}

	/**
	 * Render using a GuiText object, preferred method
	 *
	 * @param text the text object to render
	 * @return the GuiText object rendered
	 */
	public static GuiText drawText(GuiText text)
	{
		renderer.processGuiText(text);
		return text;
	}

	public static void setFontSize(float fontSize)
	{
		globalAttribs.setFontSize(fontSize);
	}

	/**
	 * Render a GuiTexture as a Rectangle
	 *
	 * @param pos  the textures pos (from center)
	 * @param size the size of the texture
	 * @param c    the color of the texture (with alpha)
	 */
	public static void drawRect(Vector2f pos, Vector2f size, Color c)
	{
		GuiTexture texture = new GuiTexture(c, pos, size); // change later to not dynamic allocate
		renderer.processGuiTexture(texture);
	}

	/**
	 * Render a GuiTexture as a Rectangle
	 *
	 * @param pos     the textures pos (from center)
	 * @param size    the size of the texture
	 * @param c       the color of the texture (with alpha)
	 * @param opacity the opacity of the overall square
	 */
	public static void drawRect(Vector2f pos, Vector2f size, Color c, float opacity)
	{
		GuiTexture texture = new GuiTexture(c, pos, size); // change later to not dynamic allocate
		texture.setOpacity(opacity);
		renderer.processGuiTexture(texture);
	}
}
