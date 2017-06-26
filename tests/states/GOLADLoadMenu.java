package tests.states;

import engine.Engine;
import gui.GuiTexture;
import gui.components.*;
import gui.text.TextAttributes;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import resources.EngineFiles;
import resources.TextureResource;
import states.FadeTransState;
import states.GameState;
import states.GameStateManager;
import tests.GOLADGame;
import tests.GOLADGameParams;
import tests.GOLADGenerator;
import tests.GOLADGuiScene;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 4/24/2017.
 */
public class GOLADLoadMenu extends GameState
{
	private GOLADGame gameVars;
	private GOLADGuiScene loadGui;

	private TextureResource goladBackground;

	private Map<InteractionButtonComponent, GOLADSavedGame> saveButtons;

	private Panel savesDisplay_panel;
	private InteractionButtonComponent toMenu_btn;
	private InteractionButtonComponent delGame_btn;
	private InteractionButtonComponent loadGame_btn;
	private InteractionButtonComponent gameSettings_btn;

	private InteractionButtonComponent selected_btn;

	public GOLADLoadMenu(GOLADGame gameVars)
	{
		this.gameVars = gameVars;
		this.saveButtons = new HashMap<>();
	}

	@Override
	public void init()
	{
		loadGui = new GOLADGuiScene(EngineFiles.GUI_PATH + "GOLAD_Load_Game.xml", gameVars);
		savesDisplay_panel = (Panel) loadGui.getChildById("savesPanel");
		loadGame_btn = (InteractionButtonComponent) loadGui.getChildById("loadBtn");
		delGame_btn = (InteractionButtonComponent) loadGui.getChildById("delBtn");
		toMenu_btn = (InteractionButtonComponent) loadGui.getChildById("backBtn");
		loadGui.formatScene();

		goladBackground = Engine.getResourceManager().directLoadResource(new TextureResource("goladBackground", "res/golad/GOLAD_menu_back.png"));
	}

	@Override
	public void cleanUp()
	{

	}

	@Override
	public void tick(GameStateManager stateManager)
	{
		for (InteractionButtonComponent btn : saveButtons.keySet())
		{
			if(btn.isHovered())
			{
				btn.backgroundColor.set(gameVars.BTN_HOVER_COL);
			}
			if (btn.isPressed())
			{
				btn.backgroundColor.set(gameVars.BTN_PRESS_COLOR);
			}
			if (btn.isStatic()){
				btn.backgroundColor.set(gameVars.BTN_STATIC_COL);
			}

			if (btn.isClicked() && savesDisplay_panel.isHovered())
			{
				selected_btn = btn;
				selected_btn.backgroundColor.set(gameVars.BTN_HOVER_COL);
			}
		}

		if (toMenu_btn.isClicked())
		{
			stateManager.set(new FadeTransState(this, gameVars.MAIN_MENU, gameVars.FADE_TIME));
		}

		if(selected_btn != null)
		{
			if(!selected_btn.isPressed())
				selected_btn.backgroundColor.set(gameVars.BTN_HOVER_COL);

			if (loadGame_btn.isClicked())
			{
				load(stateManager, selected_btn);
			}

			if (delGame_btn.isClicked())
			{
				delete(selected_btn);
			}
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
		Trinket2D.setRenderLevel(-11);
		Trinket2D.setDrawColor(new Color(0.847f, 0.811f, 0.898f, 1.0f));
		Trinket2D.drawRectangle(new Vector2f(), new Vector2f(2));

		Trinket2D.setRenderLevel(-10);
		renderer.processGuiTexture(new GuiTexture(goladBackground.getId(), new Vector2f(0), new Vector2f(2, 2)));
	}

	@Override
	public void pause()
	{
		if(selected_btn != null)
		{
			selected_btn.backgroundColor.set(gameVars.BTN_STATIC_COL);
			selected_btn = null;
		}

		Engine.getGuiManager().hide(loadGui);
	}

	@Override
	public void resume()
	{
		Map<String, GOLADSavedGame> saves = gameVars.gameSaves;

		// Maybe dont re-do each time
		// this is a strange way of doing this :( i cant format regularly
		Engine.getGuiManager().hide(loadGui);
		// issue with clearing panel children
		// clearing managers list first works, but its kind of hacky
		savesDisplay_panel.clearChildren();
		saveButtons.clear();

		for (String path : saves.keySet())
		{
			GOLADSavedGame savedGame = saves.get(path);
			InteractionButtonComponent saveBtn = genSaveBtn(savedGame);
			savesDisplay_panel.addComponent(saveBtn);
			saveButtons.put(saveBtn, savedGame);
		}

		loadGui.build();

		Engine.getGuiManager().show(loadGui);
	}

	private void delete(InteractionButtonComponent btn)
	{
		GOLADSavedGame save = saveButtons.get(btn);
		gameVars.removeGameSave(save);
		saveButtons.remove(btn);

		Engine.getGuiManager().hide(loadGui);
		savesDisplay_panel.removeComponent(btn);
		savesDisplay_panel.build();
		Engine.getGuiManager().show(loadGui);


		selected_btn = null;

		//resume();
	}

	private void load(GameStateManager stateManager, InteractionButtonComponent btn)
	{
		GOLADSavedGame save = saveButtons.get(btn);
		GOLADGameParams params = gameVars.gameSerializer.readFromFile(save.getSavePath());

		gameVars.ACTIVE_GAME = GOLADGenerator.buildGame(gameVars, params);
		gameVars.ACTIVE_GAME.init();

		stateManager.set(new FadeTransState(this, gameVars.ACTIVE_GAME, gameVars.FADE_TIME));
	}

	public InteractionButtonComponent genSaveBtn(GOLADSavedGame save)
	{
		InteractionButtonComponent saveDisplay_btn = new InteractionButtonComponent(new Vector2f(), new Vector2f());
		saveDisplay_btn.addClassName("menu_button");
		saveDisplay_btn.backgroundColor = new Color(0.5f, 0.5f, 0.5f, 0.8f);
		saveDisplay_btn.minSize = new Vector2f(1f, 0.3f);
		saveDisplay_btn.margin = new Vector4f(0.01f);

		TextAttributes labelAttribs = new TextAttributes();
		labelAttribs.setColor(Color.WHITE);
		labelAttribs.setMaxLineLength(50);
		labelAttribs.setSharpness(0.85f);
		labelAttribs.setFontSize(0.55f);
		labelAttribs.setFont("Geo");

		LabelComponent saveName_lb = new LabelComponent(new Vector2f(), save.getGameName(), labelAttribs);
		saveName_lb.addClassName("menu_label");
		saveDisplay_btn.addComponent(saveName_lb);

		//saveDisplay_btn.addInteraction(new HoverColorInteraction(saveDisplay_btn, gameVars.BTN_STATIC_COL, gameVars.BTN_HOVER_COL, 0));
		saveDisplay_btn.addInteraction(new HoverGrowInteraction(saveDisplay_btn, 0.025f, 0.25f));
		saveDisplay_btn.backgroundColor.set(gameVars.BTN_STATIC_COL);

		return saveDisplay_btn;
	}
}
