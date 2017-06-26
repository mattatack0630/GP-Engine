package gui_m3;

import engine.Engine;
import rendering.Color;
import resources.TextureResource;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by matthew on 5/31/17.
 */
public class TextureElementBuilder extends GuiElementBuilder
{
    @Override
    public String getName()
    {
        return "texture";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        el.addComponent(new TextureComponent());
    }

    @Override
    public void parseAttribute(GuiElement el, Map<String, String> attributeMap)
    {
        TextureComponent textureComponent = el.getComponent(TextureComponent.class);

        for (String attribName : attributeMap.keySet())
        {
            String attribValue = attributeMap.get(attribName);

            GuiElementBuilder.parseDefaultAttributes(el, attribName, attribValue);

            switch (attribName)
            {
                case "texture-size":
                    Vector2f size = (Vector2f) ExtraUtils.parseVector(attribValue);
                    textureComponent.setSize(size);
                    break;

                case "texture-color":
                    textureComponent.setColor(new Color(attribValue));
                    break;

                case "texture-img":
                    TextureResource resource = new TextureResource(attribValue, attribValue);
                    Engine.getResourceManager().directLoadResource(resource);
                    textureComponent.setTexture(resource.getId());
                    break;
            }
        }
    }
}
