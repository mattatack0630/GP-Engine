package gui;

import utils.ExtraUtils;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by matthew on 5/28/17.
 */
public class ParsableVector4fValue<E extends Vector4f> extends ParsableValue
{
    private E value;

    public ParsableVector4fValue(E init)
    {
        this.value = (E) new Vector4f();
        this.value.set(init.x(), init.y(), init.z(), init.w());
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
        this.value = (E) ((Vector4f) ExtraUtils.parseVector(s));
    }
}
