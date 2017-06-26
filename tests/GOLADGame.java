package tests;

import rendering.Color;
import states.GameState;
import tests.states.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 3/13/2017.
 */
public class GOLADGame
{
	public static final String TEST_PATH = "res/testing.txt";
	public static final String SAVES_FOLDER_PTH = "res/golad/saves/";
	public static final String SAVES_META_PTH = "res/golad/saves/game_saves.dpdb";
	public static final String VARS_META_PTH = "res/golad/test_meta.gpdp";

	public static final float FADE_TIME = 0.5f;
	public static final Color BTN_STATIC_COL = new Color(0.9f, 0.0f, 0.0f, 0.8f);
	public static final Color BTN_HOVER_COL = new Color(0.0f, 0.0f, 0.9f, 0.9f);
	public static final Color BTN_PRESS_COLOR = new Color(0.0f, 0.0f, 0.4f, 0.9f);

	public List<GameState> states;
	public GOLADLoadMenu LOAD_GAME;
	public GOLADMainMenu MAIN_MENU;
	public GOLADGenGame GEN_GAME;
	public GOLADPlayInstance ACTIVE_GAME;
	public GOLADEnd END_GAME;

	public GOLADGameSerializer gameSerializer = new GOLADGameSerializer();
	public Map<String, GOLADSavedGame> gameSaves = new HashMap<>();

	public Player winningPlayer; // remove

	public GOLADGame()
	{
		MAIN_MENU = new GOLADMainMenu(this);
		LOAD_GAME = new GOLADLoadMenu(this);
		GEN_GAME = new GOLADGenGame(this);
		END_GAME = new GOLADEnd(this);

		GOLADMetaSerializer varsSerializer = new GOLADMetaSerializer();
		varsSerializer.metaVarsFromFile(VARS_META_PTH, this);
		
		states = new ArrayList<>();
		states.add(MAIN_MENU);
		states.add(ACTIVE_GAME);
		states.add(GEN_GAME);
		states.add(END_GAME);
		states.add(LOAD_GAME);
	}

	public void reset()
	{

	}

	public void addGameSave(GOLADSavedGame gameSave)
	{
		gameSaves.put(gameSave.getSavePath(), gameSave);
	}

	public void removeGameSave(GOLADSavedGame save)
	{
		gameSaves.remove(save.getSavePath());
	}

	public List<GameState> getStates()
	{
		return states;
	}

	public void cleanUp()
	{
		GOLADMetaSerializer varsSerializer = new GOLADMetaSerializer();
		varsSerializer.metaVarsToFile(this, VARS_META_PTH);
	}

}
