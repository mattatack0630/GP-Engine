package gui_m4.props;

import utils.ExtraUtils;
import utils.math.linear.vector.Vector4f;

/**
 * Created by matthew on 6/26/17.
 */
public class Vec4GuiProperty extends GuiProperty<Vector4f>
{
    public Vec4GuiProperty(Vector4f value, String propertyName)
    {
        super(value, propertyName);
    }

    public Vec4GuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Vector4f parseValueImplementation(String value)
    {
        return (Vector4f) ExtraUtils.parseVector(value);
    }
}