package gui_m3;

import gui.components.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthew on 5/31/17.
 */
public class GuiElementParser
{
    private static Map<String, GuiElementBuilder> nameBuilderMap = new HashMap<>();

    public static void init()
    {
        // Default Parsers
        addBuilder(new EmptyElementBuilder());
        addBuilder(new TextureElementBuilder());
        addBuilder(new VPanelElementBuilder());
        addBuilder(new HPanelElementBuilder());
        addBuilder(new FPanelElementBuilder());
        addBuilder(new ButtonElementBuilder());
        addBuilder(new LabelElementBuilder());
        addBuilder(new SliderElementBuilder());
        addBuilder(new TextInputElementBuilder());
        addBuilder(new ToggleButtonBuilder());
        addBuilder(new AnimatedTextureElementBuilder());
    }

    public static void addBuilder(GuiElementBuilder builder)
    {
        nameBuilderMap.put(builder.getName(), builder);
    }

    public static GuiElement generateElement(String name, Map<String, String> attribMap)
    {
        GuiElement element = new GuiElement();
        return generateElement(element, name, attribMap);
    }

    public static GuiElement generateElement(GuiElement element, String name, Map<String, String> attribMap)
    {
        GuiElementBuilder builder = nameBuilderMap.get(name);

        // remove any previous components
        if(element.getComponents().size() > 0)
            element.clearComponents();

        // add defualt element comp
        GuiElementBuilder.addDefaultComponents(element);
        //GuiElementBuilder.parseDefaultAttributes(element, attribMap); //BeginParse, endParse, parseAttrib, tempComps?


        if (builder != null)
        {
            builder.buildElement(element);
            builder.parseAttribute(element, attribMap);

        } else
        {
            System.out.println("There is no builder for the element type " + name);
        }

        element.build();
        return element;
    }

    public static void addAttribs(GuiElement element, String elementType, Map<String, String> attribMap)
    {
        GuiElementBuilder builder = nameBuilderMap.get(elementType);
        builder.parseAttribute(element, attribMap);
    }
}
