package rendering.renderers;

import engine.Engine;
import gui.GuiTexture;
import gui.Text.GuiText;
import gui.Text.TextAttributes;
import rendering.Color;
import rendering.DisplayManager;
import resources.TextureResource;
import utils.math.linear.vector.Vector2f;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mjmcc on 3/2/2017.
 */
public class Trinket2D
{
	private static final int TEXT_CACHE_SIZE = 25;

	private static MasterRenderer masterRenderer;
	private static Vector2f screen;

	private static Color globalColor;
	private static float globalOpacity;
	private static float globalRotation;
	private static TextAttributes globalAttributes;

	private static TextureResource ellipseTexture;
	private static TextureResource triangleTexture;
	private static TextureResource rectangleTexture;

	private static Map<String, GuiText> textCache;

	public static void init(MasterRenderer _masterRenderer)
	{
		masterRenderer = _masterRenderer;
		screen = new Vector2f(DisplayManager.WIDTH, DisplayManager.HEIGHT);

		globalOpacity = 1.0f;
		globalColor = new Color(1, 1, 1, 1);
		globalAttributes = new TextAttributes();
		ellipseTexture = Engine.getResourceManager().loadResource(new TextureResource("ellipseTexture", "trinket_textures/elipse_texture"));
		triangleTexture = Engine.getResourceManager().loadResource(new TextureResource("triangleTexture", "trinket_textures/triangle_texture"));
		rectangleTexture = Engine.getResourceManager().loadResource(new TextureResource("rectangleTexture", "trinket_textures/rectangle_texture"));
		textCache = new LinkedHashMap<>();
	}

	public static void setTextAttributes(TextAttributes textAttribs)
	{
		globalAttributes = textAttribs;
	}

	public static void setDrawColor(Color color)
	{
		globalColor.setR(color.getR());
		globalColor.setG(color.getG());
		globalColor.setB(color.getB());
		globalOpacity = color.getA();
	}

	public static void setDrawRotation(float rotation)
	{
		globalRotation = rotation;
	}

	private static GuiText getTextFromCache(String name)
	{
		GuiText guiText = textCache.get(name);
		textCache.remove(name);

		if (textCache.size() > TEXT_CACHE_SIZE)
		{
			String fkey = textCache.keySet().iterator().next();
			textCache.remove(fkey);
		}

		return guiText;
	}

	public static GuiText drawText(String text, Vector2f center)
	{
		GuiText guiText = getTextFromCache(text);

		if (guiText == null || guiText.getFont().getName() != globalAttributes.getFont())
			guiText = new GuiText(text, center, globalAttributes);

		guiText.setAttribs(globalAttributes);
		guiText.setPosition(center);

		textCache.put(text, guiText);
		masterRenderer.processGuiText(guiText);

		return guiText;
	}

	public static GuiText drawText(GuiText guiText, Vector2f center)
	{
		guiText.setAttribs(globalAttributes);
		guiText.setPosition(center);

		textCache.put(guiText.getText(), guiText);
		masterRenderer.processGuiText(guiText);

		return guiText;
	}

	public static void drawEllipse(Vector2f center, Vector2f size)
	{
		GuiTexture texture = new GuiTexture(ellipseTexture.getId(), center, size);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawEllipsePX(Vector2f center, Vector2f size)
	{
		Vector2f centerPX = new Vector2f((center.x() / screen.x() * 2.0f) - 1.0f, 1.0f - (center.y() / screen.y() * 2.0f));
		Vector2f sizePX = new Vector2f(size.x() / screen.x() * 2.0f, size.y() / screen.y() * 2.0f);

		GuiTexture texture = new GuiTexture(ellipseTexture.getId(), centerPX, sizePX);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawRectangle(Vector2f center, Vector2f size)
	{
		GuiTexture texture = new GuiTexture(rectangleTexture.getId(), center, size);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		texture.setRotation(globalRotation);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawRectanglePX(Vector2f center, Vector2f size)
	{
		Vector2f centerPX = new Vector2f((center.x() / screen.x() * 2.0f) - 1.0f, 1.0f - (center.y() / screen.y() * 2.0f));
		Vector2f sizePX = new Vector2f(size.x() / screen.x() * 2.0f, size.y() / screen.y() * 2.0f);

		GuiTexture texture = new GuiTexture(rectangleTexture.getId(), centerPX, sizePX);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawTriangle(Vector2f center, Vector2f size)
	{
		GuiTexture texture = new GuiTexture(triangleTexture.getId(), center, size);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawTrianglePX(Vector2f center, Vector2f size)
	{
		Vector2f centerPX = new Vector2f((center.x() / screen.x() * 2.0f) - 1.0f, 1.0f - (center.y() / screen.y() * 2.0f));
		Vector2f sizePX = new Vector2f(size.x() / screen.x() * 2.0f, size.y() / screen.y() * 2.0f);

		GuiTexture texture = new GuiTexture(triangleTexture.getId(), centerPX, sizePX);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawTexture(GuiTexture texture, Vector2f center, Vector2f size)
	{
		texture.setSize(size);
		texture.setPosition(center);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawTexturePX(GuiTexture texture, Vector2f center, Vector2f size)
	{
		Vector2f centerPX = new Vector2f((center.x() / screen.x() * 2.0f) - 1.0f, 1.0f - (center.y() / screen.y() * 2.0f));
		Vector2f sizePX = new Vector2f(size.x() / screen.x() * 2.0f, size.y() / screen.y() * 2.0f);

		texture.setSize(sizePX);
		texture.setPosition(centerPX);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		masterRenderer.processGuiTexture(texture);
	}
}
