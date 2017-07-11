package gui_m4;

import gui_m4.css.CssSelectionParser;
import gui_m4.css.CssSelector;
import gui_m4.elements.GuiElement;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by matthew on 6/29/17.
 */
public class GuiElementLocator
{
    private List<GuiElement> elementStackReference;

    protected GuiElementLocator(List<GuiElement> elementStack)
    {

        this.elementStackReference = elementStack;
    }


    public GuiElement getById(String id)
    {
        for (GuiElement element : elementStackReference)
        {
            if (element.getId().equals(id))
            {
                return element;
            }
        }

        return null;
    }

    public List<GuiElement> getByClass(String className)
    {
        List<GuiElement> list = new LinkedList<>();

        for (GuiElement element : elementStackReference)
        {
            if (element.hasClass(className))
            {
                list.add(element);
            }
        }

        return list;
    }

    public List<GuiElement> getBySelector(String selector)
    {
        return getBySelector(CssSelectionParser.parseSelector(selector));
    }

    public List<GuiElement> getBySelector(CssSelector selector)
    {

        return selector.solve(elementStackReference);
    }

    public GuiElement getFirstBySelector(String selectorStr)
    {
        CssSelector selector = CssSelectionParser.parseSelector(selectorStr);
        List<GuiElement> fElement = selector.solve(elementStackReference);
        return fElement.size() > 0 ? fElement.get(0) : null; // throw exception?
    }

    public GuiElement getFirstBySelector(CssSelector selector)
    {
        List<GuiElement> fElement = selector.solve(elementStackReference);
        return fElement.size() > 0 ? fElement.get(0) : null; // throw exception?
    }


    public GuiElement getById(String id, GuiElement rootElement)
    {
        List<GuiElement> decentents = getDecedents(rootElement, new LinkedList<>());

        for (GuiElement element : decentents)
        {
            if (element.getId().equals(id))
            {
                return element;
            }
        }

        return null;
    }

    public List<GuiElement> getByClass(String className, GuiElement rootElement)
    {
        List<GuiElement> decentents = getDecedents(rootElement, new LinkedList<>());
        List<GuiElement> list = new LinkedList<>();

        for (GuiElement element : decentents)
        {
            if (element.hasClass(className))
            {
                list.add(element);
            }
        }

        return list;
    }

    public List<GuiElement> getBySelector(String selector, GuiElement rootElement)
    {
        return getBySelector(CssSelectionParser.parseSelector(selector), rootElement);
    }

    public List<GuiElement> getBySelector(CssSelector selector, GuiElement rootElement)
    {
        List<GuiElement> decentents = getDecedents(rootElement, new LinkedList<>());
        return selector.solve(decentents);
    }

    public GuiElement getFirstBySelector(String selectorStr, GuiElement rootElement)
    {
        List<GuiElement> decentents = getDecedents(rootElement, new LinkedList<>());
        CssSelector selector = CssSelectionParser.parseSelector(selectorStr);
        List<GuiElement> fElement = selector.solve(decentents);
        return fElement.size() > 0 ? fElement.get(0) : null; // throw exception?
    }

    public GuiElement getFirstBySelector(CssSelector selector, GuiElement rootElement)
    {
        List<GuiElement> decentents = getDecedents(rootElement, new LinkedList<>());
        List<GuiElement> fElement = selector.solve(decentents);
        return fElement.size() > 0 ? fElement.get(0) : null; // throw exception?
    }


    protected void resetElementReference(List<GuiElement> elementStack)
    {

        this.elementStackReference = elementStack;
    }

    private List<GuiElement> getDecedents(GuiElement rootElement, List<GuiElement> list)
    {
        list.add(rootElement);

        for (GuiElement element : rootElement.getChildren())
        {
            getDecedents(element, list);
        }

        return list;
    }
}
