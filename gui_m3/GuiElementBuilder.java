package gui_m3;

import engine.Engine;
import gui.components.Component;
import rendering.Color;
import resources.TextureResource;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.Map;

/**
 * Created by matthew on 5/31/17.
 */
public abstract class GuiElementBuilder
{
    public abstract String getName();

    public abstract void buildElement(GuiElement el);

    public abstract void parseAttribute(GuiElement el, Map<String, String> attributeMap);

    public static void addDefaultComponents(GuiElement el)
    {
        el.addComponent(new BackgroundComponent(new Color(0, 0, 0, 0)));
    }

    // For every component, call inside other builders
    public static void parseDefaultAttributes(GuiElement el, String attribName, String attribValue)
    {
        switch (attribName)
        {
            case "content-size":
                String[] data1 = attribValue.split("\\(");
                String data2 = data1[1].replace("(", "").replace(")", "");
                String[] content1 = data2.split(",");//stripChars(data1[1], ')', '(').split(",");
                el.setContentSize(new GuiSize(content1[0], content1[1]));
                break;

            case "min-size":
                String[] data3 = attribValue.split("\\(");
                String data4 = data3[1].replace("(", "").replace(")", "");
                String[] content2 = data4.split(",");
                el.setMinSize(new GuiSize(content2[0], content2[1]));
                break;

            case "max-size":
                String[] data5 = attribValue.split("\\(");
                String data6 = data5[1].replace("(", "").replace(")", "");
                String[] content3 = data6.split(",");//stripChars(data1[1], ')', '(').split(",");
                el.setMaxSize(new GuiSize(content3[0], content3[1]));
                break;

            case "margin":
                Vector4f margin = (Vector4f) ExtraUtils.parseVector(attribValue);
                el.setMargin(margin);
                break;

            case "padding":
                Vector4f padding = (Vector4f) ExtraUtils.parseVector(attribValue);
                el.setPadding(padding);
                break;

            case "layer":
                el.setLayer(Integer.valueOf(attribValue));
                break;

            case "id":
                el.setId(attribValue);
                break;

            case "class":
                el.addClass(attribValue);
                break;

            // Backgrounds temp stuff (maybe)
            case "background-color":
                BackgroundComponent bc0 = el.getComponent(BackgroundComponent.class);
                bc0.setColor(new Color(attribValue));
                break;

            case "background-img":
                BackgroundComponent bc1 = el.getComponent(BackgroundComponent.class);
                TextureResource resource = new TextureResource(attribValue, attribValue);
                Engine.getResourceManager().directLoadResource(resource);
                bc1.setTexture(resource.getId());
                break;

        }
    }
}
