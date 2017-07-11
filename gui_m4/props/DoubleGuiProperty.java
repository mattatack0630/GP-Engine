package gui_m4.props;

/**
 * Created by matthew on 6/26/17.
 */
public class DoubleGuiProperty extends GuiProperty<Double>
{
    public DoubleGuiProperty(Double value, String propertyName)
    {
        super(value, propertyName);
    }

    public DoubleGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Double parseValueImplementation(String value)
    {
        return Double.parseDouble(value);
    }
}
