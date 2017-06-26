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
public class VSliderComponent extends Component
{
	private Vector2f sliderSize;
	private Vector2f buttonSize;
	private GuiTexture buttonTexture;
	private GuiTexture sliderTexture;
	private boolean pressed;

	private Vector2f buttonPosition;
	private float valueNormal;
	private float value;
	private float minValue;
	private float maxValue;
	private Vector2f graplePos;

	public VSliderComponent(Vector2f position, Vector2f sliderSize, Vector2f buttonSize)
	{
		super(position);
		this.buttonSize = new Vector2f(buttonSize);
		this.sliderSize = new Vector2f(sliderSize);
		this.buttonTexture = new GuiTexture(Color.GREY, absolutePos, buttonSize);
		this.sliderTexture = new GuiTexture(Color.GREY, absolutePos, sliderSize);
		this.buttonPosition = new Vector2f();
		this.pressed = false;
		this.minValue = 0;
		this.maxValue = 1;
		this.graplePos = new Vector2f();
	}

	public VSliderComponent(Vector2f position, Vector2f sliderSize)
	{
		super(position);
		this.buttonSize = new Vector2f(sliderSize.x(), sliderSize.y() * 0.1f);
		this.sliderSize = new Vector2f(sliderSize);
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
			graplePos = Vector2f.sub(mouse, buttonPosition, null);
		}

		if (pressed && !Engine.getInputManager().isMouseButtonDown(0))
		{
			pressed = false;
		}

		if (pressed)
		{
			float bPos = mouse.y() - graplePos.y();
			float bmin = absolutePos.y() - (absoluteSize.y() / 2.0f) + (buttonSize.y() / 2.0f);
			float bmax = absolutePos.y() + (absoluteSize.y() / 2.0f) - (buttonSize.y() / 2.0f);
			buttonPosition.setY(Maths.clamp(bPos, bmin, bmax));

			valueNormal = (buttonPosition.y() - absolutePos.y()) / ((bmax - bmin) / 2.0f);
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
		sliderTexture.setClippingBounds(clippingBounds);
		buttonTexture.setClippingBounds(clippingBounds);
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

	public void setButtonSize(Vector2f buttonSize)
	{

		this.buttonSize.set(buttonSize);
	}

	public void setSliderSize(Vector2f sliderSize)
	{

		this.sliderSize.set(sliderSize);
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

		parseMap.put("v-slider-button-width", (c, value) -> ((VSliderComponent) c).buttonSize.setX(Float.parseFloat(value)));
		parseMap.put("v-slider-button-height", (c, value) -> ((VSliderComponent) c).buttonSize.setY(Float.parseFloat(value)));
		parseMap.put("v-slider-rail-width", (c, value) -> ((VSliderComponent) c).sliderSize.setX(Float.parseFloat(value)));
		parseMap.put("v-slider-rail-height", (c, value) -> ((VSliderComponent) c).sliderSize.setY(Float.parseFloat(value)));

		// path to texture
		//parseMap.put("h-slider-img", (c, value) -> ((HSliderComponent)c).sliderSize.setY(Float.parseFloat(value)));

		parseMap.put("v-slider-button-color", (c, value) -> {
			Color col = new Color(value);
			((VSliderComponent) c).buttonTexture.setColor(col);
		});

		parseMap.put("v-slider-rail-color", (c, value) -> {
			Color col = new Color(value);
			((VSliderComponent) c).sliderTexture.setColor(col);
		});
	}

	@Override
	public Component blankInstance()
	{
		return new VSliderComponent(new Vector2f(), new Vector2f(), new Vector2f());
	}

	@Override
	public String componentName()
	{
		return "v-slider";
	}

	public void setValueBounds(float min, float max)
	{
		minValue = min;
		maxValue = max;
	}

	public void setValue(float value)
	{
		this.value = Maths.clamp(value, 0, 1);
		float bmin = absolutePos.y() - (absoluteSize.y() / 2.0f) + (buttonSize.y() / 2.0f);
		float bmax = absolutePos.y() + (absoluteSize.y() / 2.0f) - (buttonSize.y() / 2.0f);
		buttonPosition.setY(Interpolation.linearInterpolate(bmax, bmin, this.value));
	}
}
