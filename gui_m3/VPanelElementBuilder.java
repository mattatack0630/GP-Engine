package gui_m3;

import engine.Engine;
import gui.Align;
import rendering.Color;
import resources.TextureResource;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by matthew on 5/31/17.
 */
public class VPanelElementBuilder extends GuiElementBuilder
{
    @Override
    public String getName()
    {
        return "v-panel";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        ContainerComponent containerComponent = new ContainerComponent();
        containerComponent.setLayout(new VerticalLayout());
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
