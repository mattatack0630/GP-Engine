package gui;

import gui.Component.Component;
import gui.Component.LabelComponent;
import gui.Component.Panel.Panel;
import gui.Transition.MoveTransition;
import gui.Transition.OpacityTransition;
import gui.XML.SXMLParser;
import utils.Timer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/21/2016.
 */
public class PopupGui extends GuiScene
{
	private static final String POPUP_TEMPLATE_LOC = "popup.xml";

	private OpacityTransition fade;
	private MoveTransition movement;

	private Panel contentPanel;
	private LabelComponent messageLabel;

	private Align alignment;
	private Vector2f startPos;
	private Vector2f endPos;

	private float timeUp;
	private Timer timer;

	public PopupGui(String message, float timeUp, float speed, Align alignment, String srcXml)
	{
		this.addPanels(SXMLParser.parse(srcXml).getDirectChildren());

		this.messageLabel = (LabelComponent) this.getChildById("message");
		this.contentPanel = (Panel) this.getChildById("messagePanel");
		this.messageLabel.setText(message);
		this.build();
		this.show();

		this.alignment = alignment;
		calculateMovement(alignment);

		this.setPosition(endPos);
		this.movement = new MoveTransition(startPos, speed);
		this.fade = new OpacityTransition(1, speed);

		this.timer = new Timer();
		this.timeUp = timeUp;
		timer.start();
	}

	public PopupGui(String message, float timeUp, float speed, Align alignment)
	{
		this(message, timeUp, speed, alignment, POPUP_TEMPLATE_LOC);
	}

	/**
	 * Set the source xml to a different file than the defualt
	 * Your new xml sheet must contain 2 sections with the id's
	 * - title
	 * - content
	 *
	 * @param path the path to the new xml source, from gui Res folder
	 */
	public void setPopupSource(String path)
	{
		// Check for content, message, etc
		directChildren.clear();
		addPanels(SXMLParser.parse(path).getDirectChildren());
		calculateMovement(alignment);
	}

	private void calculateMovement(Align alignment)
	{
		// Get guiScene size
		Vector2f s = size;
		Vector2f a = alignment.toVector2f();
		this.startPos = new Vector2f(a.x() - a.x() * s.x() / 2f, a.y() - a.y() * s.y() / 2f);
		this.endPos = new Vector2f(a.x() - ((a.y() * a.y() * 2) - 1) * a.x() * s.x() / 2f, a.y() + a.y() * s.y() / 2f);
	}

	public void tick()
	{
		if (timer.isTimeAt(timeUp))
		{
			movement.setToNewPosition(endPos);
			fade.setNewOpacity(0);
		}

		applyTransition(movement);
		applyTransition(fade);
		checkDead();

		super.tick();
	}

	public void setMovement(Vector2f from, Vector2f to, float speed)
	{
		startPos = new Vector2f(from);
		endPos = new Vector2f(to);
		setPosition(endPos);
		movement = new MoveTransition(startPos, speed);
	}

	public Panel getContentPanel()
	{
		return contentPanel;
	}

	public void checkDead()
	{
		Vector2f distLeft = Vector2f.sub(position, endPos, null);
		boolean dead = timer.isTimeAt(timeUp) && Math.abs(distLeft.lengthSquared()) < 0.001f;
		setRemovable(dead);
	}

	public void addComponent(Component c)
	{
		contentPanel.addChild(c);
		build();
	}

	@Override
	public void onScreenClose()
	{
		timer.pause();
	}

	@Override
	public void onScreenOpen()
	{
		timer.start();
	}

	public void setTitle(String title)
	{
		((LabelComponent) getChildById("title")).setText(title);
	}
}
