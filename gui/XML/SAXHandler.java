package gui.XML;

import gui.Component.Component;
import gui.Component.Panel.Panel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by mjmcc on 3/14/2017.
 */
public class SAXHandler extends DefaultHandler
{
	private Component currentComponent;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		Panel parent = currentComponent != null ? (Panel) currentComponent : null;
		currentComponent = ComponentDictionary.getComponent(qName);

		for (int i = 0; i < attributes.getLength(); i++)
		{
			String name = attributes.getQName(i).toLowerCase();
			String value = attributes.getValue(i);
			currentComponent.applyAttrib(name, value);
		}

		currentComponent.updateContent();

		if (parent != null) parent.addChild(currentComponent);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (currentComponent.parent != null)
			currentComponent = currentComponent.parent;
	}

	public Component getRoot()
	{
		return currentComponent;
	}
}
