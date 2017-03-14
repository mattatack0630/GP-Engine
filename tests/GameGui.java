package tests;

import gui.Component.LabelComponent;
import gui.Component.Panel.HPanel;
import gui.Component.Panel.VPanel;
import gui.GuiScene;
import gui.Text.TextAttributes;
import rendering.Color;
import rendering.DisplayManager;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 2/17/2017.
 */
public class GameGui extends GuiScene
{
	private HPanel gamePanel;
	private VPanel gridPanel;

	private VPanel gameMenuPanel;
	private VPanel playerDataPanel;
	private VPanel optionsPanel;
	private LabelComponent finishTurnButton;
	private LabelComponent otherButton;
	private LabelComponent menuButton;
	private Map<Player, PlayerCard> playerCards;

	public void generateGui(List<Player> players)
	{
		// Entire Panel
		gamePanel = new HPanel();
		gamePanel.minSize = new Vector2f(2.0f);
		gamePanel.padding = new Vector4f();
		gamePanel.margin = new Vector4f();

		// Game Grid Panel
		gridPanel = new VPanel();
		gridPanel.minSize = new Vector2f(2.0f * DisplayManager.getAspect(), 2.0f);
		gridPanel.backgroundColor = Color.NONE;
		gridPanel.padding = new Vector4f();
		gridPanel.margin = new Vector4f();
		gamePanel.addChild(gridPanel);

		// Game Info Panel
		gameMenuPanel = new VPanel();
		gameMenuPanel.minSize = new Vector2f(2.0f - (2.0f * DisplayManager.getAspect()), 2.0f);
		gameMenuPanel.backgroundColor = new Color(0.7f, 0.7f, 0.7f, 0.9f);
		gameMenuPanel.padding = new Vector4f();
		gameMenuPanel.margin = new Vector4f();
		gamePanel.addChild(gameMenuPanel);

		LabelComponent label = new LabelComponent("Menu", "Arial", new TextAttributes().setFontSize(0.5f));
		label.backgroundColor = new Color(0.0f, 0.0f, 0.0f, 0.7f);
		label.minSize = new Vector2f(0.0f, 0.2f);
		label.shouldInheritSizeX = true;
		label.margin = new Vector4f();
		gameMenuPanel.addChild(label);

		// Player Name Cards Panel
		playerDataPanel = new VPanel();
		playerDataPanel.minSize = new Vector2f(0.0f, 0.5f);
		playerDataPanel.shouldInheritSizeX = true;
		playerDataPanel.margin = new Vector4f();
		gameMenuPanel.addChild(playerDataPanel);

		// Player Name Cards
		playerCards = new HashMap<>(players.size());
		for (int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			PlayerCard playerCard = new PlayerCard(player, i);
			playerDataPanel.addChild(playerCard.getPlayerPanel());
			playerCards.put(player, playerCard);
		}

		// Menu/Options Panel
		optionsPanel = new VPanel();
		optionsPanel.minSize = new Vector2f();
		optionsPanel.shouldInheritSizeX = true;
		optionsPanel.shouldInheritSizeY = true;
		optionsPanel.margin = new Vector4f();
		gameMenuPanel.addChild(optionsPanel);

		TextAttributes buttonAttribs = new TextAttributes().setFontSize(0.4f).setMaxLineLength(20);
		// Finish Turn Panel
		finishTurnButton = new LabelComponent("Finish Turn", "Arial", buttonAttribs);
		finishTurnButton.backgroundColor = Color.DARK_GREY;
		finishTurnButton.minSize = new Vector2f(0.3f, 0.3f);
		finishTurnButton.shouldInheritSizeX = true;
		optionsPanel.addChild(finishTurnButton);

		// Finish Turn Panel
		otherButton = new LabelComponent("Settings", "Arial", buttonAttribs);
		otherButton.backgroundColor = Color.DARK_GREY;
		otherButton.minSize = new Vector2f(0.3f, 0.3f);
		otherButton.shouldInheritSizeX = true;
		optionsPanel.addChild(otherButton);

		// Finish Turn Panel
		menuButton = new LabelComponent("Main Menu", "Arial", buttonAttribs);
		menuButton.backgroundColor = Color.DARK_GREY;
		menuButton.minSize = new Vector2f(0.3f, 0.3f);
		menuButton.shouldInheritSizeX = true;
		optionsPanel.addChild(menuButton);

		addPanel(gamePanel);
		build();
		show();
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
		if (finishTurnButton.isPressed())
			finishTurnButton.backgroundColor = Color.BLUE;
		if (!finishTurnButton.isPressed())
			finishTurnButton.backgroundColor = Color.DARK_GREY;

		return finishTurnButton.isClicked();
	}

	public boolean isSettingsClicked()
	{
		if (otherButton.isPressed())
			otherButton.backgroundColor = Color.BLUE;
		if (!otherButton.isPressed())
			otherButton.backgroundColor = Color.DARK_GREY;

		return otherButton.isClicked();
	}

	public boolean isMainMenuClicked()
	{
		if (menuButton.isPressed())
			menuButton.backgroundColor = Color.BLUE;
		if (!menuButton.isPressed())
			menuButton.backgroundColor = Color.DARK_GREY;

		return finishTurnButton.isClicked();
	}

	public VPanel getGridPanel()
	{
		return gridPanel;
	}

}
