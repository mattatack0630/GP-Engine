package gui_m4.css;

import gui_m4.elements.GuiElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 6/30/17.
 */
public abstract class CssCombineSelector extends CssSelector
{
    protected CssSelector left;
    protected CssSelector right;

    public CssCombineSelector(CssSelector left, CssSelector right)
    {
        this.left = left;
        this.right = right;
    }

    public List<GuiElement> solve(List<GuiElement> input)
    {
        List<GuiElement> elsLeft = left.solve(input);
        List<GuiElement> elsRight = right.solve(input);
        Map<String, GuiElement> elsLeftMap = new HashMap<>();
        Map<String, GuiElement> elsRightMap = new HashMap<>();

        for(GuiElement element : elsLeft)
            elsLeftMap.put(element.getId(), element);
        for(GuiElement element : elsRight)
            elsRightMap.put(element.getId(), element);

        return combineSolve(elsLeftMap,elsRightMap);
    }

    protected abstract List<GuiElement> combineSolve(Map<String, GuiElement> inputLeft, Map<String, GuiElement> inputRight);
}
