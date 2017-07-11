package gui_m4.elements;

import gui_m4.Align;
import gui_m4.GuiRenderStack;
import gui_m4.GuiStyle;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.props.AlignmentGuiProperty;
import gui_m4.props.BooleanGuiProperty;
import gui_m4.props.GuiProperty;
import utils.math.linear.vector.Vector2f;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 6/26/17.
 * <p>
 * Overrides the main build method to reduce overhead
 */
public abstract class ContainerGuiElement extends GuiElement
{
    protected AlignmentGuiProperty alignmentProperty;
    protected BooleanGuiProperty wrapChildrenProperty;

    protected ContainerLayout layout;
    protected Vector2f childrenLocalBounds;

    @Override
    public void initialize()
    {
        this.alignmentProperty = new AlignmentGuiProperty(Align.CENTER, "ALIGN");
        this.wrapChildrenProperty = new BooleanGuiProperty(true, "WRAP_CHILDREN");
        this.childrenLocalBounds = new Vector2f();
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
    public void build()
    {
        onBuildImp();
    }

    @Override
    protected void onBuildImp()
    {
        layout.reset();
        layout.setContainerEl(this);
        layout.setContainerAlignment(alignmentProperty.getPropertyValue());

        if (wrapChildrenProperty.getPropertyValue() == true) wrappingBuild();
        if (wrapChildrenProperty.getPropertyValue() == false) nonWrappingBuild();

        // Align
        Vector2f offset = getAlignmentOffset(getElementSize(), childrenLocalBounds);
        for (GuiElement element : getChildren())
        {
            Vector2f childRelPos = element.getRelativePosition();
            element.setRelativePosition(new Vector2f(childRelPos.x() + offset.x(), childRelPos.y() + offset.y()));
        }
    }

    public void wrappingBuild()
    {
        // Sort relative and non-relative users
        List<GuiElement> absoluteSizeUsers = new LinkedList<>();
        List<GuiElement> relativeSizeUsers = new LinkedList<>();
        sortChildrenUserType(absoluteSizeUsers, relativeSizeUsers);

        // Build non-relative sized elements
        for (GuiElement element : absoluteSizeUsers)
            element.build();

        // Place the non-relative sized elements
        layout.setChildrenEl(absoluteSizeUsers);
        childrenLocalBounds = layout.place();

        // Use that place info to calculate container size
        setContentSize(childrenLocalBounds);
        calculateSizes();

        // Build relative size components (now that container size is calculated)
        for (GuiElement element : relativeSizeUsers)
            element.build();

        // Place all child elements
        layout.setChildrenEl(getChildren());
        childrenLocalBounds = layout.place();

        // Calculate final size
        setContentSize(childrenLocalBounds);
        calculateSizes();
    }

    public void nonWrappingBuild()
    {
        // Calculate actual size
        calculateSizes();

        // Build each child
        for (GuiElement element : getChildren())
            element.build();

        // Place using layout
        layout.setChildrenEl(getChildren());
        childrenLocalBounds = layout.place();
    }

    @Override
    protected void onPostBuildImp()
    {

    }

    private void sortChildrenUserType(List<GuiElement> absUsers, List<GuiElement> relUsers)
    {
        for (GuiElement element : getChildren())
        {
            if (element.usesRelativeSizes())
                relUsers.add(element);
            else
                absUsers.add(element);
        }
    }

    private Vector2f getAlignmentOffset(Vector2f containerSize, Vector2f childrenSize)
    {
        Vector2f offset = new Vector2f();
        Align alignTowards = alignmentProperty.getPropertyValue();

        if (alignTowards.top) offset.setY((containerSize.y() / 2.0f) - (childrenSize.y() / 2.0f));
        if (alignTowards.bottom) offset.setY((childrenSize.y() / 2.0f) - (containerSize.y() / 2.0f));
        if (alignTowards.v_center) offset.setY(0);

        if (alignTowards.left) offset.setX((childrenSize.x() / 2.0f) - (containerSize.x() / 2.0f));
        if (alignTowards.right) offset.setX((containerSize.x() / 2.0f) - (childrenSize.x() / 2.0f));
        if (alignTowards.h_center) offset.setX(0);

        return offset;
    }

    @Override
    public void addChild(GuiElement child)
    {
        super.addChild(child);
    }

    @Override
    protected void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
        propertyMap.put(alignmentProperty.getPropertyName(), alignmentProperty);
        propertyMap.put(wrapChildrenProperty.getPropertyName(), wrapChildrenProperty);
    }

    @Override
    protected GuiStyle getDefaultStyle()
    {

        return new GuiStyle();
    }

    public void setWrapChildren(boolean wrapChildren)
    {
        this.wrapChildrenProperty.setPropertyValue(wrapChildren);
    }
}
