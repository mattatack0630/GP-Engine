package parsing.gxml;

import gui.GuiTexture;
import gui.text.TextAttributes;
import gui.components.*;
import rendering.Color;
import utils.math.linear.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 3/20/2017.
 */
public class ComponentXMLBuilder
{
	public static Map<String, ParseAction> parseActionMap;
	public static Map<String, GXMLParsable> componentNameMap;

	public ComponentXMLBuilder()
	{
		parseActionMap = new HashMap<>();
		componentNameMap = new HashMap<>();

		addToMap(new HPanel(new Vector2f(), new Vector2f()));
		addToMap(new VPanel(new Vector2f(), new Vector2f()));
		addToMap(new ButtonComponent(new Vector2f(), new Vector2f()));
		addToMap(new InteractionButtonComponent(new Vector2f(), new Vector2f()));
		addToMap(new HSliderComponent(new Vector2f(), new Vector2f()));
		addToMap(new VSliderComponent(new Vector2f(), new Vector2f()));
		addToMap(new TextInputComponent(new Vector2f(), new Vector2f()));
		addToMap(new TextureButtonComponent(new Vector2f(), new Vector2f()));
		addToMap(new LabelComponent(new Vector2f(), "", new TextAttributes()));
		addToMap(new ScrollContainerComponent(new Vector2f(), new Vector2f()));
		addToMap(new TextureComponent(new Vector2f(), new Vector2f(), new GuiTexture(Color.NONE, new Vector2f(), new Vector2f())));
	}

	public void giveAttribute(String cName, Component c, String name, String value)
	{
		ParseAction parseAction = parseActionMap.get(name);

		if (parseAction == null)
			System.out.println("Cannot find attribute " + name + " in attribute function map");
		else
			parseAction.parse(c, value);
	}

	public static void addToMap(GXMLParsable parsable)
	{
		componentNameMap.put(parsable.componentName(), parsable);
		parsable.addToParsingFuncMap(parseActionMap);
	}

	public Component genInstanceFromName(String name)
	{
		GXMLParsable parsable = componentNameMap.get(name);

		if (parsable == null)
			System.out.println("Cannot find component " + name + " in component map");
		else
			return parsable.blankInstance();

		if (parsable != null)
			return parsable.blankInstance();

		return null;
	}
}
