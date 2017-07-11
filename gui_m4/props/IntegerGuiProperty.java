package gui_m4.props;

/**
 * Created by matthew on 6/26/17.
 */
public class IntegerGuiProperty extends GuiProperty<Integer>
{
    public IntegerGuiProperty(Integer value, String propertyName)
    {
        super(value, propertyName);
    }

    public IntegerGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Integer parseValueImplementation(String value)
    {
        return Integer.parseInt(value);
    }

    public String toString()
    {
        return Integer.toString(getPropertyValue());
    }
}
