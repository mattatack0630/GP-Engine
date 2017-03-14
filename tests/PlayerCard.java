package tests;

import gui.Align;
import gui.Component.LabelComponent;
import gui.Component.Panel.HPanel;
import gui.Component.Panel.VPanel;
import gui.Text.TextAttributes;
import rendering.Color;
import utils.ColorUtils;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 2/17/2017.
 */
public class PlayerCard
{
	private Player player;
	private Color playerColor;
	private VPanel playerPanel;
	private VPanel playerOnLight;
	private LabelComponent nameLabel;
	private LabelComponent cellLabel;
	private LabelComponent playerLabel;

	public PlayerCard(Player player, int i)
	{
		this.player = player;

		playerPanel = new VPanel();
		Vector3f c = player.getColor();
		playerColor = new Color(c.x(), c.y(), c.z(), 0.5f);
		playerPanel.shouldInheritSizeX = true;
		playerPanel.shouldInheritSizeY = true;
		playerPanel.backgroundColor = playerColor;
		playerPanel.alignment = Align.TOP_CENTER;

		TextAttributes playerAttribs = new TextAttributes();
		playerAttribs.setFontSize(0.3f);
		playerAttribs.setColor(Color.WHITE);
		playerAttribs.setSharpness(0.1f);
		playerLabel = new LabelComponent("Player" + (i + 1), "Arial", playerAttribs);
		playerLabel.backgroundColor = ColorUtils.changedBrightness(playerColor, 0.3f);
		playerLabel.shouldInheritSizeX = true;
		playerLabel.contentColor = new Color(1.0f, 1.0f, 0.0f, 0.7f);
		playerPanel.addChild(playerLabel);

		HPanel bpanel = new HPanel();
		playerPanel.addChild(bpanel);

		TextAttributes nameAttribs = new TextAttributes();
		nameAttribs.setFontSize(0.3f);
		nameAttribs.setColor(Color.WHITE);
		nameAttribs.setSharpness(0.15f);
		nameLabel = new LabelComponent(player.getName(), "Arial", nameAttribs);
		bpanel.addChild(nameLabel);

		TextAttributes cellAttribs = new TextAttributes();
		cellAttribs.setFontSize(0.3f);
		cellAttribs.setColor(Color.WHITE);
		cellAttribs.setSharpness(0.15f);
		cellLabel = new LabelComponent("x10", "Arial", cellAttribs);
		bpanel.addChild(cellLabel);

		playerOnLight = new VPanel();
		playerOnLight.shouldInheritSizeX = true;
		playerOnLight.minSize.set(0.0f, 0.02f);
		playerOnLight.margin.set(0.0f, 0.0f, 0.0f, 0.0f);
		playerOnLight.backgroundColor = new Color(1.0f, 1.0f, 0.0f, 1.0f);
		playerPanel.addChild(playerOnLight);
	}

	public VPanel getPlayerPanel()
	{
		return playerPanel;
	}

	public LabelComponent getNameLabel()
	{
		return nameLabel;
	}

	public LabelComponent getCellLabel()
	{
		return cellLabel;
	}

	public LabelComponent getPlayerLabel()
	{
		return playerLabel;
	}

	public void setHighlight(boolean highlight)
	{
		playerOnLight.backgroundColor = highlight ? new Color(1.0f, 1.0f, 0.0f, 1.0f) : playerColor;
	}

	public void setCellCount(int i)
	{
		cellLabel.setText("x" + i);

	}
}
