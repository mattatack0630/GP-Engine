package rendering.renderers;

import engine.Engine;
import gui_m4.GuiTexture;
import gui_m4.text.GuiText;
import gui_m4.text.TextAttributes;
import rendering.Color;
import rendering.DisplayManager;
import resources.TextureResource;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 3/2/2017.
 */
public class Trinket2D
{
	private static final int TEXT_CACHE_SIZE = 25;
	private static final int MAX_TEXTURES = 1000;
	public static final Vector4f FULL_SCREEN_BOUNDS = new Vector4f(-1,-1, 1, 1);

	private static MasterRenderer masterRenderer;
	private static Vector2f screen;

	private static float renderLevel;
	private static Color globalColor;
	private static float globalOpacity;
	private static float globalRotation;
	private static Vector4f globalClippingBounds;
	private static TextAttributes globalAttributes;

	private static TextureResource ellipseTexture;
	private static TextureResource triangleTexture;
	private static TextureResource rectangleTexture;

	private static Map<String, GuiText> textCache;

	private static List<GuiTexture> texturesCache;
	private static int texturesRendered;

	public static void init(MasterRenderer _masterRenderer)
	{
		masterRenderer = _masterRenderer;
		screen = new Vector2f(DisplayManager.WIDTH, DisplayManager.HEIGHT);

		globalOpacity = 1.0f;
		globalColor = new Color(1, 1, 1, 1);
		globalAttributes = new TextAttributes();
		globalClippingBounds = new Vector4f(FULL_SCREEN_BOUNDS);
		ellipseTexture = Engine.getResourceManager().directLoadResource(new TextureResource("ellipseTexture", "res/textures/trinket_textures/elipse_texture.png"));
		triangleTexture = Engine.getResourceManager().directLoadResource(new TextureResource("triangleTexture", "res/textures/trinket_textures/triangle_texture.png"));
		rectangleTexture = Engine.getResourceManager().directLoadResource(new TextureResource("rectangleTexture", "res/textures/trinket_textures/rectangle_texture.png"));
		textCache = new LinkedHashMap<>();

		texturesCache = new ArrayList<>(MAX_TEXTURES);
		texturesRendered = 0;

		for (int i = 0; i < MAX_TEXTURES; i++)
		{
			texturesCache.add(new GuiTexture(new Color(), new Vector2f(), new Vector2f()));
		}
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

	public static void setRenderLevel(float renderLevel)
	{
		Trinket2D.renderLevel = renderLevel;
	}

	public static void setClippingBounds(Vector4f _clippingBounds)
	{
		globalClippingBounds.set(_clippingBounds);
	}

	///////////////////////////////////////////////////////////////////////////
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

		guiText.setClippingBounds(globalClippingBounds);
		guiText.setAttribs(globalAttributes);
		guiText.setPosition(center);

		textCache.put(text, guiText);
		masterRenderer.processGuiText(guiText);

		return guiText;
	}

	public static GuiText drawText(GuiText guiText, Vector2f center)
	{
		guiText.setAttribs(globalAttributes);
		guiText.setClippingBounds(globalClippingBounds);
		guiText.setPosition(center);

		textCache.put(guiText.getText(), guiText);
		masterRenderer.processGuiText(guiText);

		return guiText;
	}

