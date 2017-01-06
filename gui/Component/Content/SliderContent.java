package gui.Component.Content;

import gui.Component.Component;
import gui.GuiTexture;
import org.lwjgl.input.Mouse;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/10/2016.
 */
public class SliderContent extends Content
{
	public GuiTexture sliderRail;
	public GuiTexture sliderButton;

	private float value;
	private float max;
	private float min;

	public SliderContent(Component parent, Vector2f position, Vector2f size)
	{
		super(parent, position, size);

		sliderButton = new GuiTexture(Color.RED, new Vector2f(0, 0), new Vector2f(size.x() + 0.01f, size.y() / 10f));
		sliderRail = new GuiTexture(Color.BLUE, position, size);
		sliderRail.setOpacity(parent.opacity);
		sliderButton.setOpacity(parent.opacity);

		value = 0;
		max = 1.0f;
		min = 0.1f;
	}

	@Override
	public void onHold()
	{
		calculateValue(Maths.glCoordsFromPixle(new Vector2f(Mouse.getX(), Mouse.getY())).y());
	}

	@Override
	public void onBeginHover()
	{
		//Mouse.getDWheel();
	}

	@Override
	public void onHover()
	{
		float currValue = Mouse.getDWheel();

		if (currValue == 0)
			return;

		currValue = currValue / Math.abs(currValue) * (.05f * size.y());
		calculateValue(sliderButton.getPosition().y() + currValue);
	}

	public void calculateValue(float nv)
	{
		// Calculate the size such that the button wont go past the rail
		float actualSize = (size.y() - sliderButton.getSize().y());

		value = (actualSize / 2 + (nv - position.y())) / actualSize;
		value = Math.min(value, 1);
		value = Math.max(value, 0);
	}

	public float getValue()
	{
		return (value * Math.abs(max - min)) + min;
	}

	public void tick()
	{
		super.tick();

		Vector2f sliderPos = new Vector2f(position);
		sliderPos.setY(sliderPos.y() + ((size.y() - sliderButton.getSize().y()) / 2 * (value * 2 - 1)));

		sliderButton.setPosition(sliderPos);
		sliderRail.setPosition(position);
		sliderRail.setSize(size);
	}

	public void render(MasterRenderer renderer)
	{
		super.render(renderer);
		sliderRail.setOpacity(parent.opacity);
		sliderButton.setOpacity(parent.opacity);
		renderer.processGuiTexture(sliderRail);
		renderer.processGuiTexture(sliderButton);
	}

	public void setBounds(float min, float max)
	{
		this.min = min;
		this.max = max;
	}

	@Override
	public void update()
	{
		sliderRail.setSize(size);
		sliderButton.setSize(new Vector2f(size.x() + 0.01f, size.y() / 10f));
	}
}
