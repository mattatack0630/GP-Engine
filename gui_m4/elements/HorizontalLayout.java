package gui_m4.elements;

import gui_m4.Align;
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
        Vector2f childrenLocalBounds = getChildDimensions();
        Vector2f containerSize = containerEl.getElementSize();

        float dx = childrenLocalBounds.x() / 2.0f * dir;

        for (GuiElement element : childrenEl)
        {

            Vector2f childSize = element.getBoundingSize();

            dx -= childSize.x() / 2.0f  * dir;

            float vPos = getVPos(childrenLocalBounds, childSize, containerAlignment);
            element.setRelativePosition(new Vector2f(dx, vPos));

            dx -= childSize.x() / 2.0f  * dir;
        }

        return childrenLocalBounds;
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

    public float getVPos(Vector2f childLocalBounds, Vector2f elementSize, Align aligment)
    {
        float vPos = 0;

        if (aligment.top)
            vPos = (childLocalBounds.y() - elementSize.y()) / 2.0f;
        if (aligment.v_center)
            vPos = 0;
        if (aligment.bottom)
            vPos = (elementSize.y() - childLocalBounds.y()) / 2.0f;

        return vPos;
    }
}
