package gui_m3;

import gui.Align;

import java.util.Map;

/**
 * Created by matthew on 6/12/17.
 */
public class ToggleButtonBuilder extends GuiElementBuilder
{
    @Override
    public String getName()
    {
        return "toggle";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        el.addComponent(new MouseInteractionComponent());
        el.addComponent(new MetaAlertComponent());

        ContainerComponent containerComponent = new ContainerComponent();
        containerComponent.setLayout(new FlatLayout());
        containerComponent.setAlignTowards(Align.CENTER);
        el.addComponent(containerComponent);

        el.addComponent(new ToggleComponent(containerComponent));
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
