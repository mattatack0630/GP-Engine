package gui_m4.props;

import gui_m4.GuiSize;

/**
 * Created by matthew on 6/26/17.
 */
public class SizeGuiProperty extends GuiProperty<GuiSize>
{
    public SizeGuiProperty(GuiSize value, String propertyName)
    {
        super(value, propertyName);
    }

    public SizeGuiProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected GuiSize parseValueImplementation(String value)
    {
        String[] data3 = value.split("\\(");
        String data4 = data3[1].replaceAll("[\\(]|[\\)]", "");
        String[] content2 = data4.split(",");
        return new GuiSize(content2[0], content2[1]);
    }
}
