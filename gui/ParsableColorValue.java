package gui;

import rendering.Color;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 5/28/17.
 */
public class ParsableColorValue<E extends Color> extends ParsableValue
{
    private E value;

    public ParsableColorValue(E init)
    {
        this.value = (E) new Color(0,0,0,0);
        this.value.set(init);
    }

    @Override
    public E getValue()
    {
        return value;
    }

    @Override
    public void parseValue(String s)
    {
        //check cast
        this.value = (E) (new Color(s));
    }
}
