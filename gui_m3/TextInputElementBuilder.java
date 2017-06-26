package gui_m3;

import gui.Align;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 6/5/17.
 */
public class TextInputElementBuilder extends GuiElementBuilder
{
    public static final String LABEL_PREF = "text-input-label-";
    public static final String BLINKER_PREF = "text-input-blinker-";
    public static final String BACKGROUND_PREF = "text-input-background-";

    public static final String CONTAINER_PREF = "text-input-container-"; //

    @Override
    public String getName()
    {
        return "text-input";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        // Builds el into a panel component
        // Default values
        Map<String, String> labelAttribs = new HashMap<>();
        labelAttribs.put("font-color", "rgba(0,0,0,1)");

        Map<String, String> blinkerAttribs = new HashMap<>();
        blinkerAttribs.put("texture-color", "rgba(0,0,0,1)");
        blinkerAttribs.put("min-size", "vec2(0.01vp, 75%)");

        Map<String, String> backgroundAttribs = new HashMap<>();
        backgroundAttribs.put("texture-color", "rgba(1,1,1,1)");
        backgroundAttribs.put("min-size", "vec2(100%, 100%)");
        backgroundAttribs.put("max-size", "vec2(100%, 100%)");
        backgroundAttribs.put("layer", "-1");

        Map<String, String> containerAttribs = new HashMap<>();
        containerAttribs.put("min-size", "vec2(100%, 100%)");
        containerAttribs.put("max-size", "vec2(100%, 100%)");

        GuiElement labelContainer = GuiElementParser.generateElement("h-panel", containerAttribs);
        GuiElement label = GuiElementParser.generateElement("label", labelAttribs);
        GuiElement blinker = GuiElementParser.generateElement("texture", blinkerAttribs);
        GuiElement background = GuiElementParser.generateElement("texture", backgroundAttribs);

        // Container to hold label and blinker
        ContainerComponent labelContainerComp = labelContainer.getComponent(ContainerComponent.class);
        labelContainerComp.setAlignTowards(Align.CENTER_LEFT);
        labelContainerComp.setWrapChildren(false);
        labelContainerComp.addChild(label);
        labelContainerComp.addChild(blinker);

        // Container to hold ^ container and background texture
        ContainerComponent containerComponent = new ContainerComponent();
        containerComponent.setLayout(new FlatLayout());
        containerComponent.setWrapChildren(false);
        containerComponent.addChild(background);
        containerComponent.addChild(labelContainer);

        // Primary TextInput component
        TextInputComponent textInputComponent = new TextInputComponent(labelContainerComp, label.getComponent(TextComponent.class),
                blinker.getComponent(TextureComponent.class), background.getComponent(TextureComponent.class));

        // Add components to element
        el.addComponent(containerComponent);
        el.addComponent(textInputComponent);
        el.addComponent(new MouseInteractionComponent());
        el.addComponent(new KeyboardEventAlertComponent());
    }

    @Override
    public void parseAttribute(GuiElement el, Map<String, String> attributeMap)
    {
        ContainerComponent contComp = el.getComponent(ContainerComponent.class);

        Map<String, String> labelAttributeMap = new HashMap<>();
        Map<String, String> blinkerAttributeMap = new HashMap<>();
        Map<String, String> backgroundAttributeMap = new HashMap<>();
        Map<String, String> labelContainAttributeMap = new HashMap<>();

        for (String attribName : attributeMap.keySet())
        {
            String attribValue = attributeMap.get(attribName);

            GuiElementBuilder.parseDefaultAttributes(el, attribName, attribValue);

            if (attribName.startsWith(LABEL_PREF))
            {
                // Label parsing
                String name = attribName.replaceAll(LABEL_PREF, "");
                labelAttributeMap.put(name, attribValue);

            } else if (attribName.startsWith(BLINKER_PREF))
            {
                // Blinker parsing
                String name = attribName.replaceAll(BLINKER_PREF, "");
                blinkerAttributeMap.put(name, attribValue);

            } else if (attribName.startsWith(BACKGROUND_PREF))
            {
                // Background parsing
                String name = attribName.replaceAll(BACKGROUND_PREF, "");
                backgroundAttributeMap.put(name, attribValue);

            } else if (attribName.startsWith(CONTAINER_PREF))
            {
                // Background parsing
                String name = attribName.replaceAll(CONTAINER_PREF,"");
                labelContainAttributeMap.put(name, attribValue);

            } else
            {
                // TextIn parsing
                switch (attribName)
                {

                }
            }
        }

        List<GuiElement> children = contComp.getChildren();
        GuiElement backgroundEl = children.get(0);
        GuiElement labelContainEl = children.get(1);

        GuiElementParser.addAttribs(backgroundEl, "texture", backgroundAttributeMap);
        GuiElementParser.addAttribs(labelContainEl, "h-panel", labelContainAttributeMap);

        ContainerComponent labelContainComp = labelContainEl.getComponent(ContainerComponent.class);
        List<GuiElement> labelContChildren = labelContainComp.getChildren();

        GuiElementParser.addAttribs(labelContChildren.get(0), "label", labelAttributeMap);
        GuiElementParser.addAttribs(labelContChildren.get(1), "texture", blinkerAttributeMap);
    }
}
