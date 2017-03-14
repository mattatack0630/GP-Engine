package gui.Component.Content;

import gui.Component.Component;
import gui.InteractiveComponents.InteractiveBase;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by Matthew McCloskey on 10/4/2016.
 * A template to place inside of components
 */
public abstract class Content extends InteractiveBase
{
	public Component parent;
	public Vector2f position;
	public Vector2f size;

	public Content(Component parent, Vector2f position, Vector2f size)
	{
		super(position, size);

		this.parent = parent;
		this.position = new Vector2f(position);
		this.size = new Vector2f(size);
	}

	/* Update the content */
	public void tick()
	{
		super.tick();
	}

	/* Draw the content */
	public void render(MasterRenderer renderer)
	{
		//Trinket2D.drawRect(position, size, Color.RED);
	}

	public void updateDimension(Vector2f position, Vector2f size)
	{
		this.position = position;
		this.size = size;
		super.updateInteractive(position, size);
	}

	public void setParent(Component c)
	{
		this.parent = c;
	}

	public Vector2f getSize()
	{
		return size;
	}

	public float getWidth()
	{
		return size.x();
	}

	public float getHeight()
	{
		return size.y();
	}

	public void update()
	{
	}
}
