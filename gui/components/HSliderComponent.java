package gui.components;

import engine.Engine;
import gui.GuiTexture;
import parsing.gxml.ParseAction;
import rendering.Color;
import utils.math.Interpolation;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by mjmcc on 4/9/2017.
 */
public class HSliderComponent extends Component
{
	private Vector2f sliderSize;
	private Vector2f buttonSize;
	private GuiTexture buttonTexture;
	private GuiTexture sliderTexture;
	private Vector2f buttonPosition;

	private boolean pressed;
	private float valueNormal;
	private float minValue;
	private float maxValue;
	private float value;
	private Vector2f graplePos;

	public HSliderComponent(Vector2f position, Vector2f sliderSize, Vector2f buttonSize)
	{
		super(position);
		this.buttonSize = new Vector2f(buttonSize);
		this.sliderSize = new Vector2f(sliderSize);
		this.buttonTexture = new GuiTexture(Color.GREY, absolutePos, buttonSize);
		this.sliderTexture = new GuiTexture(Color.GREY, absolutePos, sliderSize);
		this.buttonPosition = new Vector2f();
		this.graplePos = new Vector2f();
		this.pressed = false;
		this.minValue = 0;
		this.maxValue = 1;
	}

	public HSliderComponent(Vector2f position, Vector2f sliderSize)
	{
		super(position);
		this.sliderSize = new Vector2f(sliderSize);
		this.buttonSize = new Vector2f(sliderSize.x() * 0.1f, sliderSize.y());
		this.buttonTexture = new GuiTexture(Color.GREY, absolutePos, buttonSize);
		this.sliderTexture = new GuiTexture(Color.GREY, absolutePos, sliderSize);
		this.buttonPosition = new Vector2f();
		this.pressed = false;
		this.minValue = 0;
		this.maxValue = 1;
	}

	@Override
	public void tickContent()
	{
		Vector2f mouse = new Vector2f(Engine.getInputManager().getGLCursorCoords());

		if (clickingButton(mouse))
		{
			pressed = true;
			graplePos = Vector2f.sub(Engine.getInputManager().getGLCursorCoords(), buttonPosition, null);
		}

		if (pressed && !Engine.getInputManager().isMouseButtonDown(0))
			pressed = false;

		if (pressed)
		{
			float bPos = mouse.x() - graplePos.x();
			float bmin = absolutePos.x() - (absoluteSize.x() / 2.0f) + (buttonSize.x() / 2.0f);
			float bmax = absolutePos.x() + (absoluteSize.x() / 2.0f) - (buttonSize.x() / 2.0f);
			buttonPosition.setX(Maths.clamp(bPos, bmin, bmax));

			valueNormal = (buttonPosition.x() - absolutePos.x()) / ((bmax - bmin) / 2.0f);
			valueNormal = Maths.clamp(valueNormal, -1.0f, 1.0f);
			value = Maths.map(valueNormal, -1, 1, minValue, maxValue);
		}
	}

	private boolean clickingButton(Vector2f mouse)
	{
		boolean insideX = mouse.x() > buttonPosition.x() - (buttonSize.x() / 2.0f) && mouse.x() < buttonPosition.x() + (buttonSize.x() / 2.0f);
		boolean insideY = mouse.y() > buttonPosition.y() - (buttonSize.y() / 2.0f) && mouse.y() < buttonPosition.y() + (buttonSize.y() / 2.0f);
		boolean clicked = isClicked() && insideX && insideY;
		return clicked;
	}

	@Override
	public void renderContent()
	{
		buttonTexture.setPosition(buttonPosition);
		Engine.getRenderManager().processGuiTexture(sliderTexture);
		Engine.getRenderManager().processGuiTexture(buttonTexture);
	}

	@Override
	public void syncContent()
	{
		buttonPosition.set(absolutePos.x(), absolutePos.y());
		buttonTexture.setPosition(absolutePos);
		sliderTexture.setPosition(absolutePos);
		sliderTexture.setSize(sliderSize);
		buttonTexture.setSize(buttonSize);
		sliderTexture.setOpacity(opacity);
		buttonTexture.setOpacity(opacity);
		sliderTexture.setRenderLevel(renderLevel + 0.55f);
		buttonTexture.setRenderLevel(renderLevel + 0.60f);
		buttonTexture.setClippingBounds(clippingBounds);
		sliderTexture.setClippingBounds(clippingBounds);
	}

	@Override
	public Vector2f getContentSize()
	{

		return sliderSize;
	}

	public float getValue()
	{

		return value;
	}

	public void setMaxValue(float maxValue)
	{

		this.maxValue = maxValue;
	}

	public void setMinValue(float minValue)
	{

		this.minValue = minValue;
	}

	public void setValueBounds(float min, float max)
	{
		minValue = min;
		maxValue = max;
	}

	public void setValue(float value)
	{
		this.value = Maths.clamp(value, 0, 1);
		float bmin = absolutePos.x() - (absoluteSize.x() / 2.0f) + (buttonSize.x() / 2.0f);
		float bmax = absolutePos.x() + (absoluteSize.x() / 2.0f) - (buttonSize.x() / 2.0f);
		buttonPosition.setX(Interpolation.linearInterpolate(bmax, bmin, this.value));
	}

	public void setButtonSize(Vector2f buttonSize)
	{

		this.buttonSize = buttonSize;
	}

	public void setSliderSize(Vector2f sliderSize)
	{

		this.sliderSize = sliderSize;
	}

	public void setSliderTexture(GuiTexture sliderTexture)
	{

		this.sliderTexture = sliderTexture;
	}

	public void setButtonTexture(GuiTexture buttonTexture)
	{

		this.buttonTexture = buttonTexture;
	}

	@Override
	public void addToParsingFuncMap(Map<String, ParseAction> parseMap)
	{
		super.addToParsingFuncMap(parseMap);

		parseMap.put("h-slider-button-width", (c, value) -> ((HSliderComponent) c).buttonSize.setX(Float.parseFloat(value)));
		parseMap.put("h-slider-button-height", (c, value) -> ((HSliderComponent) c).buttonSize.setY(Float.parseFloat(value)));
		parseMap.put("h-slider-width", (c, value) -> ((HSliderComponent) c).sliderSize.setX(Float.parseFloat(value)));
		parseMap.put("h-slider-height", (c, value) -> ((HSliderComponent) c).sliderSize.setY(Float.parseFloat(value)));

		// path to texture
		//parseMap.put("h-slider-img", (c, value) -> ((HSliderComponent)c).sliderSize.setY(Float.parseFloat(value)));

		parseMap.put("h-slider-button-color", (c, value) -> {
			Color col = new Color(value);
			((HSliderComponent) c).buttonTexture.setColor(col);
		});

		parseMap.put("h-slider-rail-color", (c, value) -> {
			Color col = new Color(value);
			((HSliderComponent) c).sliderTexture.setColor(col);
		});
	}

	@Override
	public Component blankInstance()
	{
		return new HSliderComponent(new Vector2f(), new Vector2f(), new Vector2f());
	}

	@Override
	public String componentName()
	{
		return "h-slider";
	}
}
