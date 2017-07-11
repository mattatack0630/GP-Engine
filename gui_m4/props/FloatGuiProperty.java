package gui_m4.props;

/**
 * Created by matthew on 6/26/17.
 */
public class FloatGuiProperty extends GuiProperty<Float>
{
    public FloatGuiProperty(Float value, String propertyName)
    {
        super(value, propertyName);
    }

    public FloatGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Float parseValueImplementation(String value)
    {
        return Float.parseFloat(value);
    }
}
