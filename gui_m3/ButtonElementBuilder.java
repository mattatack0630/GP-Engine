package gui_m3;

import gui.Align;

import java.util.Map;

/**
 * Created by matthew on 6/1/17.
 */
public class ButtonElementBuilder extends GuiElementBuilder
{
    @Override
    public String getName()
    {
        return "button";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        el.addComponent(new MouseInteractionComponent());

        ContainerComponent containerComponent = new ContainerComponent();
        containerComponent.setLayout(new FlatLayout());
        containerComponent.setAlignTowards(Align.CENTER);
        containerComponent.setMaxChildren(1);
        el.addComponent(containerComponent);
    }

    @Override
    public void parseAttribute(GuiElement el, Map<String, String> attributeMap)
    {
        for (String attribName : attributeMap.keySet())
        {
            String attribValue = attributeMap.get(attribName);

            GuiElementBuilder.parseDefaultAttributes(el, attribName, attribValue);

            switch (attribName)
            {

            }
        }
    }
}
