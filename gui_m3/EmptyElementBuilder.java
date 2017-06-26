package gui_m3;

import java.util.Map;

/**
 * Created by matthew on 6/1/17.
 */
public class EmptyElementBuilder extends GuiElementBuilder
{
    @Override
    public String getName()
    {
        return "component";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        // Adds nothing
    }

    @Override
    public void parseAttribute(GuiElement el, Map<String, String> attributeMap)
    {
        for (String attribName : attributeMap.keySet())
        {
            String attribValue = attributeMap.get(attribName);
            GuiElementBuilder.parseDefaultAttributes(el, attribName, attribValue);
        }
    }
}
