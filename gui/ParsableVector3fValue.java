package gui;

import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by matthew on 5/28/17.
 */
public class ParsableVector3fValue<E extends Vector3f> extends ParsableValue
{
    private E value;

    public ParsableVector3fValue(E init)
    {
        this.value = (E) new Vector3f();
        this.value.set(init.x(), init.y(), init.z());
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
        this.value = (E)((Vector3f) ExtraUtils.parseVector(s));
    }
}
