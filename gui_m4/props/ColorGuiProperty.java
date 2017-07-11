package gui_m4.props;

import rendering.Color;

/**
 * Created by matthew on 6/26/17.
 */
public class ColorGuiProperty extends GuiProperty<Color>
{
    public ColorGuiProperty(Color value, String propertyName)
    {
        super(value, propertyName);
    }

    public ColorGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Color parseValueImplementation(String value)
    {
        return new Color(value);
    }
}
