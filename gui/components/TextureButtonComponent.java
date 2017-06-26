package gui.components;

import gui.GuiTexture;
import parsing.gxml.ParseAction;
import rendering.Color;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by mjmcc on 4/7/2017.
 */
public class TextureButtonComponent extends ButtonComponent
{
	public TextureComponent staticTexture;
	public TextureComponent hoverTexture;
	public TextureComponent clickTexture;
	public TextureComponent pressedTexture;
	public TextureComponent releaseTexture;

	public TextureButtonComponent(Vector2f position, Vector2f size)
	{
		super(position, size);
		this.staticTexture = new TextureComponent(position, size, new GuiTexture(Color.NONE, position, size));
		this.hoverTexture = new TextureComponent(position, size, new GuiTexture(Color.NONE, position, size));
		this.clickTexture = new TextureComponent(position, size, new GuiTexture(Color.NONE, position, size));
		this.pressedTexture = new TextureComponent(position, size, new GuiTexture(Color.NONE, position, size));
		this.releaseTexture = new TextureComponent(position, size, new GuiTexture(Color.NONE, position, size));
	}

	public TextureButtonComponent(Vector2f position, Vector2f size, GuiTexture staticTexture, GuiTexture hoverTexture,
								  GuiTexture clickTexture, GuiTexture pressedTexture, GuiTexture releaseTexture)
	{
		super(position, size);
		this.staticTexture = new TextureComponent(position, size, staticTexture);
		this.hoverTexture = new TextureComponent(position, size, hoverTexture);
		this.clickTexture = new TextureComponent(position, size, clickTexture);
		this.pressedTexture = new TextureComponent(position, size, pressedTexture);
		this.releaseTexture = new TextureComponent(position, size, releaseTexture);
	}

	@Override
	public void tickContent()
	{
		if (isClicked())
			addComponent(clickTexture);
		if (isHovered())
			addComponent(hoverTexture);
		if (isPressed())
			addComponent(pressedTexture);
		if (isReleased())
			addComponent(releaseTexture);
		if (isStatic())
			addComponent(staticTexture);

		super.tickContent();
	}

	public void setStaticTexture(GuiTexture staticTexture)
	{
		this.staticTexture = new TextureComponent(absolutePos, panelSize, staticTexture);
	}

	public void setHoverTexture(GuiTexture hoverTexture)
	{
		this.hoverTexture = new TextureComponent(absolutePos, panelSize, hoverTexture);
	}

	public void setClickTexture(GuiTexture clickTexture)
	{
		this.clickTexture = new TextureComponent(absolutePos, panelSize, clickTexture);
	}

	public void setReleaseTexture(GuiTexture releaseTexture)
	{
		this.releaseTexture = new TextureComponent(absolutePos, panelSize, releaseTexture);
	}

	public void setPressTexture(GuiTexture pressTexture)
	{
		this.pressedTexture = new TextureComponent(absolutePos, panelSize, pressTexture);
	}

	@Override
	public void syncContent()
	{
		staticTexture.textureSize.set(absoluteSize.x(), absoluteSize.y());
		hoverTexture.textureSize.set(absoluteSize.x(), absoluteSize.y());
		clickTexture.textureSize.set(absoluteSize.x(), absoluteSize.y());
		pressedTexture.textureSize.set(absoluteSize.x(), absoluteSize.y());
		releaseTexture.textureSize.set(absoluteSize.x(), absoluteSize.y());

		staticTexture.absolutePos.set(absolutePos.x(), absolutePos.y());
		hoverTexture.absolutePos.set(absolutePos.x(), absolutePos.y());
		clickTexture.absolutePos.set(absolutePos.x(), absolutePos.y());
		pressedTexture.absolutePos.set(absolutePos.x(), absolutePos.y());
		releaseTexture.absolutePos.set(absolutePos.x(), absolutePos.y());

		staticTexture.syncContent();
		hoverTexture.syncContent();
		clickTexture.syncContent();
		pressedTexture.syncContent();
		releaseTexture.syncContent();
	}

	@Override
	public void addToParsingFuncMap(Map<String, ParseAction> parseMap)
	{
		super.addToParsingFuncMap(parseMap);

		parseMap.put("button-static-color", (c, value) -> {
			Color col = new Color(value);
			((TextureButtonComponent) c).staticTexture.setColor(col);
		});

		parseMap.put("button-hovered-color", (c, value) -> {
			Color col = new Color(value);
			((TextureButtonComponent) c).hoverTexture.setColor(col);
		});

		parseMap.put("button-pressed-color", (c, value) -> {
			Color col = new Color(value);
			((TextureButtonComponent) c).pressedTexture.setColor(col);
		});

		parseMap.put("button-clicked-color", (c, value) -> {
			Color col = new Color(value);
			((TextureButtonComponent) c).clickTexture.setColor(col);
		});

		parseMap.put("button-released-color", (c, value) -> {
			Color col = new Color(value);
			((TextureButtonComponent) c).releaseTexture.setColor(col);
		});
	}

	@Override
	public Component blankInstance()
	{
		return new TextureButtonComponent(new Vector2f(), new Vector2f());
	}

	@Override
	public String componentName()
	{
		return "texture-button";
	}
}
