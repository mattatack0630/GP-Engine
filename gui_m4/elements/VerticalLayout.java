package gui_m4.elements;

import gui_m4.Align;
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
        Vector2f childrenLocalBounds = getChildDimensions();

        float dy = childrenLocalBounds.y() / 2.0f * dir;

        for (GuiElement element : childrenEl)
        {

            Vector2f childSize = element.getBoundingSize();

            dy -= childSize.y() / 2.0f * dir;

            float hPos = getHPos(childrenLocalBounds, childSize, containerAlignment);
            element.setRelativePosition(new Vector2f(hPos, dy));

            dy -= childSize.y() / 2.0f * dir;
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

            dy += childSize.y();

            if (childSize.x() > dx)
                dx = childSize.x();
        }

        return new Vector2f(dx, dy);
    }

    public float getHPos(Vector2f childLocalBounds, Vector2f elementSize, Align aligment)
    {
        float hPos = 0;

        if (aligment.left)
            hPos = (elementSize.x() - childLocalBounds.x()) / 2.0f;
        if (aligment.h_center)
            hPos = 0;
        if (aligment.right)
            hPos = (childLocalBounds.x() - elementSize.x()) / 2.0f;

        return hPos;
    }
}
