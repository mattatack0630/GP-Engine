package gui.Component.Content;

import gui.Component.Component;
import gui.GuiTexture;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/10/2016.
 */
public class ButtonContent extends Content
{
	private GuiTexture buttonTexture;
	public GuiTexture staticTexture;
	public GuiTexture clickTexture;
	public GuiTexture hoverTexture;

	private String label;

	private boolean clicked;
	private boolean lastClick;
	private boolean hovered;
	private boolean pressed;
	private boolean released;

	public ButtonContent(Component parent, Vector2f position, Vector2f size)
	{
		super(parent, position, size);
		staticTexture = new GuiTexture(Color.BLACK, position, size);
		clickTexture = new GuiTexture(Color.BLACK, position, size);
		hoverTexture = new GuiTexture(Color.BLACK, position, size);
		buttonTexture = staticTexture;

		lastClick = false;
		clicked = false;
		hovered = false;
		pressed = false;
		released = false;
		label = "";
	}

	@Override
	public void onClick()
	{
		clicked = true;
		setButtonTexture(clickTexture);
	}

	@Override
	public void onHover()
	{
		hovered = true;
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
		setButtonTexture(staticTexture);
	}

	public void tick()
	{
		clicked = false;
		released = false;
		hovered = false;
		super.tick();
	}

	public void render(MasterRenderer renderer)
	{
		super.render(renderer);
		buttonTexture.setOpacity(parent.opacity);
		renderer.processGuiTexture(buttonTexture);
	}

	public void updateDimension(Vector2f position, Vector2f size)
	{
		buttonTexture.setPosition(position);
		buttonTexture.setSize(size);
		super.updateDimension(position, size);
	}

	public void setButtonTexture(GuiTexture texture)
	{
		texture.setPosition(buttonTexture.getPosition());
		texture.setSize(buttonTexture.getSize());
		buttonTexture = texture;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

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
