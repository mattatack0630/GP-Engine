package gui_m4.css;

import gui_m4.elements.GuiElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 6/30/17.
 */
public class CssIdSelector extends CssBasicSelector
{
    public static final char NAME = '#';

    public CssIdSelector(String selectorName)
    {
        super(selectorName);
    }

    @Override
    public List<GuiElement> solve(List<GuiElement> inputs)
    {
        List<GuiElement> output = new ArrayList<>();

        for (GuiElement element : inputs)
        {
            String elementId = element.getId();
            if (elementId.equals(selectorName))
            {
                output.add(element);
            }
        }

        return output;
    }
}
