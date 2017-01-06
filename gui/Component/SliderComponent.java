package gui.Component;

import gui.Component.Content.SliderContent;
import gui.GuiTexture;
import rendering.Color;
import resources.ResourceManager;
import resources.TextureResource;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/8/2016.
 */
public class SliderComponent extends Component
{
	private SliderContent sliderContent;

	public SliderComponent(Vector2f size)
	{
		super(new Vector2f());
		sliderContent = new SliderContent(this, new Vector2f(), size);
		setContent(sliderContent);
		sliderContent.setBounds(0, 1);
	}

	public void setSliderTexture(GuiTexture rail, GuiTexture button)
	{
		sliderContent.sliderRail = rail;
		sliderContent.sliderButton = button;
	}

	public void setSliderTexture(Color rail, Color button)
	{
		sliderContent.sliderRail = new GuiTexture(rail, new Vector2f(0, 0), content.size);
		sliderContent.sliderButton = new GuiTexture(button, new Vector2f(0, 0), new Vector2f(content.size.x() + 0.01f, content.size.y() / 10f));
	}

	public float getValue()
	{
		return sliderContent.getValue();
	}

	@Override
	public void applyAttrib(String name, String value)
	{
		super.applyAttrib(name, value);
		switch (name)
		{
			case "valuebounds":
				String[] data = value.split(",");
				sliderContent.setBounds(Float.parseFloat(data[0]), Float.parseFloat(data[1]));
				break;
			case "buttoncolor":
				setSliderTexture(sliderContent.sliderRail.getColor(), new Color(value));
				break;
			case "railcolor":
				setSliderTexture(new Color(value), sliderContent.sliderButton.getColor());
				break;
			case "buttonimage":
				int bi = ResourceManager.loadResource(new TextureResource(value, value)).getId();
				sliderContent.sliderButton = new GuiTexture(bi, new Vector2f(0, 0), content.size);
				break;
			case "railimage":
				int ri = ResourceManager.loadResource(new TextureResource(value, value)).getId();
				sliderContent.sliderRail = new GuiTexture(ri, new Vector2f(0, 0), content.size);
				break;
		}
	}
}
