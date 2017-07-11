package gui_m4.css;

import gui_m4.elements.GuiElement;

import java.util.List;

/**
 * Created by matthew on 6/30/17.
 */
public class CssUniversalSelector extends CssBasicSelector
{
    public CssUniversalSelector(String selectorName)
    {
        super(selectorName);
    }

    @Override
    public List<GuiElement> solve(List<GuiElement> inputs)
    {
        return inputs;
    }
}
