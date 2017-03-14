package tests;

import engine.GameState;
import engine.GameStateManager;
import gui.Component.LabelComponent;
import gui.GuiManager;
import gui.GuiScene;
import rendering.Color;
import rendering.renderers.MasterRenderer;

/**
 * Created by mjmcc on 3/13/2017.
 */
public class GOLADEnd extends GameState
{
	private GOLADGameVars gameVars;
	private GuiScene endGui;
	private LabelComponent backToMain;
	private LabelComponent playerWinner;

	public GOLADEnd(GOLADGameVars gameVars)
	{
		this.gameVars = gameVars;
	}

	@Override
	public void init()
	{
		this.endGui = new GuiScene("GOLAD_End.xml");
		this.backToMain = (LabelComponent) endGui.getChildById("backToMain");
		this.playerWinner = (LabelComponent) endGui.getChildById("playerWinner");

		// get rid of this later
		String SCREEN_THREE = "screen_3";
		GuiManager.switchScreen(SCREEN_THREE);
		GuiManager.addSceneToScreen(SCREEN_THREE, endGui);
	}

	@Override
	public void cleanUp()
	{

	}

	@Override
	public void tick(GameStateManager stateManager)
	{
		if (backToMain.isClicked())
			stateManager.setState(gameVars.MAIN_MENU);
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
		// set gui player winner
		GuiManager.switchScreen("screen_3");

		// set player winner
		if (gameVars.winningPlayer != null)
		{
			playerWinner.setText(gameVars.winningPlayer.getName() + " Has Won!");
			playerWinner.backgroundColor = new Color(gameVars.winningPlayer.getColor(), 0.95f);
		}
	}
}
