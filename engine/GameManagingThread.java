package engine;

import tests.GOLADEnd;
import tests.GOLADGame;
import tests.GOLADGameVars;
import tests.GOLADMainMenu;

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

		GOLADGameVars gameVars = new GOLADGameVars();
		gameVars.MAIN_MENU = new GOLADMainMenu(gameVars);
		gameVars.IN_GAME = new GOLADGame(20, 20, gameVars);
		gameVars.END_GAME = new GOLADEnd(gameVars);

		List<GameState> states = new ArrayList<>();
		states.add(gameVars.MAIN_MENU);
		states.add(gameVars.IN_GAME);
		states.add(gameVars.END_GAME);

		Engine.begin(states, gameVars.MAIN_MENU);
	}


}