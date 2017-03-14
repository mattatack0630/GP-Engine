package gui.InteractiveComponents;

import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 9/30/2016.
 */
public class InteractiveBase implements KeyUsable, Clickable
{
	private ClickChecker clickChecker;
	private KeyChecker keyChecker;
	private Vector2f position;
	private Vector2f size;

	private boolean released;
	private boolean hovered;
	private boolean pressed;
	private boolean clicked;

	public InteractiveBase(Vector2f position, Vector2f size)
	{
		this.keyChecker = new KeyChecker(this);
		this.clickChecker = new ClickChecker(position.x(), position.y(), size.x() / 2f, size.y() / 2f, this);
		this.position = new Vector2f(position);
		this.size = new Vector2f(size);
		this.released = false;
		this.hovered = false;
		this.pressed = false;
	}

	public void tick()
	{
		clicked = false;
		released = false;
		hovered = false;
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
		//systems.out.println("Enter");
	}

	@Override
	public void onEndHover()
	{
		//systems.out.println("Exit");
	}

	@Override
	public void onPress()
	{
		//systems.out.println("Pressing \tcode :" + i +" char : "+clickChecker);
	}

	@Override
	public void onHold()
	{
		hovered = true;
		pressed = true;
	}

	@Override
	public void onRelease()
	{
		pressed = false;
		released = true;
	}

	@Override
	public void onClick()
	{
		clicked = true;
	}

	@Override
	public void onHover()
	{
		hovered = true;
	}


	// Keyboard Methods
	@Override
	public void onKeyPress(int i, char c)
	{
		//System.out.println("Pressed \tcode :" + i +" char : "+clickChecker);
	}

	@Override
	public void onKeyRepress(int i, char c)
	{
		//System.out.println("REPressed \tcode :" + i +" char : "+clickChecker);
	}

	@Override
	public void onKeyRelease(int i, char c)
	{
		//System.out.println("Released \tcode :" + i +" char : "+clickChecker);
	}

	@Override
	public void onKeyClick(int i, char c)
	{
		//System.out.println("Clicking \tcode :" + i +" char : "+clickChecker);
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
}
