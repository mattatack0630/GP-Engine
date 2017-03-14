package gui.Component;

import engine.Engine;
import gui.Component.Content.ButtonContent;
import gui.GuiTexture;
import rendering.Color;
import resources.TextureResource;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/8/2016.
 */
public class ButtonComponent extends Component
{
	private ButtonContent buttonContent;

	public ButtonComponent(Vector2f size)
	{
		super(new Vector2f());
		buttonContent = new ButtonContent(this, new Vector2f(), size);
		setContent(buttonContent);
	}

	public void setStaticTexture(GuiTexture texture)
	{
		buttonContent.staticTexture = texture;
		buttonContent.setButtonTexture(buttonContent.staticTexture);
	}

	public void setOnClickTexture(GuiTexture texture)
	{
		buttonContent.clickTexture = texture;
		buttonContent.setButtonTexture(buttonContent.staticTexture);
	}

	public boolean isClicked()
	{
		return buttonContent.isClicked();
	}

	public boolean isHeld()
	{
		return buttonContent.isPressed();
	}

	public boolean isReleased()
	{
		return buttonContent.isReleased();
	}

	public boolean isHovered()
	{
		return buttonContent.isHovered();
	}

	@Override
	public void applyAttrib(String name, String value)
	{
		super.applyAttrib(name, value);
		switch (name)
		{
			case "clickimage":
				int cId = Engine.getResourceManager().loadResource(new TextureResource(value, value)).getId();
				setOnClickTexture(new GuiTexture(cId, absolutePos, absoluteSize));
				break;

			case "hoverimage":
				//int hId = VaoLoader.loadTexture(value);
				//setOnClickTexture(new GuiTexture(hId, absolutePos, absoluteSize));
				break;

			case "staticimage":
				int sId = Engine.getResourceManager().loadResource(new TextureResource(value, value)).getId();
				setStaticTexture(new GuiTexture(sId, absolutePos, absoluteSize));
				break;

			case "clickcolor":
				setOnClickTexture(new GuiTexture(new Color(value), absolutePos, absoluteSize));
				break;

			case "hovercolor":
				//setOnClickTexture(new GuiTexture(new Color(value), absolutePos, absoluteSize));
				break;

			case "staticcolor":
				setStaticTexture(new GuiTexture(new Color(value), absolutePos, absoluteSize));
				break;
		}
	}
}
