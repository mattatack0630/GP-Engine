package gui_m3;

import gui.Align;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 6/1/17.
 */
public class SliderElementBuilder extends GuiElementBuilder
{
    private static final String BUTTON_PREF = "slider-button-";
    private static final String BACKGROUND_PREF = "slider-background-";

    @Override
    public String getName()
    {
        return "slider";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        Map<String, String> buttonAttribs = new HashMap<>();
        buttonAttribs.put("texture-size", "vec2(0.1, 0.1)");
        buttonAttribs.put("layer", "1");
        GuiElement buttonEl = GuiElementParser.generateElement("texture", buttonAttribs);

        Map<String, String> backgroundAttribs = new HashMap<>();
        backgroundAttribs.put("min-size", "vec2(100%, 100%)");
        backgroundAttribs.put("layer", "0");
        GuiElement backgroundEl = GuiElementParser.generateElement("texture", backgroundAttribs);

        ContainerComponent containerComponent = new ContainerComponent();
        containerComponent.setAlignTowards(Align.CENTER);
        containerComponent.setLayout(new FlatLayout());
        containerComponent.addChild(backgroundEl);
        containerComponent.addChild(buttonEl);
        el.addComponent(containerComponent);

        containerComponent.onBuild();
        el.calculateSizes();

        SliderComponent sliderComponent = new SliderComponent(backgroundEl, buttonEl);

        el.addComponent(new MouseInteractionComponent());
        el.addComponent(sliderComponent);
    }

    @Override
    public void parseAttribute(GuiElement el, Map<String, String> attributeMap)
    {
        ContainerComponent contComp = el.getComponent(ContainerComponent.class);
        SliderComponent sliderComp = el.getComponent(SliderComponent.class);

        Map<String, String> buttonAttributeMap = new HashMap<>();
        Map<String, String> backgroundAttributeMap = new HashMap<>();

        for (String attribName : attributeMap.keySet())
        {
            String attribValue = attributeMap.get(attribName);

            GuiElementBuilder.parseDefaultAttributes(el, attribName, attribValue);

            if (attribName.startsWith(BUTTON_PREF))
            {
                // Button parsing
                String name = attribName.replaceAll(BUTTON_PREF, "");
                buttonAttributeMap.put(name, attribValue);
            } else if (attribName.startsWith(BACKGROUND_PREF))
            {
                // Background parsing
                String name = attribName.replaceAll(BACKGROUND_PREF, "");
                backgroundAttributeMap.put(name, attribValue);
            } else
            {
                switch (attribName)
                {
                    // Slider Parsing
                    case "lock-x":
                        sliderComp.setLockX(Boolean.valueOf(attribValue));
                        break;
                    case "lock-y":
                        sliderComp.setLockY(Boolean.valueOf(attribValue));
                        break;
                }
            }
        }

        List<GuiElement> children = contComp.getChildren();
        GuiElementParser.addAttribs(children.get(0), "texture", backgroundAttributeMap);
        GuiElementParser.addAttribs(children.get(1), "texture", buttonAttributeMap);
    }
}
