package gui.Component.Content;

import com.sun.istack.internal.Nullable;
import gui.Component.Component;
import gui.Text.GuiText;
import gui.Text.TextAttributes;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/9/2016.
 */
public class TextContent extends Content
{
	public GuiText text;

	public TextContent(Component parent, Vector2f position, String text, TextAttributes attribs)
	{
		super(parent, new Vector2f(), new Vector2f());

		this.text = new GuiText(text, attribs.getFont(), position, attribs);

		updateDimension(position, this.text.getSize());
	}

	public void updateDimension(Vector2f position, Vector2f size)
	{
		text.setPosition(position);

		super.updateDimension(text.getCenterPosition(), size);
	}

	public void render(MasterRenderer renderer)
	{
		super.render(renderer);
		text.setOpacity(parent.opacity);
		text.setOpacity(parent.opacity);
		renderer.processGuiText(text);
	}

	@Override
	public void update()
	{
		text.update();
		size = text.getSize();
	}

	public void updateText(String text, @Nullable TextAttributes attribs)
	{
		if (attribs != null)
			this.text.setAttribs(attribs);

		this.text.setText(text);
		this.text.update();

		this.updateDimension(position, size);
	}
}
