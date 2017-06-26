package gui_m3.props;

import utils.ExtraUtils;
import utils.math.linear.vector.Vector4f;

/**
 * Created by matthew on 6/26/17.
 */
public class StringGuiProperty extends GuiProperty<String>
{
    public StringGuiProperty(String value, String propertyName)
    {
        super(value, propertyName);
    }

    public StringGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected String parseValueImplementation(String value)
    {
        return value;
    }
}