	//////////////////////////////////////////////////////////////////////////
	public static void drawEllipse(Vector2f center, Vector2f size)
	{
		GuiTexture texture = texturesCache.get(texturesRendered++);
		texture.setTexture(ellipseTexture.getId());
		texture.setPosition(center);
		texture.setSize(size);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		texture.setRenderLevel(renderLevel);
		texture.setClippingBounds(globalClippingBounds);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawEllipsePX(Vector2f center, Vector2f size)
	{
		Vector2f centerPX = new Vector2f((center.x() / screen.x() * 2.0f) - 1.0f, 1.0f - (center.y() / screen.y() * 2.0f));
		Vector2f sizePX = new Vector2f(size.x() / screen.x() * 2.0f, size.y() / screen.y() * 2.0f);

		GuiTexture texture = texturesCache.get(texturesRendered++);
		texture.setTexture(ellipseTexture.getId());
		texture.setPosition(centerPX);
		texture.setSize(sizePX);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		texture.setRenderLevel(renderLevel);
		texture.setClippingBounds(globalClippingBounds);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawRectangle(Vector2f center, Vector2f size)
	{
		GuiTexture texture = texturesCache.get(texturesRendered++);
		texture.setTexture(GuiTexture.NO_TEXTURE);
		texture.setPosition(center);
		texture.setSize(size);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		texture.setRotation(globalRotation);
		texture.setRenderLevel(renderLevel);
		texture.setClippingBounds(globalClippingBounds);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawRectanglePX(Vector2f center, Vector2f size)
	{
		Vector2f centerPX = new Vector2f((center.x() / screen.x() * 2.0f) - 1.0f, 1.0f - (center.y() / screen.y() * 2.0f));
		Vector2f sizePX = new Vector2f(size.x() / screen.x() * 2.0f, size.y() / screen.y() * 2.0f);

		GuiTexture texture = texturesCache.get(texturesRendered++);
		texture.setTexture(GuiTexture.NO_TEXTURE);
		texture.setPosition(centerPX);
		texture.setSize(sizePX);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		texture.setRenderLevel(renderLevel);
		texture.setClippingBounds(globalClippingBounds);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawTriangle(Vector2f center, Vector2f size)
	{
		GuiTexture texture = texturesCache.get(texturesRendered++);
		texture.setTexture(triangleTexture.getId());
		texture.setPosition(center);
		texture.setSize(size);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		texture.setRenderLevel(renderLevel);
		texture.setClippingBounds(globalClippingBounds);
		masterRenderer.processGuiTexture(texture);
	}

	public static void drawTrianglePX(Vector2f center, Vector2f size)
	{
		Vector2f centerPX = new Vector2f((center.x() / screen.x() * 2.0f) - 1.0f, 1.0f - (center.y() / screen.y() * 2.0f));
		Vector2f sizePX = new Vector2f(size.x() / screen.x() * 2.0f, size.y() / screen.y() * 2.0f);

		GuiTexture texture = texturesCache.get(texturesRendered++);
		texture.setTexture(triangleTexture.getId());
		texture.setPosition(centerPX);
		texture.setSize(sizePX);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		texture.setRenderLevel(renderLevel);
		texture.setClippingBounds(globalClippingBounds);
		masterRenderer.processGuiTexture(texture);
	}

	public static void releaseRenderedTextures()
	{

		texturesRendered = 0;
	}

	// Unimplemented ////////////////////////////////////////////////////////////////
	public static void drawTriangle(Vector2f p0, Vector2f p1, Vector2f p2)
	{
		float l0 = Vector2f.sub(p0, p1, null).lengthSquared();
		float l1 = Vector2f.sub(p0, p2, null).lengthSquared();
		float l2 = Vector2f.sub(p1, p2, null).lengthSquared();
		float maxLen = Maths.max(l0, l1, l2);

		Vector2f ll0;
		Vector2f ll1;
		Vector2f sl0;

		if (maxLen == l0)
		{
			ll0 = p0;
			ll1 = p1;
			sl0 = p2;
		}
		if (maxLen == l1)
		{
			ll0 = p0;
			ll1 = p2;
			sl0 = p1;
		}
		if (maxLen == l0)
		{
			ll0 = p1;
			ll1 = p2;
			sl0 = p0;
		}


	}

	public static void drawTexture(GuiTexture texture, Vector2f center, Vector2f size)
	{
		texture.setSize(size);
		texture.setPosition(center);
		texture.setColor(globalColor);
		texture.setOpacity(globalOpacity);
		texture.setRenderLevel(renderLevel);
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
