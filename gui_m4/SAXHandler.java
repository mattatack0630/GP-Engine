package gui_m4;

import gui_m4.elements.GuiElement;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by matthew on 6/26/17.
 */
public class SAXHandler extends DefaultHandler
{
    private LinkedList<GuiElement> elementOrder;
    private GuiElementParser parser;

    public SAXHandler()
    {
        this.parser = new GuiElementParser();
        this.elementOrder = new LinkedList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        qName = qName.toLowerCase();

        Map<String, String> attribMap = new HashMap<>();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            String name = attributes.getQName(i);
            String value = attributes.getValue(i);
            attribMap.put(name, value);
        }

        GuiElement element =  parser.generateElement(qName, attribMap);

        if(elementOrder.size() > 0)
            elementOrder.getLast().addChild(element);

        elementOrder.add(element);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if(elementOrder.size() > 1)
            elementOrder.removeLast();
    }

    public GuiElement getRoot()
    {
        return elementOrder.getLast();
    }
}