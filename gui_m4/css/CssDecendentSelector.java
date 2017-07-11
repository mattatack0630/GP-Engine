package gui_m4.css;

import gui_m4.elements.GuiElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 6/30/17.
 */
public class CssDecendentSelector extends CssCombineSelector
{
    public static final char NAME = ' ';

    public CssDecendentSelector(CssSelector left, CssSelector right)
    {
        super(left, right);
    }

    @Override
    protected List<GuiElement> combineSolve(Map<String, GuiElement> inputLeft, Map<String, GuiElement> inputRight)
    {
        List<GuiElement> output = new ArrayList<>();

        // Brute force, but time not necessarily important
        // as these identifiers only need to be run once
        for (GuiElement pChild : inputRight.values())
        {
            GuiElement parent = pChild.getParentElement();
            while (parent != null)
            {
                if (inputLeft.containsKey(parent.getId()))
                {
                    output.add(pChild);
                    break;
                }
                parent = parent.getParentElement();
            }
        }

        return output;
    }
}
