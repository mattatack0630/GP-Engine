package gui_m3;

import gui.text.TextAttributes;
import rendering.Color;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by matthew on 6/1/17.
 */
public class LabelElementBuilder extends GuiElementBuilder
{
    @Override
    public String getName()
    {
        return "label";
    }

    @Override
    public void buildElement(GuiElement el)
    {
        el.addComponent(new TextComponent());
    }

    @Override
    public void parseAttribute(GuiElement el, Map<String, String> attributeMap)
    {
        TextComponent textComponent = el.getComponent(TextComponent.class);
        TextAttributes textAttributes = textComponent.getTextAttributes();

        for (String attribName : attributeMap.keySet())
        {
            String attribValue = attributeMap.get(attribName);

            GuiElementBuilder.parseDefaultAttributes(el, attribName, attribValue);

            switch (attribName)
            {
                case "text":
                    textComponent.setText(attribValue);
                    break;

                case "font":
                    textAttributes.setFont(attribValue);
                    break;

                case "font-color":
                    textAttributes.setColor(new Color(attribValue));
                    break;

                case "font-letter-width":
                    textAttributes.setLetterWidth(Float.valueOf(attribValue));
                    break;

                case "font-line-spacing":
                    textAttributes.setLineSpacing(Float.valueOf(attribValue));
                    break;

                case "font-outline-color":
                    textAttributes.setOutLineColor(new Color(attribValue));
                    break;

                case "font-outline-sharpness":
                    textAttributes.setOutLineSharpness(Float.valueOf(attribValue));
                    break;

                case "font-outline-width":
                    textAttributes.setOutLineWidth(Float.valueOf(attribValue));
                    break;

                case "font-shadow-color":
                    textAttributes.setShadowColor(new Color(attribValue));
                    break;

                case "font-shadow-offset":
                    Vector2f v = (Vector2f) ExtraUtils.parseVector(attribValue);
                    textAttributes.setShadowOffX(v.x());
                    textAttributes.setShadowOffY(v.y());
                    break;

                case "font-size":
                    textAttributes.setFontSize(Float.valueOf(attribValue));
                    break;

                case "font-line-length":
                    textAttributes.setMaxLineLength(Integer.valueOf(attribValue));
                    break;

                case "font-sharpness":
                    textAttributes.setSharpness(Float.valueOf(attribValue));
                    break;

            }
        }
    }
}
