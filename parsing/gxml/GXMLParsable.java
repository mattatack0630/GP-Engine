package parsing.gxml;

import gui.components.Component;

import java.util.Map;

/**
 * Created by mjmcc on 4/9/2017.
 */
public interface GXMLParsable
{
	void addToParsingFuncMap(Map<String, ParseAction> parseMap);

	Component blankInstance();

	String componentName();
}
