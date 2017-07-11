package gui_m4.css;

import gui_m4.elements.GuiElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 6/30/17.
 */
public class CssOrSelector extends CssCombineSelector
{
    public static final char NAME = ',';

    public CssOrSelector(CssSelector left, CssSelector right)
    {
        super(left, right);
    }

    @Override
    protected List<GuiElement> combineSolve(Map<String, GuiElement> inputLeft, Map<String, GuiElement> inputRight)
    {
        List<GuiElement> output = new ArrayList<>();

        // Brute force, but time not necessarily important
        // as these identifiers only need to be run once
        output.addAll(inputLeft.values());
        output.addAll(inputRight.values());

        return output;
    }
}
