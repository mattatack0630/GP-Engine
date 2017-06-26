package gui_m3;

import gui.Align;

import java.util.Map;

/**
 * Created by matthew on 5/31/17.
 */
public class HPanelElementBuilder extends GuiElementBuilder
{

    @Override
    public String getName()
    {
        return "h-panel";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        ContainerComponent containerComponent = new ContainerComponent();
        containerComponent.setLayout(new HorizontalLayout());
        containerComponent.setAlignTowards(Align.CENTER);
        el.addComponent(containerComponent);
    }

    @Override
    public void parseAttribute(GuiElement el, Map<String, String> attributeMap)
    {
        ContainerComponent containerComponent = (ContainerComponent) el.getComponent(ContainerComponent.class);

        for (String attribName : attributeMap.keySet())
        {
            String attribValue = attributeMap.get(attribName);

            GuiElementBuilder.parseDefaultAttributes(el, attribName, attribValue);

            switch (attribName)
            {
                case "alignment" :
                    containerComponent.setAlignTowards(Align.parseAlignment(attribValue));
                    break;

            }
        }

    }
}
