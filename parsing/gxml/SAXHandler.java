package parsing.gxml;

import gui.components.Component;
import gui.components.Panel;
import gui_m3.ContainerComponent;
import gui_m3.GuiElement;
import gui_m3.GuiElementParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 3/14/2017.
 */
public class SAXHandler extends DefaultHandler
{
    //private static ComponentXMLBuilder componentXMLBuilder = new ComponentXMLBuilder();
    private GuiElement currentComponent;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        qName = qName.toLowerCase();

        GuiElement parent = currentComponent == null ? null : currentComponent;
        ContainerComponent containerComponent = parent == null ? null : parent.getComponent(ContainerComponent.class);

        Map<String, String> attribMap = new HashMap<>();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            String name = attributes.getQName(i).toLowerCase();
            String value = attributes.getValue(i);
            attribMap.put(name, value);
        }

        currentComponent = GuiElementParser.generateElement(qName, attribMap);

        if (containerComponent != null)
            containerComponent.addChild(currentComponent);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        GuiElement parent = currentComponent.getParentElement();

        if (parent != null)
            currentComponent = parent;
    }

    public GuiElement getRootR()
    {
        return currentComponent;
    }

    public Component getRoot()
    {
        return null;
    }


}
/*		Panel parent = currentComponent != null ? (Panel) currentComponent : null;
        qName = qName.toLowerCase();

		currentComponent = componentXMLBuilder.genInstanceFromName(qName);

		for (int i = 0; i < attributes.getLength(); i++)
		{
			String name = attributes.getQName(i).toLowerCase();
			String value = attributes.getValue(i);
			componentXMLBuilder.giveAttribute(qName, currentComponent, name, value);
		}

		if (parent != null)
			parent.addComponent(currentComponent);*/
/*if (currentComponent.parent != null)
			currentComponent = currentComponent.parent;*/