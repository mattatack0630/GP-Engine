package gui_m4.elements;

import gui_m4.ClickChecker;
import gui_m4.events.Clickable;
import gui_m4.GuiRenderStack;
import gui_m4.GuiSize;
import gui_m4.GuiStyle;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.events.ConsumableMouseEvent;
import gui_m4.props.ColorGuiProperty;
import gui_m4.props.GuiProperty;
import gui_m4.props.IntegerGuiProperty;
import rendering.Color;

import java.util.Map;

/**
 * Created by matthew on 6/27/17.
 */
public class ButtonGuiElement extends GuiElement implements Clickable
{
    public static final int STATIC = 2234;
    public static final int HELD = 2235;
    public static final int CLICKED = 2236;
    public static final int RELEASED = 2237;
    public static final int HOVERED = 2238;
    public static final int ENTERED = 2239;
    public static final int EXITED = 2240;

    private static final String CONTENT_HOLDER_CLASS = "CONTENT_HOLDER";
    private static final String BUTTON_STATIC_TEXTURE_CLASS = "BUTTON_TEXTURE";
    private static final String BUTTON_PRESS_TEXTURE_CLASS = "PRESS_TEXTURE";
    private static final String BUTTON_HOVER_TEXTURE_CLASS = "HOVER_TEXTURE";

    private ClickChecker clickChecker;

    private IntegerGuiProperty mouseEventOn;

    private StackPanelGuiElement contentHolder;

    private GuiTextureElement buttonTexture;
    private GuiTextureElement hoverTexture;
    private GuiTextureElement pressTexture;

    // Element super

    @Override
    public void initialize()
    {
        this.clickChecker = new ClickChecker(getAbsolutePosition(), getElementSize(), this);

        this.mouseEventOn = new IntegerGuiProperty(STATIC, "EVENT_ON_OBSERVE_ONLY");

        this.buttonTexture = new GuiTextureElement();
        this.buttonTexture.addClass(BUTTON_STATIC_TEXTURE_CLASS);
        this.buttonTexture.setContentSize(new GuiSize("100%", "100%"));
        this.buttonTexture.setOpacity(1.0f);

        this.pressTexture = new GuiTextureElement();
        this.pressTexture.addClass(BUTTON_PRESS_TEXTURE_CLASS);
        this.pressTexture.setContentSize(new GuiSize("100%", "100%"));
        this.pressTexture.setOpacity(0.0f);

        this.hoverTexture = new GuiTextureElement();
        this.hoverTexture.addClass(BUTTON_HOVER_TEXTURE_CLASS);
        this.hoverTexture.setContentSize(new GuiSize("100%", "100%"));
        this.hoverTexture.setOpacity(0.0f);

        this.contentHolder = new StackPanelGuiElement();
        this.contentHolder.addClass(CONTENT_HOLDER_CLASS);
        this.contentHolder.addChild(buttonTexture);
        this.contentHolder.addChild(hoverTexture);
        this.contentHolder.addChild(pressTexture);
        this.contentHolder.setContentSize(new GuiSize("100%", "100%"));
        this.contentHolder.setWrapChildren(false);

        super.addChild(contentHolder);

        // Set up button texture events (Maybe Change Later!!) TODO 7/10/17
        mouseEventOn.addChangeListener((property, oldValue, newValue) ->
        {

            switch (newValue)
            {
                case STATIC:
                    pressTexture.setOpacity(0.0f);
                    hoverTexture.setOpacity(0.0f);
                    buttonTexture.setOpacity(1.0f);
                    break;
                case HELD:
                    buttonTexture.setOpacity(0.0f);
                    hoverTexture.setOpacity(0.0f);
                    pressTexture.setOpacity(1.0f);
                    break;
                case HOVERED:
                    if (oldValue != HELD)
                    {
                        buttonTexture.setOpacity(0.0f);
                        pressTexture.setOpacity(0.0f);
                        hoverTexture.setOpacity(1.0f);
                    }
                    break;
            }
        });
    }
    @Override
    public void addChild(GuiElement element)
    {
        this.contentHolder.addChild(element);
    }

    @Override
    protected void onTickImp(ConsumableEventsHandler eventsHandler)
    {
        mouseEventOn.setPropertyValue(STATIC);

        ConsumableMouseEvent mouseEvent = eventsHandler.getEvent(ConsumableMouseEvent.class);

        if (mouseEvent != null)
        {
            clickChecker.checkClick();

            if (mouseEventOn.getPropertyValue() != STATIC)
                eventsHandler.consume(mouseEvent);
        }
    }

    @Override
    protected void onRenderImp(GuiRenderStack renderer)
    {
    }

    @Override
    protected void onBuildImp()
    {
    }

    @Override
    protected void onPostBuildImp()
    {

        clickChecker.setClickDimensions(getAbsolutePosition(), getElementSize());
    }

    @Override
    public void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {

    }

    @Override
    protected GuiStyle getDefaultStyle()
    {
        GuiStyle style = new GuiStyle();
        style.addProperty(buttonTexture, new ColorGuiProperty(new Color(0.3f, 0.3f, 0.3f, 1f), "COLOR"));
        style.addProperty(hoverTexture, new ColorGuiProperty(new Color(0.2f, 0.2f, 0.2f, 1f), "COLOR"));
        style.addProperty(pressTexture, new ColorGuiProperty(new Color(0.1f, 0.1f, 0.1f, 1f), "COLOR"));
        return style;
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return ButtonGuiElement.BUILDER;
    }

    //Clickable interface

    @Override
    public void onClick()
    {
        mouseEventOn.setPropertyValue(CLICKED);
    }

    @Override
    public void onRelease()
    {
        mouseEventOn.setPropertyValue(RELEASED);
    }

    @Override
    public void onHold()
    {
        mouseEventOn.setPropertyValue(HELD);
    }

    @Override
    public void onHover()
    {
        mouseEventOn.setPropertyValue(HOVERED);
    }

    @Override
    public void onBeginHover()
    {
        mouseEventOn.setPropertyValue(ENTERED);
    }

    @Override
    public void onEndHover()
    {
        mouseEventOn.setPropertyValue(EXITED);
    }

    public static final GuiElementBuilder<ButtonGuiElement> BUILDER = new GuiElementBuilder<ButtonGuiElement>()
    {
        @Override
        public ButtonGuiElement genInstance()
        {
            return new ButtonGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "button";
        }

        @Override
        public boolean isParentable()
        {
            return false;
        }
    };

    public GuiTextureElement getButtonTexture()
    {

        return buttonTexture;
    }

    public IntegerGuiProperty getMouseEventProperty()
    {

        return mouseEventOn;
    }

}
