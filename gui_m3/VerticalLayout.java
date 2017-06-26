package gui_m3;

import gui.Align;
import gui.components.Component;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 5/31/17.
 */
public class VerticalLayout extends ContainerLayout
{

    public static final int BOTTOM_TO_TOP = -1;
    public static final int TOP_TO_BOTTOM = +1;

    private int dir;

    public VerticalLayout()
    {
        this(TOP_TO_BOTTOM);
    }

    public VerticalLayout(int dir)
    {
        this.dir = dir;
    }

    @Override
    public void reset()
    {

    }

    @Override
    public Vector2f place()
    {
        Vector2f childrenSize = getChildDimensions();
        Vector2f containerSize = containerEl.getElementSize();

        float dy = childrenSize.y() / 2.0f * dir;

        for (GuiElement element : childrenEl)
        {

            Vector2f childSize = element.getBoundingSize();

            dy -= childSize.y() / 2.0f * dir;

            element.setRelativePosition(new Vector2f(0, dy));

            dy -= childSize.y() / 2.0f * dir;
        }


        return childrenSize;
    }

    public Vector2f getChildDimensions()
    {
        float dx = 0;
        float dy = 0;

        for (GuiElement element : childrenEl)
        {
            Vector2f childSize = element.getBoundingSize();

            dy += childSize.y();

            if (childSize.x() > dx)
                dx = childSize.x();
        }

        return new Vector2f(dx, dy);
    }
}
