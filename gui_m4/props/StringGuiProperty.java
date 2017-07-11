package gui_m4.props;

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