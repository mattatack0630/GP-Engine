package engine;

import states.GameState;
import tests.GOLADGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Engine Test Driver Class
 */

public class GameManagingThread
{

    /**
     * Main engine loop
     * <p>
     * Get rid of all static classes
     */
    public static void main(String[] args)
    {
        Engine.init();

		GameState test = new TestState();
        List<GameState> states = new ArrayList<>();
		states.add(test);
		Engine.begin(states, test);
/*
        GOLADGame game = new GOLADGame();// GoladFactory
        Engine.begin(game.getStates(), game.MAIN_MENU);
        game.cleanUp();*/
    }


}

/**
 * Engine.init();
 * <p>
 * GOLADGame gameVars = new GOLADGame();
 * gameVars.MAIN_MENU = new GOLADMainMenu(gameVars);
 * gameVars.GEN_GAME = new GOLADGenGame(gameVars);
 * gameVars.END_GAME = new GOLADEnd(gameVars);
 * gameVars.LOAD_GAME = new GOLADLoadMenu(gameVars);
 * <p>
 * GOLADMetaSerializer varsSerializer = new GOLADMetaSerializer();
 * varsSerializer.metaVarsFromFile("res/golad/test_meta.gpdp", gameVars);
 * <p>
 * TestState resolveConwayRuling = new TestState();
 * <p>
 * List<GameState> states = new ArrayList<>();
 * states.add(gameVars.MAIN_MENU);
 * states.add(gameVars.ACTIVE_GAME);
 * states.add(gameVars.GEN_GAME);
 * states.add(gameVars.END_GAME);
 * states.add(gameVars.LOAD_GAME);
 * states.add(resolveConwayRuling);
 * <p>
 * /*TestState state = new TestState();
 * List<GameState> states = new ArrayList<>();
 * states.add(state);
 */
/*
Engine.begin(states, resolveConwayRuling);
*
* */