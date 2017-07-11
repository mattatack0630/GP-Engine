package gui_m4.css;

import gui_m4.elements.GuiElement;

import java.util.List;

/**
 * Created by matthew on 6/29/17.
 */
public abstract class CssSelector
{
    public abstract List<GuiElement> solve(List<GuiElement> inputs);
}
