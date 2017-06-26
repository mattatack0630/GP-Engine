package gui_m3.props;

import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/26/17.
 */
public class Vec2GuiProperty extends GuiProperty<Vector2f>
{
    public Vec2GuiProperty(Vector2f value, String propertyName)
    {
        super(value, propertyName);
    }

    public Vec2GuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Vector2f parseValueImplementation(String value)
    {
        return (Vector2f) ExtraUtils.parseVector(value);
    }
}