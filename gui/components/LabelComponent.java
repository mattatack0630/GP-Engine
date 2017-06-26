package gui.components;

import engine.Engine;
import gui.Align;
import gui.text.GuiText;
import gui.text.TextAttributes;
import parsing.gxml.ParseAction;
import rendering.Color;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by mjmcc on 4/7/2017.
 */
public class LabelComponent extends Component
{
	private GuiText text;
	public Align alignment;

	public LabelComponent(Vector2f position, String text, TextAttributes attributes)
	{
		super(position);
		this.text = new GuiText(text, position, attributes);
		this.alignment = Align.CENTER;
	}

	public void setText(String text)
	{
		this.text.setText(text);
		build();
	}

	public void setTextAttributes(TextAttributes attributes)
	{
		this.text.setAttribs(attributes);
	}

	@Override
	public void build()
	{
		text.update();
		super.build();
	}

	@Override
	public void tickContent()
	{

	}

	@Override
	public void renderContent()
	{
		Engine.getRenderManager().processGuiText(text);
	}

	@Override
	public void syncContent()
	{
		text.setPosition(absolutePos);
		text.setClippingBounds(clippingBounds);
		placeText();
	}

	@Override
	public Vector2f getContentSize()
	{
		Vector2f ts = text.getSize();
		return ts;
	}

	private void placeText()
	{
		Vector2f textSize = this.text.getSize();
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

		this.text.setPosition(textPlacement);
	}

	public TextAttributes getTextAttributes()
	{
		return text.getAttribs();
	}

	@Override
	public void addToParsingFuncMap(Map<String, ParseAction> parseMap)
	{
		super.addToParsingFuncMap(parseMap);
		parseMap.put("label-text", (c, value) -> ((LabelComponent) c).setText(value));
		parseMap.put("label-font", (c, value) -> ((LabelComponent) c).getTextAttributes().setFont(value));
		parseMap.put("label-font-color", (c, value) -> ((LabelComponent) c).getTextAttributes().setColor(new Color(value)));
		parseMap.put("label-font-size", (c, value) -> ((LabelComponent) c).getTextAttributes().setFontSize(Float.parseFloat(value)));
		parseMap.put("label-font-sharpness", (c, value) -> ((LabelComponent) c).getTextAttributes().setSharpness(Float.parseFloat(value)));
		parseMap.put("label-font-line-length", (c, value) -> ((LabelComponent) c).getTextAttributes().setMaxLineLength(Integer.parseInt(value)));
	}

	@Override
	public Component blankInstance()
	{
		return new LabelComponent(new Vector2f(), "", new TextAttributes());
	}

	@Override
	public String componentName()
	{
		return "label";
	}

}
