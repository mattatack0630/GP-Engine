package gui_m3.props;

/**
 * Created by matthew on 6/26/17.
 */
public class ShortGuiProperty extends GuiProperty<Short>
{
    public ShortGuiProperty(Short value, String propertyName)
    {
        super(value, propertyName);
    }

    public ShortGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Short parseValueImplementation(String value)
    {
        return Short.parseShort(value);
    }
}
