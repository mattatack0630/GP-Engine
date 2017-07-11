package gui_m4.props;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 6/26/17.
 */
public class StringListGuiProperty extends GuiProperty<List<String>>
{
    public StringListGuiProperty(ArrayList<String> strs, String name)
    {
        super(strs, name);
    }

    @Override
    protected List<String> parseValueImplementation(String value)
    {
        List<String> strings1 = new ArrayList<>();
        String[] strings = value.split(",");

        for (String s : strings)
        {
            strings1.add(s.trim());
        }

        return strings1;
    }
}
