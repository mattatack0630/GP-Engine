package gui_m3.props;

/**
 * Created by matthew on 6/26/17.
 */
public class LongGuiProperty extends GuiProperty<Long>
{
    public LongGuiProperty(Long value, String propertyName)
    {
        super(value, propertyName);
    }

    public LongGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Long parseValueImplementation(String value)
    {
        return Long.parseLong(value);
    }
}