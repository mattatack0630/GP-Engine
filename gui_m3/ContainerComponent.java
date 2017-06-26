package gui_m3;

import gui.Align;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by matthew on 5/31/17.
 */
public class ContainerComponent extends ElementComponent
{
    private static final int DEFAULT_CHILD_CAP = 100;

    private List<GuiElement> children;
    private ContainerLayout layout;
    private Align alignTowards;
    private int maxChildren;
    private boolean wrapChildren;

    public ContainerComponent()
    {
        this.children = new ArrayList<>();
        this.layout = new VerticalLayout();
        this.alignTowards = Align.CENTER;
        this.maxChildren = DEFAULT_CHILD_CAP;
        this.wrapChildren = false;
    }

    @Override
    public void onTick()
    {
        for (GuiElement element : children)
            element.tick();

    }

    @Override
    public void onRender(MasterRenderer renderer)
    {
        for (GuiElement element : children)
            element.render(renderer);
    }

    @Override
    public void onEvent(Event event)
    {
    }

    @Override
    public void onBuild()
    {
        if(wrapChildren == true) wrappingBuild();
        if(wrapChildren == false) nonWrappingBuild();
    }

    public void wrappingBuild()
    {
        layout.reset();
        layout.setContainerEl(parentElement);

        // Sort relative and non-relative users
        List<GuiElement> absoluteSizeUsers = new LinkedList<>();
        List<GuiElement> relativeSizeUsers = new LinkedList<>();

        for (GuiElement element : children)
        {
            if(element.usesRelativeSizes())
                relativeSizeUsers.add(element);
            else
                absoluteSizeUsers.add(element);
        }

        // Build non-relative sized elements
        for (GuiElement element : absoluteSizeUsers)
            element.build();

        // Place the relative sized elements
        layout.setChildrenEl(absoluteSizeUsers);
        Vector2f preSize = layout.place();

        // Use that place info to calculate container size
        parentElement.setContentSize(preSize);
        parentElement.calculateSizes();

        // Build relative size components (now that container size is calculated)
        for (GuiElement element : relativeSizeUsers)
            element.build();

        // Place all child elements
        layout.setChildrenEl(children);
        Vector2f postSize = layout.place();

        //recalculate container size
        parentElement.setContentSize(postSize);
        parentElement.calculateSizes();

        // Align
        Vector2f offset = getAlignmentOffset(parentElement.getElementSize(), postSize);
        for (GuiElement element : children)
        {
            Vector2f childRelPos = element.getRelativePosition();
            element.setRelativePosition(new Vector2f(childRelPos.x() + offset.x(), childRelPos.y() + offset.y()));
        }
    }

    public void nonWrappingBuild()
    {
        // Calculate actual size
        parentElement.calculateSizes();

        // Build each child
        for(GuiElement element : children)
            element.build();

        // Place using layout
        layout.reset();
        layout.setContainerEl(parentElement);
        layout.setChildrenEl(children);
        Vector2f childrenBounds = layout.place();

        // Align
        Vector2f offset = getAlignmentOffset(parentElement.getElementSize(), childrenBounds);
        for (GuiElement element : children)
        {
            Vector2f childRelPos = element.getRelativePosition();
            element.setRelativePosition(new Vector2f(childRelPos.x() + offset.x(), childRelPos.y() + offset.y()));
        }
    }

    @Override
    public void postBuild()
    {
        for (GuiElement element : children)
        {
            // Recalculate absolute position for child
            // This may be replaced with a "soft-build" method
            element.calculatePositions();
            element.calculateClippingBounds();
            // Do post building stuff for child
            element.postBuild();
        }
    }

    @Override
    public void setParent(GuiElement element)
    {
        super.setParent(element);

        for(GuiElement child : children)
        {
            // update children of new parent
            child.setParentElement(parentElement);
        }
    }

    private Vector2f getAlignmentOffset(Vector2f containerSize, Vector2f childrenSize)
    {
        Vector2f offset = new Vector2f();

        if (alignTowards.top) offset.setY((containerSize.y() / 2.0f) - (childrenSize.y() / 2.0f));
        if (alignTowards.bottom) offset.setY((childrenSize.y() / 2.0f) - (containerSize.y() / 2.0f));
        if (alignTowards.v_center) offset.setY(0);

        if (alignTowards.left) offset.setX((childrenSize.x() / 2.0f) - (containerSize.x() / 2.0f));
        if (alignTowards.right) offset.setX((containerSize.x() / 2.0f) - (childrenSize.x() / 2.0f));
        if (alignTowards.h_center) offset.setX(0);

        return offset;
    }

    public void offsetChildren(Vector2f contentsRelativeOffset)
    {
        for(GuiElement element : children)
        {
            element.setRelativePosition(contentsRelativeOffset);
            element.build();
        }
    }

    public void addChild(GuiElement child)
    {
        if (children.size() < maxChildren)
        {
            this.children.add(child);
            child.setParentElement(parentElement);
        }
    }

    public void clearChildren()
    {
        children.clear();
    }

    public List<GuiElement> getChildren()
    {
        return children;
    }

    public void setLayout(ContainerLayout layout)
    {
        this.layout = layout;
    }

    public void setAlignTowards(Align alignTowards)
    {
        this.alignTowards = alignTowards;
    }

    public void setMaxChildren(int maxChildren)
    {
        this.maxChildren = maxChildren;
    }

    public void setWrapChildren(boolean wrapChildren)
    {
        this.wrapChildren = wrapChildren;
    }

}
