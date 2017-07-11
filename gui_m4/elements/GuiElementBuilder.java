package gui_m4.elements;

import gui_m4.elements.GuiElement;
import gui_m4.props.GuiProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthew on 6/26/17.
 */
public abstract class GuiElementBuilder <T extends GuiElement>
{
    public abstract T genInstance();

    public abstract String getElementName(); // the elements parable name

    public abstract boolean isParentable(); // allowed to have parsed children

    public void applyAttributes(T element, Map<String, String> attribMap)
    {
        HashMap<String, GuiProperty> propertyMap = element.getProperties();

        for(GuiProperty property : propertyMap.values()){
            String value = attribMap.get(property.getPropertyName());
            if(value != null) property.parseValue(value);
        }
    }
}
