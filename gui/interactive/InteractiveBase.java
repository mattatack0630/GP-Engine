package gui.interactive;

import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 9/30/2016.
 */
public class InteractiveBase implements KeyUsable, Clickable
{
	protected ClickChecker clickChecker;
	protected KeyChecker keyChecker;
	private Vector2f position;
	private Vector2f size;

	private boolean released;
	private boolean hovered;
	private boolean pressed;
	private boolean clicked;
	private boolean entered;
	private boolean exited;

	private MouseEventAction m_releasedAction;
	private MouseEventAction m_pressedAction;
	private MouseEventAction m_clickedAction;
	private MouseEventAction m_hoveredAction;
	private MouseEventAction m_enteredAction;
	private MouseEventAction m_exitedAction;

	private KeyEventAction k_repressedAction;
	private KeyEventAction k_releasedAction;
	private KeyEventAction k_pressedAction;
	private KeyEventAction k_clickedAction;

	public InteractiveBase(Vector2f position, Vector2f size)
	{
		this.keyChecker = new KeyChecker(this);
		this.clickChecker = new ClickChecker(position.x(), position.y(), size.x() / 2f, size.y() / 2f, this);
		this.position = new Vector2f(position);
		this.size = new Vector2f(size);
		this.released = false;
		this.hovered = false;
		this.pressed = false;

		m_releasedAction = () -> {
		};
		m_hoveredAction = () -> {
		};
		m_pressedAction = () -> {
		};
		m_clickedAction = () -> {
		};
		m_enteredAction = () -> {
		};
		m_exitedAction = () -> {
		};
		k_repressedAction = (i, j) -> {
		};
		k_releasedAction = (i, j) -> {
		};
		k_pressedAction = (i, j) -> {
		};
		k_clickedAction = (i, j) -> {
		};
	}

	public void tick()
	{
		clicked = false;
		released = false;
		hovered = false;
		entered = false;
		pressed = false;
		exited = false;
		clickChecker.checkClick();
		keyChecker.checkKey();
	}

	public void updateInteractive(Vector2f position, Vector2f size)
	{
		clickChecker.setClickDimensions(position.x(), position.y(), size.x() / 2f, size.y() / 2f);
		this.position = new Vector2f(position);
		this.size = new Vector2f(size);
	}


	// Mouse Methods
	@Override
	public void onBeginHover()
	{
		entered = true;
		m_enteredAction.doAction();
	}

	@Override
	public void onEndHover()
	{
		exited = true;
		m_exitedAction.doAction();
	}

	@Override
	public void onHover()
	{
		hovered = true;
		m_hoveredAction.doAction();
	}

	@Override
	public void onHold()
	{
		hovered = true;
		pressed = true;
		m_pressedAction.doAction();
	}

	@Override
	public void onRelease()
	{
		pressed = false;
		released = true;
		m_releasedAction.doAction();
	}

	@Override
	public void onClick()
	{
		clicked = true;
		m_clickedAction.doAction();
	}


	// Keyboard Methods
	@Override
	public void onKeyPress(int i, char c)
	{
		//System.out.println("Pressed \tcode :" + i +" char : "+clickChecker);
		k_pressedAction.doAction(i, c);
	}

	@Override
	public void onKeyRepress(int i, char c)
	{
		//System.out.println("REPressed \tcode :" + i +" char : "+clickChecker);
		k_repressedAction.doAction(i, c);
	}

	@Override
	public void onKeyRelease(int i, char c)
	{
		//System.out.println("Released \tcode :" + i +" char : "+clickChecker);
		k_releasedAction.doAction(i, c);
	}

	@Override
	public void onKeyClick(int i, char c)
	{
		//System.out.println("Clicking \tcode :" + i +" char : "+clickChecker);
		k_clickedAction.doAction(i, c);
	}


	// State Queries
	public boolean isReleased()
	{
		return released;
	}

	public boolean isClicked()
	{
		return clicked;
	}

	public boolean isHovered()
	{
		return hovered;
	}

	public boolean isPressed()
	{
		return pressed;
	}

	public boolean isEntered()
	{
		return entered;
	}

	public boolean isExited()
	{
		return exited;
	}

	public boolean isStatic()
	{
		return !clicked && !pressed && !released && !hovered;
	}


	// Action Events
	public void setK_pressedAction(KeyEventAction k_pressedAction)
	{
		this.k_pressedAction = k_pressedAction;
	}

	public void setK_clickedAction(KeyEventAction k_clickedAction)
	{
		this.k_clickedAction = k_clickedAction;
	}

	public void setK_releasedAction(KeyEventAction k_releasedAction)
	{
		this.k_releasedAction = k_releasedAction;
	}

	public void setK_repressedAction(KeyEventAction k_repressedAction)
	{
		this.k_repressedAction = k_repressedAction;
	}

	public void setM_hoveredAction(MouseEventAction m_hoveredAction)
	{
		this.m_hoveredAction = m_hoveredAction;
	}

	public void setM_pressedAction(MouseEventAction m_pressedAction)
	{
		this.m_pressedAction = m_pressedAction;
	}

	public void setM_clickedAction(MouseEventAction m_clickedAction)
	{
		this.m_clickedAction = m_clickedAction;
	}

	public void setM_releasedAction(MouseEventAction m_releasedAction)
	{
		this.m_releasedAction = m_releasedAction;
	}

	public void setM_enteredAction(MouseEventAction m_enteredAction)
	{
		this.m_enteredAction = m_enteredAction;
	}

	public void setM_exitedAction(MouseEventAction m_exitedAction)
	{
		this.m_exitedAction = m_exitedAction;
	}

}
