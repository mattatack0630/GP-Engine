package gui;

import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mjmcc on 10/22/2016.
 * <p>
 * A content the smallest unit, it has a size, and functionality of sorts
 * A component is a element on the screen which can hold and display content
 * A panel is a group of components that are placed together in a layout (ei. a grid or vertical aligned panel)
 * A GuiScene is a set of panels that are grouped together, they move together (ei. a popup)
 * A GuiScreen is a set of GuiScene which are shown together, like a start screen, or a main screen, end screen
 */
public class GuiManager
{
	private static final String GUI_FILES_PATH = "res/gui_files/";
	private static final String DEFAULT_POPUP_SOURCE = "popup.xml";

	private static String popupSource;
	private static HashMap<String, ArrayList<GuiScene>> screenMap;
	private static ArrayList<GuiScene> currentScreenGui;
	private static String currentScreenName;

	public static void init()
	{
		screenMap = new HashMap<>();
		currentScreenGui = new ArrayList<>();
		currentScreenName = "";
		popupSource = DEFAULT_POPUP_SOURCE;
	}

	public static void tick()
	{
		for (int i = 0; i < currentScreenGui.size(); i++)
		{
			GuiScene s = currentScreenGui.get(i);
			s.tick();
			if (s.isRemovable())
				currentScreenGui.remove(s);
		}
	}

	public static void render(MasterRenderer renderer)
	{
		for (GuiScene s : currentScreenGui)
			s.render(renderer);
	}

	public static void setSceneToScreen(GuiScene... scene)
	{
		currentScreenGui.clear();

		for (GuiScene s : scene)
			currentScreenGui.add(s);
	}

	public static void setSceneToScreen(String screenName, GuiScene... scene)
	{
		String currScreen = currentScreenName;
		switchScreen(screenName);
		setSceneToScreen(scene);
		switchScreen(currScreen);
	}

	public static void addSceneToScreen(GuiScene... scene)
	{
		for (GuiScene s : scene)
			currentScreenGui.add(s);
	}

	public static void addSceneToScreen(String screenName, GuiScene... scene)
	{
		String currScreen = currentScreenName;
		switchScreen(screenName);
		addSceneToScreen(scene);
		switchScreen(currScreen);
	}

	public static void removeSceneFromScreen(GuiScene... scene)
	{
		for (GuiScene s : scene)
			currentScreenGui.remove(s);
	}

	public static void removeSceneFromScreen(String screenName, GuiScene... scene)
	{
		String currScreen = currentScreenName;
		switchScreen(screenName);
		removeSceneFromScreen(scene);
		switchScreen(currScreen);
	}

	public static GuiScene getSceneOnScreen(String screenName, String id)
	{
		String currScreen = currentScreenName;
		switchScreen(screenName);
		GuiScene result = getSceneOnScreen(id);
		switchScreen(currScreen);
		return result;
	}

	public static GuiScene getSceneOnScreen(String id)
	{
		GuiScene result = null;

		for (GuiScene s : currentScreenGui)
			if (s.getId().equals(id))
				result = s;

		return result;
	}

	public static void clearCurrentScreen()
	{
		currentScreenGui.clear();
	}

	public static void setCurrentScreenName(String name)
	{
		screenMap.remove(currentScreenName);
		currentScreenName = name;
		screenMap.put(name, currentScreenGui);
	}

	public static String getCurrentSceneName()
	{
		return currentScreenName;
	}

	public static void setPopupXMLSource(String path)
	{
		popupSource = path;
	}

	// Add a popup with a message and title, based on default template
	public static PopupGui addPopup(String title, String message)
	{
		PopupGui p = new PopupGui(message, 1, .1f, Align.BOTTOM_RIGHT, popupSource);
		p.setTitle(title);
		return addPopup(p);
	}

	// Add any popup
	public static PopupGui addPopup(PopupGui popupGui)
	{
		currentScreenGui.add(popupGui);
		return popupGui;
	}

	public static ArrayList<GuiScene> makeNewScreen(String screenName)
	{
		ArrayList<GuiScene> list = new ArrayList<>();
		screenMap.put(screenName, list);
		return list;
	}

	// Close currently opened screen, and display this one
	public static void switchScreen(String sceneName)
	{
		for (GuiScene s : currentScreenGui)
		{
			s.onScreenClose();
			s.hide();
		}

		if (!screenMap.containsKey(sceneName))
			makeNewScreen(sceneName);

		currentScreenGui = screenMap.get(sceneName);
		currentScreenName = sceneName;

		for (GuiScene s : currentScreenGui)
		{
			s.onScreenOpen();
			s.show();
		}
	}

	// Overlay a screen atop current one
	public void overlayScreen(String screenName)
	{

	}
}
