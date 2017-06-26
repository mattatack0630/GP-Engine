package gui.components;

import engine.Engine;
import gui.Align;
import gui.text.GuiText;
import gui.text.TextAttributes;
import org.lwjgl.input.Keyboard;
import parsing.gxml.ParseAction;
import rendering.Color;
import rendering.renderers.Trinket2D;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by mjmcc on 4/10/2017.
 */
public class TextInputComponent extends Component
{
	public static final float BLINKER_WIDTH = 0.01f;
	private static final float BLINKER_RATE = 1.0f;
	private static final float BLINKER_DUR = 0.5f;

	public Vector2f textBoxSize;
	public Vector2f blinkerSize;
	public Vector2f blinkerPosition;

	public Color boxColor;
	public Color blinkerColor;

	public GuiText renderableText;
	public int maxInputLength;
	public boolean selected;
	public String inputText;
	public Align alignment;

	public TextInputComponent(Vector2f position, Vector2f size)
	{
		super(position);
		this.selected = false;
		this.maxInputLength = 15;
		this.inputText = new String();
		this.alignment = Align.TOP_LEFT;

		this.textBoxSize = new Vector2f(size);
		this.boxColor = new Color();

		this.blinkerSize = new Vector2f(BLINKER_WIDTH, size.y() - BLINKER_WIDTH);
		this.blinkerPosition = new Vector2f();
		this.blinkerColor = new Color();

		this.keyChecker.setRepressTime(0.40f);
		this.keyChecker.setRepressRate(0.10f);

		this.renderableText = new GuiText("", new Vector2f(), new TextAttributes());
		this.renderableText.getAttribs().setMaxLineLength(maxInputLength);
	}

	public void handleInputKey(int i, char c)
	{
		boolean addChar = Character.isLetter(c) || Character.isDigit(c) || Character.isSpaceChar(c);

		if (addChar && inputText.length() < maxInputLength)
			inputText += c;
		else if (i == Keyboard.KEY_BACK && inputText.length() > 0)
			inputText = inputText.substring(0, inputText.length() - 1);
	}

	@Override
	public void tickContent()
	{
		if (Engine.getInputManager().isMouseButtonClicked(0))
			selected = isClicked();

		if (inputText.compareTo(renderableText.getText()) != 0)
		{
			renderableText.setText(inputText);
			renderableText.update();
		}

		Vector2f textSize = renderableText.getSize();
		Vector2f textPlacement = new Vector2f();

		if (alignment.h_center)
			textPlacement.setX(absolutePos.x());
		if (alignment.left)
			textPlacement.setX(absolutePos.x() - ((absoluteSize.x() - textSize.x()) / 2.0f));
		if (alignment.right)
			textPlacement.setX(absolutePos.x() + ((absoluteSize.x() - textSize.x()) / 2.0f));

		if (alignment.v_center)
			textPlacement.setY(absolutePos.y());
		if (alignment.top)
			textPlacement.setY(absolutePos.y() + ((absoluteSize.y() - textSize.y()) / 2.0f));
		if (alignment.bottom)
			textPlacement.setY(absolutePos.y() - ((absoluteSize.y() - textSize.y()) / 2.0f));

		renderableText.setPosition(textPlacement);

		blinkerPosition.setY(absolutePos.y());
		blinkerPosition.setX(textPlacement.x() + (textSize.x() / 2.0f) + blinkerSize.x());
	}

	@Override
	public void renderContent()
	{
		Trinket2D.setClippingBounds(clippingBounds);

		Trinket2D.setDrawColor(boxColor);
		Trinket2D.setRenderLevel(renderLevel);
		Trinket2D.drawRectangle(absolutePos, absoluteSize);

		if (selected && (Engine.getTime() % BLINKER_RATE < BLINKER_DUR))
		{
			Trinket2D.setDrawColor(blinkerColor);
			Trinket2D.setRenderLevel(renderLevel + 0.30f);
			Trinket2D.drawRectangle(blinkerPosition, blinkerSize);
		}

		renderableText.setRenderLevel(renderLevel + 0.35f);
		Engine.getRenderManager().processGuiText(renderableText);
		Trinket2D.setClippingBounds(Trinket2D.FULL_SCREEN_BOUNDS);
	}

	@Override
	public void syncContent()
	{
		textBoxSize = new Vector2f(absoluteSize);
		blinkerSize = new Vector2f(BLINKER_WIDTH, textBoxSize.y() - BLINKER_WIDTH);
		renderableText.setClippingBounds(clippingBounds);
	}

	@Override
	public Vector2f getContentSize()
	{
		return textBoxSize;
	}


	@Override
	public void onKeyClick(int i, char c)
	{
		if (selected)
			handleInputKey(i, c);
	}

	@Override
	public void onKeyRepress(int i, char c)
	{
		if (selected)
			handleInputKey(i, c);
	}

	@Override
	public Component blankInstance()
	{
		return new TextInputComponent(new Vector2f(), new Vector2f());
	}

	@Override
	public String componentName()
	{
		return "text-input";
	}

	@Override
	public void addToParsingFuncMap(Map<String, ParseAction> parseMap)
	{
		super.addToParsingFuncMap(parseMap);

		parseMap.put("text-in-width", (c, value) -> ((TextInputComponent) c).textBoxSize.setX(Float.parseFloat(value)));
		parseMap.put("text-in-height", (c, value) -> ((TextInputComponent) c).textBoxSize.setY(Float.parseFloat(value)));
		parseMap.put("text-in-color", (c, value) -> ((TextInputComponent) c).boxColor = new Color(value));
		parseMap.put("text-in-blink-color", (c, value) -> ((TextInputComponent) c).blinkerColor = new Color(value));

		parseMap.put("text-in-font", (c, value) -> ((TextInputComponent) c).renderableText.getAttribs().setFont(value));
		parseMap.put("text-in-font-color", (c, value) -> ((TextInputComponent) c).renderableText.getAttribs().setColor(new Color(value)));
		parseMap.put("text-in-font-size", (c, value) -> ((TextInputComponent) c).renderableText.getAttribs().setFontSize(Float.parseFloat(value)));
		parseMap.put("text-in-font-sharpness", (c, value) -> ((TextInputComponent) c).renderableText.getAttribs().setSharpness(Float.parseFloat(value)));
		parseMap.put("text-in-font-line-length", (c, value) -> ((TextInputComponent) c).renderableText.getAttribs().setMaxLineLength(Integer.parseInt(value)));
		parseMap.put("text-in-font-size", (c, value) -> ((TextInputComponent) c).renderableText.getAttribs().setFontSize(Float.parseFloat(value)));
	}

	public void setAlignment(Align alignment)
	{
		this.alignment = alignment;
	}

	public void setMaxInputLength(int maxInputLength)
	{
		this.maxInputLength = maxInputLength;
	}

	public void setBlinkerColor(Color blinkerColor)
	{
		this.blinkerColor.set(blinkerColor);
	}

	public void setBoxColor(Color boxColor)
	{
		this.boxColor.set(boxColor);
	}

	public void setFontSize(float size)
	{
		renderableText.getAttribs().setFontSize(size);
	}

	public void setFontColor(Color color)
	{
		renderableText.getAttribs().setColor(color);
	}

	public TextAttributes getFontAttribs()
	{
		return renderableText.getAttribs();
	}

	public void setFontType(String font)
	{
		renderableText.getAttribs().setFont(font);
		renderableText.update();
	}

	public String getTextValue()
	{

		return inputText;
	}
}
