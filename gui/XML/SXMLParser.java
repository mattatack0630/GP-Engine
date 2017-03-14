package gui.XML;


import gui.Component.Panel.Panel;
import gui.GuiScene;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by mjmcc on 3/14/2017.
 */
public class SXMLParser
{

	public static GuiScene parse(String path)
	{
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXHandler handler = new SAXHandler();
		SAXParser saxParser = null;

		try
		{
			saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(new File(path), handler);
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		} catch (SAXException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		GuiScene scene = new GuiScene();
		scene.addPanel((Panel) handler.getRoot());
		System.out.println(" ---- " + handler.getRoot());

		return scene;
	}

}
