package gui_m4.props;


/**
 * Created by matthew on 6/26/17.
 */
public class CharGuiProperty extends GuiProperty<Character>
{
    public CharGuiProperty(Character value, String propertyName)
    {
        super(value, propertyName);
    }

    public CharGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Character parseValueImplementation(String value)
    {
        return value.length() > 0 ? value.charAt(0) : '\0';
    }
}