package gui;

import gui.ParsableValue;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by matthew on 5/28/17.
 */
public class ParsableVector2fValue <E extends Vector2f> extends ParsableValue
{
    private E value;

    public ParsableVector2fValue(E init)
    {
        this.value = (E) new Vector2f();
        this.value.set(init.x(), init.y());
    }

    @Override
    public E getValue()
    {
        return value;
    }

    @Override
    public void parseValue(String s)
    {
        // vec2(0, 0)
        /*
        int si = s.indexOf('(');
        int ei = s.indexOf(')');
        String data1 = s.substring(si, ei);
        String[] data2 = data1.split(",");

        float x = 0;
        float y = 0;
        this.value = (E) (new Vector2f(x, y));*/

        //check cast
        this.value = (E)((Vector2f) ExtraUtils.parseVector(s));
    }
}
