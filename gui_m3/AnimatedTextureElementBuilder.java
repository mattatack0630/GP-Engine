package gui_m3;

import engine.Engine;
import models.SpriteSequence;
import models.SpriteSheet;
import parsing.utils.ParsingUtils;
import rendering.Color;
import resources.ResourceManager;
import resources.SpriteSheetResource;
import resources.TextureResource;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by matthew on 6/12/17.
 */
public class AnimatedTextureElementBuilder extends GuiElementBuilder
{
    @Override
    public String getName()
    {
        return "animated-texture";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        el.addComponent(new AnimatedTextureComponent());
    }

    @Override
    public void parseAttribute(GuiElement el, Map<String, String> attributeMap)
    {
        AnimatedTextureComponent textureComponent = el.getComponent(AnimatedTextureComponent.class);

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

                case "color":
                    textureComponent.setColor(new Color(attribValue));
                    break;

                case "animation-sequence":
                    if(attribValue.contains("-"))
                    {
                        String[] data0 = attribValue.split("-");
                        int i0 = Integer.valueOf(data0[0]);
                        int i1 = Integer.valueOf(data0[1]);
                        int[] ss = new int[Math.abs(i1-i0)];
                        for(int i = i0; i<i1; i++){
                            ss[i - i0] = i;
                        }
                        textureComponent.setAnimSequence(ss);
                    }

                    if(attribValue.contains(","))
                    {
                        String[] sArray = attribValue.split(",");
                        int[] i = ParsingUtils.toIntArray(sArray);
                        textureComponent.setAnimSequence(i);
                    }
                    break;
                case "animation-sheet":
                    SpriteSheetResource res1 = new SpriteSheetResource("animation-texture-" + el.hashCode(), attribValue);
                    Engine.getResourceManager().directLoadResource(res1);
                    SpriteSheet sheet1 = res1.getSpriteSheet();
                    textureComponent.setAnimSheet(sheet1);
                    break;
                case "animation-fps":
                    textureComponent.setFPS(Float.valueOf(attribValue));
                    break;
                case "animation-loop":
                    textureComponent.setLoop(Boolean.valueOf(attribValue));
                    break;
            }
        }
    }
}
