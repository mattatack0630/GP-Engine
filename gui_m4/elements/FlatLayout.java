package gui_m4.elements;

import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/1/17.
 * <p>
 * A layout used to keep child relative positions
 * in tact, while still calculating a bounding space for the
 * containing element
 */
public class FlatLayout extends ContainerLayout
{
    // unimplemented TODO 6/9/17
    public static final int BACK_TO_FRONT = -1143;
    public static final int FRONT_TO_BACK = -1144;

    public int dir;

    public FlatLayout()
    {
        this(BACK_TO_FRONT);
    }

    public FlatLayout(int dir)
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
        Vector2f min = new Vector2f(+100);
        Vector2f max = new Vector2f(-100);

        for (GuiElement child : childrenEl)
        {
            Vector2f csiz = child.getBoundingSize();
            Vector2f cpos = child.getRelativePosition();
            Vector2f cmin = new Vector2f(cpos.x() - (csiz.x() / 2.0f), cpos.y() - (csiz.y() / 2.0f));
            Vector2f cmax = new Vector2f(cpos.x() + (csiz.x() / 2.0f), cpos.y() + (csiz.y() / 2.0f));

            if (cmin.x() < min.x())
                min.setX(cmin.x());
            if (cmin.y() < min.y())
                min.setY(cmin.y());
            if (cmax.x() > max.x())
                max.setX(cmax.x());
            if (cmax.y() > max.y())
                max.setY(cmax.y());
        }

        return new Vector2f((max.x() - min.x()), (max.y() - min.y()));
    }
}
