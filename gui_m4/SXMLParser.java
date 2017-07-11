package gui_m4;

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
/*

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
		//scene.addPanel((Panel) handler.getRoot());

		return scene;
	}

	public static GuiElement parse2(String path)
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

		return handler.getRootR();
	}
*/


	public static gui_m4.elements.GuiElement parse3(String path)
	{
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		gui_m4.SAXHandler handler = new gui_m4.SAXHandler();
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

		return handler.getRoot();
	}
}
