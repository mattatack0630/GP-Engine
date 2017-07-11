package gui_m4.elements;

import gui_m4.text.GuiText;
import gui_m4.text.TextAttributes;
import gui_m4.GuiRenderStack;
import gui_m4.GuiStyle;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.props.*;

import java.util.Map;

/**
 * Created by matthew on 6/27/17.
 */
public class LabelGuiElement extends GuiElement
{
    private GuiText text;

    // Inputs
    private StringGuiProperty textInput;
    private StringGuiProperty fontInput;
    private FloatGuiProperty fontSizeInput;
    private ColorGuiProperty fontColorInput;
    private FloatGuiProperty fontSharpnessInput;
    private FloatGuiProperty fontLineLengthInput;
    private Vec2GuiProperty fontShadowOffsetInput;
    private FloatGuiProperty fontLetterWidthInput;
    private FloatGuiProperty fontLineSpacingInput;
    private ColorGuiProperty fontShadowColorInput;
    private ColorGuiProperty fontOutlineColorInput;
    private FloatGuiProperty fontOutlineWidthInput;
    private FloatGuiProperty fontOutlineSharpnessInput;

    public LabelGuiElement()
    {
    }

    @Override
    public void initialize()
    {
        setupInputs();
    }

    public void setupInputs()
    {
        this.text = new GuiText("", getAbsolutePosition(), new TextAttributes());

        TextAttributes attribs = text.getAttribs();

        this.textInput = new StringGuiProperty("", "TEXT");
        this.fontInput = new StringGuiProperty(attribs.getFont(), "FONT");
        this.fontColorInput = new ColorGuiProperty(attribs.getColor(), "FONT_COLOR");
        this.fontSizeInput = new FloatGuiProperty(attribs.getFontSize(), "FONT_SIZE");
        this.fontLetterWidthInput = new FloatGuiProperty(attribs.getLetterWidth(), "LETTER_WIDTH");
        this.fontLineSpacingInput = new FloatGuiProperty(attribs.getLineSpacing(), "LINE_SPACING");
        this.fontOutlineColorInput = new ColorGuiProperty(attribs.getOutLineColor(), "OUTLINE_COLOR");
        this.fontOutlineSharpnessInput = new FloatGuiProperty(attribs.getOutlineSharpness(), "OUTLINE_SHARPNESS");
        this.fontOutlineWidthInput = new FloatGuiProperty(attribs.getOutLineWidth(), "OUTLINE_WIDTH");
        this.fontShadowColorInput = new ColorGuiProperty(attribs.getShadowColor(), "SHADOW_COLOR");
        this.fontShadowOffsetInput = new Vec2GuiProperty(attribs.getShadowOff(), "SHADOW_OFFSET");
        this.fontLineLengthInput = new FloatGuiProperty(attribs.getMaxLineLength(), "LINE_LENGTH");
        this.fontSharpnessInput = new FloatGuiProperty(attribs.getSharpness(), "FONT_SHARPNESS");

        this.textInput.addChangeListener((property, oldValue, newValue) -> text.setText(newValue));
        this.fontInput.addChangeListener((property, oldValue, newValue) -> attribs.setFont(newValue));
        this.fontColorInput.addChangeListener((property, oldValue, newValue) -> attribs.setColor(newValue));
        this.fontSizeInput.addChangeListener((property, oldValue, newValue) -> attribs.setFontSize(newValue));
        this.fontLetterWidthInput.addChangeListener((property, oldValue, newValue) -> attribs.setFontSize(newValue));
        this.fontLineSpacingInput.addChangeListener((property, oldValue, newValue) -> attribs.setLineSpacing(newValue));
        this.fontOutlineColorInput.addChangeListener((property, oldValue, newValue) -> attribs.setOutLineColor(newValue));
        this.fontOutlineSharpnessInput.addChangeListener((property, oldValue, newValue) -> attribs.setOutLineSharpness(newValue));
        this.fontOutlineWidthInput.addChangeListener((property, oldValue, newValue) -> attribs.setOutLineWidth(newValue));
        this.fontShadowColorInput.addChangeListener((property, oldValue, newValue) -> attribs.setShadowColor(newValue));
        this.fontShadowOffsetInput.addChangeListener((property, oldValue, newValue) -> attribs.setShadowOffset(newValue));
        this.fontLineLengthInput.addChangeListener((property, oldValue, newValue) -> attribs.setMaxLineLength(newValue));
        this.fontSharpnessInput.addChangeListener((property, oldValue, newValue) -> attribs.setSharpness(newValue));
    }

    @Override
    protected void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
        propertyMap.put(fontInput.getPropertyName(), fontInput);
        propertyMap.put(textInput.getPropertyName(), textInput);
        propertyMap.put(fontColorInput.getPropertyName(), fontColorInput);
        propertyMap.put(fontSizeInput.getPropertyName(), fontSizeInput);
        propertyMap.put(fontLetterWidthInput.getPropertyName(), fontLetterWidthInput);
        propertyMap.put(fontLineSpacingInput.getPropertyName(), fontLineSpacingInput);
        propertyMap.put(fontOutlineColorInput.getPropertyName(), fontOutlineColorInput);
        propertyMap.put(fontOutlineSharpnessInput.getPropertyName(), fontOutlineSharpnessInput);
        propertyMap.put(fontOutlineWidthInput.getPropertyName(), fontOutlineWidthInput);
        propertyMap.put(fontShadowColorInput.getPropertyName(), fontShadowColorInput);
        propertyMap.put(fontShadowOffsetInput.getPropertyName(), fontShadowOffsetInput);
        propertyMap.put(fontLineLengthInput.getPropertyName(), fontLineLengthInput);
        propertyMap.put(fontSharpnessInput.getPropertyName(), fontSharpnessInput);
    }

    @Override
    protected GuiStyle getDefaultStyle()
    {

        return new GuiStyle();
    }

    @Override
    protected void onTickImp(ConsumableEventsHandler eventsHandler)
    {

    }

    @Override
    protected void onRenderImp(GuiRenderStack renderer)
    {

        renderer.setNextRenderable(text);
    }

    @Override
    protected void onBuildImp()
    {
        text.update();
        setContentSize(text.getSize());
    }

    @Override
    protected void onPostBuildImp()
    {
        text.setRenderLevel(getLayer());
        text.setPosition(getAbsolutePosition());
        text.setClippingBounds(getClippingBounds());
    }

    public void setText(String text)
    {
        this.text.setText(text);
        this.text.update();
    }

    public String getText()
    {
        return text.getText();
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return LabelGuiElement.BUILDER;
    }

    public static final GuiElementBuilder<LabelGuiElement> BUILDER = new GuiElementBuilder<LabelGuiElement>()
    {
        @Override
        public LabelGuiElement genInstance()
        {
            return new LabelGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "label";
        }

        @Override
        public boolean isParentable()
        {
            return false;
        }
    };
}
