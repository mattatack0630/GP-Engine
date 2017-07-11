package gui_m4.props;

/**
 * Created by matthew on 6/26/17.
 */
public class BooleanGuiProperty extends GuiProperty<Boolean>
{
    public BooleanGuiProperty(Boolean value, String propertyName)
    {
        super(value, propertyName);
    }

    public BooleanGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Boolean parseValueImplementation(String value)
    {
        return Boolean.parseBoolean(value);
    }
}