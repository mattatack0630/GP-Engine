package tests;

import gui.GuiScene;
import gui.components.HPanel;
import gui.components.InteractionButtonComponent;
import gui.components.VPanel;
import resources.EngineFiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 2/17/2017.
 */
public class GameGui
{
	private GOLADGuiScene gameScene;

	private HPanel gamePanel;
	private VPanel gridPanel;
	private VPanel playerDataPanel;
	private InteractionButtonComponent finishTurnButton;
	private InteractionButtonComponent otherButton;
	private InteractionButtonComponent menuButton;
	private Map<Player, PlayerCard> playerCards;

	public void generateGui(List<Player> players, GOLADGame gameVars)
	{
		gameScene = new GOLADGuiScene(EngineFiles.GUI_PATH + "GOLAD_In_Game.xml", gameVars);

		gamePanel = (HPanel) gameScene.getChildById("gamePanel");
		gridPanel = (VPanel) gameScene.getChildById("gridPanel");
		playerDataPanel = (VPanel) gameScene.getChildById("playerPanel");
		finishTurnButton = (InteractionButtonComponent) gameScene.getChildById("finishTurn");
		otherButton = (InteractionButtonComponent) gameScene.getChildById("otherButton");
		menuButton = (InteractionButtonComponent) gameScene.getChildById("menuButton");

		playerCards = new HashMap<>(players.size());
		for (int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			PlayerCard playerCard = new PlayerCard(player, i);
			playerDataPanel.addComponent(playerCard.getPlayerPanel());
			playerCards.put(player, playerCard);
		}

		gameScene.formatScene();
		gameScene.build();
	}

	public GuiScene getGuiScene()
	{

		return gameScene;
	}

	public void setPlayerCardHighlight(Player hPlayer)
	{
		for (Player player : playerCards.keySet())
		{
			PlayerCard playerCard = playerCards.get(player);
			playerCard.setHighlight(player == hPlayer);
		}
	}

	public PlayerCard getPlayerCard(Player player)
	{

		return playerCards.get(player);
	}

	public boolean isFinishTurnClicked()
	{

		return finishTurnButton.isClicked();
	}

	public boolean isSettingsClicked()
	{

		return otherButton.isClicked();
	}

	public boolean isMainMenuClicked()
	{

		return menuButton.isClicked();
	}

	public VPanel getGridPanel()
	{

		return gridPanel;
	}

}

		/*
		// Entire Panel
		gamePanel = new HPanel(new Vector2f(), new Vector2f(0));
		gamePanel.alignment = Align.CENTER_LEFT;
		gamePanel.minSize = new Vector2f(2.0f);
		gamePanel.margin = new Vector4f();

		// Game Grid Panel
		gridPanel = new VPanel(new Vector2f(), new Vector2f());
		gridPanel.minSize = new Vector2f(2.0f * DisplayManager.getAspect(), 2.0f);
		gridPanel.margin = new Vector4f();
		gamePanel.addComponent(gridPanel);

		// Game Info Panel
		gameMenuPanel = new VPanel(new Vector2f(), new Vector2f());
		gameMenuPanel.minSize = new Vector2f(2.0f - (2.0f * DisplayManager.getAspect()), 2.0f);
		gameMenuPanel.backgroundColor = new Color(0.7f, 0.7f, 0.7f, 1f);
		gameMenuPanel.renderLevel = 1;
		gameMenuPanel.margin = new Vector4f();
		gamePanel.addComponent(gameMenuPanel);

		LabelComponent label = new LabelComponent(new Vector2f(), "Menu", new TextAttributes().setFontSize(0.5f));
		label.backgroundColor = new Color(0.0f, 0.0f, 0.0f, 0.7f);
		label.minSize = new Vector2f(0.0f, 0.2f);
		label.shouldInheritSizeX = true;
		label.margin = new Vector4f();
		gameMenuPanel.addComponent(label);

		// Player Name Cards Panel
		playerDataPanel = new VPanel(new Vector2f(), new Vector2f());
		playerDataPanel.minSize = new Vector2f(0.0f, 0.5f);
		playerDataPanel.shouldInheritSizeX = true;
		playerDataPanel.margin = new Vector4f();
		gameMenuPanel.addComponent(playerDataPanel);

		// Player Name Cards
		playerCards = new HashMap<>(players.size());
		for (int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			PlayerCard playerCard = new PlayerCard(player, i);
			playerDataPanel.addComponent(playerCard.getPlayerPanel());
			playerCards.put(player, playerCard);
		}

		// Menu/Options Panel
		optionsPanel = new VPanel(new Vector2f(), new Vector2f());
		optionsPanel.minSize = new Vector2f();
		optionsPanel.shouldInheritSizeX = true;
		optionsPanel.shouldInheritSizeY = true;
		optionsPanel.margin = new Vector4f(0.01f);
		gameMenuPanel.addComponent(optionsPanel);

		TextAttributes buttonAttribs = new TextAttributes().setFontSize(0.4f).setMaxLineLength(20);
		// Finish Turn Panel
		finishTurnButton = new LabelComponent(new Vector2f(), "Finish Turn", buttonAttribs);
		finishTurnButton.minSize = new Vector2f(0.3f, 0.3f);
		finishTurnButton.margin = new Vector4f(0.01f);
		finishTurnButton.shouldInheritSizeX = true;
		finishTurnButton.backgroundColor.set(gameVars.BTN_STATIC_COL);
		finishTurnButton.setM_enteredAction(() -> finishTurnButton.backgroundColor.set(gameVars.BTN_HOVER_COL));
		finishTurnButton.setM_exitedAction(() -> finishTurnButton.backgroundColor.set(gameVars.BTN_STATIC_COL));
		optionsPanel.addComponent(finishTurnButton);

		// Finish Turn Panel
		otherButton = new LabelComponent(new Vector2f(), "Settings", buttonAttribs);
		otherButton.minSize = new Vector2f(0.3f, 0.3f);
		otherButton.margin = new Vector4f(0.01f);
		otherButton.shouldInheritSizeX = true;
		otherButton.backgroundColor.set(gameVars.BTN_STATIC_COL);
		otherButton.setM_enteredAction(() -> otherButton.backgroundColor.set(gameVars.BTN_HOVER_COL));
		otherButton.setM_exitedAction(() -> otherButton.backgroundColor.set(gameVars.BTN_STATIC_COL));
		optionsPanel.addComponent(otherButton);

		// Finish Turn Panel
		menuButton = new LabelComponent(new Vector2f(), "Main Menu", buttonAttribs);
		menuButton.minSize = new Vector2f(0.3f, 0.3f);
		menuButton.margin = new Vector4f(0.01f);
		menuButton.shouldInheritSizeX = true;
		menuButton.backgroundColor.set(gameVars.BTN_STATIC_COL);
		menuButton.setM_enteredAction(() -> menuButton.backgroundColor.set(gameVars.BTN_HOVER_COL));
		menuButton.setM_exitedAction(() -> menuButton.backgroundColor.set(gameVars.BTN_STATIC_COL));
		optionsPanel.addComponent(menuButton);

		addPanel(gamePanel);
		build();
		show();*/