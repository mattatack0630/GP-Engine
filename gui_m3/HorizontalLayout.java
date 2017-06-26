package gui_m3;

import utils.math.linear.vector.Vector;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 5/31/17.
 */
public class HorizontalLayout extends ContainerLayout
{
    public static final int LEFT_TO_RIGHT = -1;
    public static final int RIGHT_TO_LEFT = +1;

    private int dir;

    public HorizontalLayout()
    {
        this(LEFT_TO_RIGHT);
    }

    public HorizontalLayout(int dir)
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

        float dx = childrenSize.x() / 2.0f * dir;

        for (GuiElement element : childrenEl)
        {

            Vector2f childSize = element.getBoundingSize();

            dx -= childSize.x() / 2.0f  * dir;

            element.setRelativePosition(new Vector2f(dx, 0));

            dx -= childSize.x() / 2.0f  * dir;
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

            dx += childSize.x();

            if (childSize.y() > dy)
                dy = childSize.y();
        }

        return new Vector2f(dx, dy);
    }
}
