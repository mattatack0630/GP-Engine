package gui.components;

import engine.Engine;
import gui.GuiTexture;
import parsing.gxml.ParseAction;
import rendering.Color;
import resources.TextureResource;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by mjmcc on 4/7/2017.
 */
public class TextureComponent extends Component
{
	public GuiTexture texture;
	public Vector2f textureSize;

	public TextureComponent(Vector2f position, Vector2f size, GuiTexture texture)
	{
		super(position);
		this.texture = texture;
		this.textureSize = new Vector2f(size);
	}

	@Override
	public void tickContent()
	{

	}

	@Override
	public void renderContent()
	{
		// change
		Engine.getRenderManager().processGuiTexture(texture);
	}

	@Override
	public void syncContent()
	{
		this.texture.setPosition(absolutePos);
		this.texture.setSize(absoluteSize);
		this.texture.setClippingBounds(clippingBounds);
		this.texture.setRenderLevel(renderLevel);
		this.texture.setOpacity(opacity);
	}

	@Override
	public Vector2f getContentSize()
	{
		return textureSize;
	}

	@Override
	public void addToParsingFuncMap(Map<String, ParseAction> parseMap)
	{
		parseMap.put("texture-width", (c, value) -> ((TextureComponent) c).textureSize.setX(Float.parseFloat(value)));
		parseMap.put("texture-height", (c, value) -> ((TextureComponent) c).textureSize.setY(Float.parseFloat(value)));
		parseMap.put("texture-color", (c, value) -> {
			((TextureComponent) c).texture.setColor(new Color(value));
		});
		parseMap.put("texture-img-path", (c, value) -> {
			TextureResource resource = new TextureResource(value, value);
			resource = Engine.getResourceManager().directLoadResource(resource);
			((TextureComponent) c).texture.setTexture(resource.getId());
		});
		// path to texture
	}

	@Override
	public Component blankInstance()
	{
		return new TextureComponent(new Vector2f(), new Vector2f(), new GuiTexture(Color.NONE, new Vector2f(), new Vector2f()));
	}

	@Override
	public String componentName()
	{
		return "texture";
	}

	public void setColor(Color color)
	{
		this.texture.setColor(color);
	}
}
