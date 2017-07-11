package gui_m4.elements;

import engine.Engine;
import gui_m4.GuiRenderStack;
import gui_m4.GuiStyle;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.props.BooleanGuiProperty;
import gui_m4.props.ColorGuiProperty;
import gui_m4.props.GuiProperty;
import gui_m4.props.Vec2GuiProperty;
import rendering.Color;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by matthew on 6/27/17.
 */
public class AreaSliderGuiElement extends GuiElement
{
    protected static final float DEFUALT_BUTTON_SCALE = 0.1f;

    protected Vec2GuiProperty value;
    protected boolean pressed;

    protected BooleanGuiProperty lockX;
    protected BooleanGuiProperty lockY;

    protected Vector2f grapplePos;

    protected StackPanelGuiElement sliderContainer;
    protected ButtonGuiElement sliderButton;
    protected ButtonGuiElement sliderRail;

    public AreaSliderGuiElement()
    {
        /*this.sliderContainer = new StackPanelGuiElement();
        this.sliderButton = new ButtonGuiElement();
        this.sliderRail = new ButtonGuiElement();

        this.sliderContainer.addChild(this.sliderRail);
        this.sliderContainer.addChild(this.sliderButton);

        this.addChild(this.sliderContainer);

        this.pressed = false;
        this.grapplePos = new Vector2f();

        this.value = new Vec2GuiProperty(new Vector2f(0, 0), "VALUE");
        this.lockX = new BooleanGuiProperty(false, "LOCK_X");
        this.lockY = new BooleanGuiProperty(false, "LOCK_Y");

        this.sliderButton.getMouseEventProperty().addChangeListener((property, oldValue, newValue) ->
        {
            if (newValue == ButtonGuiElement.CLICKED) onClickButton();
        });

        this.sliderRail.getMouseEventProperty().addChangeListener((property, oldValue, newValue) ->
        {
            if (newValue == ButtonGuiElement.CLICKED) onClickRail();
        });*/

        // Tests
        sliderRail.getButtonTexture().setColor(new Color(1,0,1,1));
        //sliderButton.getButtonTexture().setColor(Color.BLACK);
    }

    @Override
    public void initialize()
    {
        this.sliderContainer = new StackPanelGuiElement();
        this.sliderButton = new ButtonGuiElement();
        this.sliderRail = new ButtonGuiElement();
        this.sliderButton.addClass("slider-button");

        this.sliderContainer.addChild(this.sliderRail);
        this.sliderContainer.addChild(this.sliderButton);

        this.addChild(this.sliderContainer);

        this.pressed = false;
        this.grapplePos = new Vector2f();

        this.value = new Vec2GuiProperty(new Vector2f(0, 0), "VALUE");
        this.lockX = new BooleanGuiProperty(false, "LOCK_X");
        this.lockY = new BooleanGuiProperty(false, "LOCK_Y");

        this.sliderButton.getMouseEventProperty().addChangeListener((property, oldValue, newValue) ->
        {
            if (newValue == ButtonGuiElement.CLICKED) onClickButton();
        });

        this.sliderRail.getMouseEventProperty().addChangeListener((property, oldValue, newValue) ->
        {
            if (newValue == ButtonGuiElement.CLICKED) onClickRail();
        });
    }

    @Override
    protected void onTickImp(ConsumableEventsHandler eventsHandler)
    {
        if (!Engine.getInputManager().isMouseButtonDown(0))
            pressed = false;

        if (pressed) onHold();
    }

    @Override
    protected void onRenderImp(GuiRenderStack renderer)
    {
    }

    @Override
    protected void onBuildImp()
    {
        calculateSizes();
        sliderButton.setContentSize(new Vector2f(getElementSize()).scale(DEFUALT_BUTTON_SCALE));
        sliderRail.setContentSize(new Vector2f(getElementSize()));
    }

    @Override
    protected void onPostBuildImp()
    {

    }

    public void onClickButton()
    {
        Vector2f mouse = new Vector2f(Engine.getInputManager().getGLCursorCoords());
        Vector2f.sub(mouse, sliderButton.getAbsolutePosition(), grapplePos);
        pressed = true;
    }

    public void onClickRail()
    {
        grapplePos.setX(0);
        grapplePos.setY(0);
        pressed = true;
    }

    public void onHold()
    {
        Vector2f mouse = new Vector2f(Engine.getInputManager().getGLCursorCoords());

        Vector2f size = getElementSize();
        Vector2f pos = getAbsolutePosition();
        Vector2f buttonSize = sliderButton.getBoundingSize();
        Vector2f sliderSize = Vector2f.sub(size, buttonSize, null).scale(0.5f);

        Vector2f min = new Vector2f(pos.x() - sliderSize.x(), pos.y() - sliderSize.y());
        Vector2f max = new Vector2f(pos.x() + sliderSize.x(), pos.y() + sliderSize.y());
        float dx = Maths.map(mouse.x() - grapplePos.x(), min.x(), max.x(), 0.0f, 1.0f);
        float dy = Maths.map(mouse.y() - grapplePos.y(), min.y(), max.y(), 0.0f, 1.0f);
        dx = Maths.clamp(dx, 0.0f, 1.0f);
        dy = Maths.clamp(dy, 0.0f, 1.0f);

        Vector2f newButtonRel = new Vector2f(sliderButton.getRelativePosition());
        if (!lockX.getPropertyValue()) newButtonRel.setX(Maths.map(dx, 0, 1, -sliderSize.x(), sliderSize.x()));
        if (!lockY.getPropertyValue()) newButtonRel.setY(Maths.map(dy, 0, 1, -sliderSize.y(), sliderSize.y()));

        sliderButton.setRelativePosition(newButtonRel);
        sliderButton.build();
        sliderButton.postBuild();

        // Set to mapped values, update value
        Vector2f valVec = new Vector2f();
        valVec.setX(dx);
        valVec.setY(dy);
        value.setPropertyValue(valVec);
    }


    @Override
    protected void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
        propertyMap.put(lockX.getPropertyName(), lockX);
        propertyMap.put(lockY.getPropertyName(), lockY);
    }

    @Override
    protected GuiStyle getDefaultStyle()
    {
        GuiStyle style = new GuiStyle();
        style.addProperty("button texture", new ColorGuiProperty(new Color(1,1,0,1), "COLOR"));
        return style;
    }

    @Override
    public GuiElementBuilder getBuilder()
    {

        return AreaSliderGuiElement.BUILDER;
    }

    public Vec2GuiProperty getValueProperty()
    {

        return value;
    }

    public static final GuiElementBuilder<AreaSliderGuiElement> BUILDER = new GuiElementBuilder<AreaSliderGuiElement>()
    {
        @Override
        public AreaSliderGuiElement genInstance()
        {
            return new AreaSliderGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "slider";
        }

        @Override
        public boolean isParentable()
        {
            return false;
        }
    };

    public ButtonGuiElement getButton()
    {
        return sliderButton;
    }

    public GuiElement getRail()
    {
        return sliderRail;
    }
}
