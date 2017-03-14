package tests;

import engine.GameState;
import engine.GameStateManager;
import gui.Component.LabelComponent;
import gui.GuiManager;
import gui.GuiScene;
import rendering.renderers.MasterRenderer;

/**
 * Created by mjmcc on 3/13/2017.
 */
public class GOLADMainMenu extends GameState
{
	private GOLADGameVars gameVars;
	private GuiScene menuGui;
	private LabelComponent startGame;
	private LabelComponent settings;

	public GOLADMainMenu(GOLADGameVars gameVars)
	{
		this.gameVars = gameVars;
	}

	@Override
	public void init()
	{
		menuGui = new GuiScene("GOLAD_Main.xml");
		startGame = (LabelComponent) menuGui.getChildById("startGame");
		settings = (LabelComponent) menuGui.getChildById("settings");

		String SCREEN_FOUR = "screen_four";
		GuiManager.switchScreen(SCREEN_FOUR);
		GuiManager.addSceneToScreen(SCREEN_FOUR, menuGui);
	}

	@Override
	public void cleanUp()
	{

	}

	@Override
	public void tick(GameStateManager stateManager)
	{
		if (startGame.isClicked())
		{
			gameVars.reset();
			stateManager.setState(gameVars.IN_GAME); // later game gen
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{
		GuiManager.switchScreen("screen_four");
	}
}
