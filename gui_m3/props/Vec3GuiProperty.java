package gui_m3.props;

import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by matthew on 6/26/17.
 */
public class Vec3GuiProperty extends GuiProperty<Vector3f>
{
    public Vec3GuiProperty(Vector3f value, String propertyName)
    {
        super(value, propertyName);
    }

    public Vec3GuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Vector3f parseValueImplementation(String value)
    {
        return (Vector3f) ExtraUtils.parseVector(value);
    }
}
