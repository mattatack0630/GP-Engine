package tests;

import gui.Align;
import gui.text.TextAttributes;
import gui.components.HPanel;
import gui.components.LabelComponent;
import gui.components.VPanel;
import rendering.Color;
import utils.ColorUtils;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

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

		playerPanel = new VPanel(new Vector2f(), new Vector2f());
		Vector3f c = player.getColor();
		playerColor = new Color(c.x(), c.y(), c.z(), 0.5f);
		playerPanel.shouldInheritSizeX = true;
		playerPanel.shouldInheritSizeY = true;
		playerPanel.backgroundColor = playerColor;
		playerPanel.alignment = Align.TOP_CENTER;
		playerPanel.margin = new Vector4f(0.01f);

		TextAttributes playerAttribs = new TextAttributes();
		playerAttribs.setFont("Geo");
		playerAttribs.setFontSize(0.35f);
		playerAttribs.setSharpness(0.85f);
		playerAttribs.setColor(Color.WHITE);
		playerLabel = new LabelComponent(new Vector2f(), "Player" + (i + 1), playerAttribs);
		playerLabel.backgroundColor = ColorUtils.changedBrightness(playerColor, 0.3f);
		playerLabel.shouldInheritSizeX = true;
		playerLabel.contentColor = new Color(1.0f, 1.0f, 0.0f, 0.7f);
		playerPanel.addComponent(playerLabel);

		HPanel bpanel = new HPanel(new Vector2f(), new Vector2f());
		bpanel.shouldInheritSizeY = true;
		bpanel.shouldInheritSizeX = true;
		playerPanel.addComponent(bpanel);

		TextAttributes nameAttribs = new TextAttributes();
		nameAttribs.setFont("Geo");
		nameAttribs.setFontSize(0.5f);
		nameAttribs.setSharpness(0.85f);
		nameAttribs.setColor(Color.WHITE);
		nameLabel = new LabelComponent(new Vector2f(), player.getName(), nameAttribs);
		nameLabel.shouldInheritSizeX = true;
		nameLabel.shouldInheritSizeY = true;
		nameLabel.margin = new Vector4f(0.01f);
		nameLabel.alignment = Align.BOTTOM_LEFT;
		bpanel.addComponent(nameLabel);

		TextAttributes cellAttribs = new TextAttributes();
		cellAttribs.setFont("Geo");
		cellAttribs.setFontSize(0.40f);
		cellAttribs.setSharpness(0.85f);
		cellAttribs.setColor(Color.WHITE);
		cellLabel = new LabelComponent(new Vector2f(), "~10", cellAttribs);
		cellLabel.shouldInheritSizeX = true;
		cellLabel.shouldInheritSizeY = true;
		cellLabel.margin = new Vector4f(0.01f);
		cellLabel.alignment = Align.BOTTOM_RIGHT;
		bpanel.addComponent(cellLabel);


		playerOnLight = new VPanel(new Vector2f(), new Vector2f());
		playerOnLight.shouldInheritSizeX = true;
		playerOnLight.minSize.set(0.0f, 0.02f);
		playerOnLight.margin.set(0.0f, 0.0f, 0.0f, 0.0f);
		playerOnLight.backgroundColor = new Color(1.0f, 1.0f, 0.0f, 1.0f);
		playerPanel.addComponent(playerOnLight);
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
		cellLabel.setText("~" + i);

	}
}
