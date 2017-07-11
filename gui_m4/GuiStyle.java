package gui_m4;

import gui_m4.css.CssSelectionParser;
import gui_m4.css.CssSelector;
import gui_m4.elements.GuiElement;
import gui_m4.props.GuiProperty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 7/3/17.
 */
public class GuiStyle
{
    private Map<GuiElement, List<GuiProperty>> elementStyleProperties;
    private Map<String, List<GuiProperty>> selectorStyleProperties;

    public GuiStyle()
    {
        this.elementStyleProperties = new HashMap<>();
        this.selectorStyleProperties = new HashMap<>();
    }

    public void addProperty(String selectorStr, GuiProperty property)
    {
        List<GuiProperty> properties;
        if (selectorStyleProperties.containsKey(selectorStr))
        {
            properties = selectorStyleProperties.get(selectorStr);
        } else
        {
            properties = new LinkedList<>();
            selectorStyleProperties.put(selectorStr, properties);
        }

        properties.add(property);
    }

    public void addProperty(GuiElement element, GuiProperty property)
    {
        List<GuiProperty> elements;

        if (elementStyleProperties.containsKey(element))
        {
            elements = elementStyleProperties.get(element);
        } else
        {
            elements = new LinkedList<>();
            elementStyleProperties.put(element, elements);
        }

        elements.add(property);
    }

    // Apply the style with a root
    public void apply(GuiElement root)
    {
        List<GuiElement> elementStack = generateStack(root, new LinkedList<>());
        apply(elementStack);
    }

    // Apply the style with a stack
    public void apply(List<GuiElement> elementStack)
    {
        resolveSelectorProperties(elementStack);

        for (GuiElement element1 : elementStyleProperties.keySet())
        {
            List<GuiProperty> declaredProperties = elementStyleProperties.get(element1);
            HashMap<String, GuiProperty> propertyHashMap = element1.getProperties();

            for (GuiProperty declaredProperty : declaredProperties)
            {
                GuiProperty elementProperty = propertyHashMap.get(declaredProperty.getPropertyName());
                if (elementProperty.getClass().isInstance(declaredProperty))
                {
                    elementProperty.setPropertyValue(declaredProperty.getPropertyValue());
                }
            }
        }
    }

    // Turn selector properties into element properties
    public void resolveSelectorProperties(List<GuiElement> elementStack)
    {
        for (String selectorStr : selectorStyleProperties.keySet())
        {
            List<GuiProperty> selectorProperties = selectorStyleProperties.get(selectorStr);
            CssSelector selector = CssSelectionParser.parseSelector(selectorStr);
            List<GuiElement> elements = selector.solve(elementStack);

            for (GuiElement element : elements)
            {
                List<GuiProperty> properties;

                if (elementStyleProperties.containsKey(element))
                {
                    properties = elementStyleProperties.get(element);
                } else
                {
                    properties = new LinkedList<>();
                    elementStyleProperties.put(element, properties);
                }

                properties.addAll(selectorProperties);
            }
        }

        selectorStyleProperties.clear();
    }

    // Generate a element list containing all decedents of this element
    private List<GuiElement> generateStack(GuiElement rootElement, List<GuiElement> list)
    {
        list.add(rootElement);

        for (GuiElement element : rootElement.getChildren())
            generateStack(element, list);

        return list;
    }
}
