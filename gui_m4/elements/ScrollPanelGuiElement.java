package gui_m4.elements;

import gui_m4.GuiRenderStack;
import gui_m4.GuiSize;
import gui_m4.GuiStyle;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.props.GuiProperty;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by matthew on 6/29/17.
 */
public class ScrollPanelGuiElement extends GuiElement
{
    private StackPanelGuiElement scrollContainer;

    private HSliderGuiElement horizontalScroller;
    private VSliderGuiElement verticalScroller;
    private StackPanelGuiElement contentPanel;

    public ScrollPanelGuiElement()
    {
        onScrollValueChange();

    }

    @Override
    public void initialize()
    {
        verticalScroller = new VSliderGuiElement();
        horizontalScroller = new HSliderGuiElement();
        scrollContainer = new StackPanelGuiElement();
        contentPanel = new StackPanelGuiElement();

        scrollContainer.setContentSize(new GuiSize("100%", "100%"));
        horizontalScroller.setContentSize(new GuiSize("100%", "5%"));
        verticalScroller.setContentSize(new GuiSize("5%", "100%"));

        scrollContainer.setWrapChildren(false);
        contentPanel.setWrapChildren(true);

        scrollContainer.addChild(contentPanel);
        scrollContainer.addChild(verticalScroller);
        scrollContainer.addChild(horizontalScroller);

        verticalScroller.getValueProperty().addChangeListener(((property, oldValue, newValue) -> onScrollValueChange()));
        horizontalScroller.getValueProperty().addChangeListener(((property, oldValue, newValue) -> onScrollValueChange()));

        // Called on super to add directly to this elements children
        super.addChild(scrollContainer);
    }

    public void onScrollValueChange()
    {
        Vector2f contentsRelativeOffset = new Vector2f();

        Vector2f containerSize = getElementSize();
        Vector2f contentsSize = contentPanel.getElementSize();
        float scrollValY = verticalScroller.getValueProperty().getPropertyValue().y();
        float scrollValX = horizontalScroller.getValueProperty().getPropertyValue().x();

        Vector2f min = Vector2f.sub(containerSize, contentsSize, null).scale(0.5f);
        Vector2f max = Vector2f.sub(contentsSize, containerSize, null).scale(0.5f);
        float dx = Maths.map(scrollValX, 1.0f, 0.0f, min.x(), max.x());
        float dy = Maths.map(scrollValY, 1.0f, 0.0f, min.y(), max.y());
        contentsRelativeOffset.setX(contentsSize.x() > containerSize.x() ? dx : 0.0f);
        contentsRelativeOffset.setY(contentsSize.y() > containerSize.y() ? dy : 0.0f);

        contentPanel.setRelativePosition(contentsRelativeOffset);
        contentPanel.calculatePositions();
        contentPanel.postBuild();
    }

    @Override
    protected void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
    }

    @Override
    protected GuiStyle getDefaultStyle()
    {

        return new GuiStyle();
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return ScrollPanelGuiElement.BUILDER;
    }

    @Override
    protected void onTickImp(ConsumableEventsHandler eventsHandler)
    {

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
        Vector2f elSize = scrollContainer.getElementSize();
        horizontalScroller.setRelativePosition(new Vector2f(0f, (horizontalScroller.getBoundingSize().y() - elSize.y()) / 2.0f));
        verticalScroller.setRelativePosition(new Vector2f((elSize.x() - verticalScroller.getBoundingSize().x()) / 2.0f, 0f));
        horizontalScroller.calculatePositions();
        verticalScroller.calculatePositions();

        Vector2f cSize = contentPanel.getElementSize();
        float vscale = (elSize.y() / cSize.y() * 100);
        float hscale = (elSize.x() / cSize.x() * 100);

        horizontalScroller.getButton().setContentSize(new GuiSize(hscale + "%", "100%"));
        verticalScroller.getButton().setContentSize(new GuiSize("100%", vscale + "%"));
    }

    @Override
    public void addChild(GuiElement element)
    {
        this.contentPanel.addChild(element);
    }

    public static final GuiElementBuilder<ScrollPanelGuiElement> BUILDER = new GuiElementBuilder<ScrollPanelGuiElement>()
    {
        @Override
        public ScrollPanelGuiElement genInstance()
        {
            return new ScrollPanelGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "scroll-panel";
        }

        @Override
        public boolean isParentable()
        {
            return true;
        }
    };
}
