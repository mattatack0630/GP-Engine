package gui_m4.elements;

import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/1/17.
 */
public class SingleLayout extends ContainerLayout
{
    @Override
    public void reset()
    {

    }

    @Override
    public Vector2f place()
    {
        Vector2f size = new Vector2f();

        // Only doing this for 0 element case, this should
        // really only be used for 1 element

        for(GuiElement child : childrenEl)
        {
            Vector2f.add(child.getBoundingSize(), size, size);
        }

        return size;
    }
}
