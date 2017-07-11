package gui_m4;


import gui_m4.elements.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthew on 6/26/17.
 */
public class GuiElementParser
{
    private Map<String, GuiElementBuilder> nameBuilderMap = new HashMap<>();

    public GuiElementParser()
    {
        // Views
        addBuilder(GuiTextureElement.BUILDER);
        addBuilder(AnimatedTexture.BUILDER);
        addBuilder(LabelGuiElement.BUILDER);

        // Controls
        addBuilder(ButtonGuiElement.BUILDER);
        addBuilder(AreaSliderGuiElement.BUILDER);
        addBuilder(VSliderGuiElement.BUILDER);
        addBuilder(HSliderGuiElement.BUILDER);
        addBuilder(TextInputGuiElement.BUILDER);

        // Containers
        addBuilder(VPanelGuiElement.BUILDER);
        addBuilder(HPanelGuiElement.BUILDER);
        addBuilder(StackPanelGuiElement.BUILDER);
        addBuilder(ScrollPanelGuiElement.BUILDER);
    }

    public void addBuilder(GuiElementBuilder builder)
    {

        nameBuilderMap.put(builder.getElementName(), builder);
    }

    public GuiElement generateElement(String name, Map<String, String> attribMap)
    {
        GuiElementBuilder builder =  nameBuilderMap.get(name);
        GuiElement element = builder.genInstance();
        builder.applyAttributes(element, attribMap);
        return element;
    }
}
