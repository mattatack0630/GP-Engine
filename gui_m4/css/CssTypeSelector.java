package gui_m4.css;

import gui_m4.elements.GuiElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 6/30/17.
 */
public class CssTypeSelector extends CssBasicSelector
{
    public CssTypeSelector(String selectorName)
    {
        super(selectorName);
    }

    @Override
    public List<GuiElement> solve(List<GuiElement> inputs)
    {
        List<GuiElement> output = new ArrayList<>();

        for (GuiElement element : inputs)
        {
            String elementType = element.getElementTypeName();
            if (elementType.equals(selectorName))
            {
                output.add(element);
            }
        }

        return output;
    }
}
