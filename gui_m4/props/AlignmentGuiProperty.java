package gui_m4.props;

import gui_m4.Align;

/**
 * Created by matthew on 6/26/17.
 */
public class AlignmentGuiProperty extends GuiProperty<Align>
{
    public AlignmentGuiProperty(Align value, String propertyName)
    {
        super(value, propertyName);
    }

    public AlignmentGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected Align parseValueImplementation(String value)
    {
        return Align.parseAlignment(value);
    }
}
