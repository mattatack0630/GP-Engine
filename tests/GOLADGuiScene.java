package tests;

import gui.GuiScene;
import gui.components.*;
import gui.text.TextAttributes;
import rendering.Color;

import java.util.List;

/**
 * Created by mjmcc on 4/29/2017.
 */
public class GOLADGuiScene extends GuiScene
{
	private final static String MENU_BTN_CLASS = "menu_button";
	private static final String MENU_LBL_CLASS = "menu_label";

	GOLADGame gameVars;

	public GOLADGuiScene(String src, GOLADGame gameVars)
	{
		super(src);
		this.gameVars = gameVars;
		this.formatScene();
	}

	public void formatScene()
	{
		formatMenuButtons();
		formatMenuLabels();
	}

	private void formatMenuButtons()
	{
		List<Component> components = getChildByClass(MENU_BTN_CLASS);

		for (Component component : components)
		{
			InteractionButtonComponent ibutton = (InteractionButtonComponent) component;
			ibutton.addInteraction(new HoverColorInteraction(ibutton, gameVars.BTN_STATIC_COL, gameVars.BTN_HOVER_COL, 0));
			ibutton.addInteraction(new HoverGrowInteraction(ibutton, 0.025f, 0.25f));
		}
	}

	private void formatMenuLabels()
	{
		List<Component> components = getChildByClass(MENU_LBL_CLASS);
		TextAttributes attributes = new TextAttributes();
		attributes.setColor(Color.WHITE);
		attributes.setSharpness(0.825f);
		attributes.setMaxLineLength(50);
		attributes.setFontSize(0.5f);
		attributes.setFont("Geo");

		for (Component component : components)
		{
			LabelComponent label = (LabelComponent) component;
			label.setTextAttributes(attributes);
			label.build();
		}
	}
}
