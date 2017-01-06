package gui.InteractiveComponents;

import input.KeyHandler;
import input.KeyUsable;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 9/30/2016.
 */
public class InteractiveBase implements KeyUsable, Clickable
{
	private ClickChecker c;
	private Vector2f position;
	private Vector2f size;

	public InteractiveBase(Vector2f position, Vector2f size)
	{
		KeyHandler.addKeyUser(this);
		c = new ClickChecker(position.x(), position.y(), size.x() / 2f, size.y() / 2f, this);
		this.position = new Vector2f(position);
		this.size = new Vector2f(size);
	}

	public void tick()
	{
		c.checkClick();
	}

	public void updateInteractive(Vector2f position, Vector2f size)
	{
		c.setClickDimensions(position.x(), position.y(), size.x() / 2f, size.y() / 2f);
		this.position = new Vector2f(position);
		this.size = new Vector2f(size);
	}

	@Override
	public void onHover()
	{
		//systems.out.println("Hover "+this.getClass());
	}

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
	public void onClick()
	{
		//systems.out.println("Clicked");
	}

	@Override
	public void onRelease()
	{
		//systems.out.println("Released");
	}

	@Override
	public void onHold()
	{
		//systems.out.println("Holding Key");
	}

	@Override
	public void onPress()
	{
		//systems.out.println("Pressing \tcode :" + i +" char : "+c);
	}


	@Override
	public void onKeyPress(int i, char c)
	{
		//systems.out.println("Pressed \tcode :" + i +" char : "+c);
	}

	@Override
	public void onKeyRepress(int i, char c)
	{
		//systems.out.println("Pressed \tcode :" + i +" char : "+c);
	}

	@Override
	public void onKeyRelease(int i, char c)
	{
		//systems.out.println("Released \tcode :" + i +" char : "+c);
	}

	@Override
	public void onKeyHold(int i, char c)
	{
		//systems.out.println("Holding \tcode :" + i +" char : "+c);
	}
}
