package gui.Component.Content;

import gui.Component.Component;
import gui.GuiTexture;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/10/2016.
 */
public class TextureContent extends Content
{
	GuiTexture texture;
	public TextureContent(Component parent, Vector2f position, Vector2f size, int texId)
	{
		super(parent, position, size);
		texture = new GuiTexture(texId, position, size);
		texture.setOpacity(parent.opacity);
	}

	@Override
	public void updateDimension(Vector2f position, Vector2f size)
	{
		texture.setPosition(position);
		texture.setSize(size);
		super.updateDimension(position, size);
	}

	@Override
	public void render(MasterRenderer renderer)
	{
		renderer.processGuiTexture(texture);
	}
}
