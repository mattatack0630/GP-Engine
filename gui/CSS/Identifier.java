package gui.CSS;

import java.util.ArrayList;

/**
 * Created by mjmcc on 10/11/2016.
 */
public class Identifier
{
	public static final String CLASS_PREFIX = "#";
	public static final String ID_PREFIX = ".";

	public ArrayList<String> classNames;
	private ArrayList<String> typeNames;
	private ArrayList<String> idNames;

	public Identifier()
	{
		classNames = new ArrayList<>();
		typeNames = new ArrayList<>();
		idNames = new ArrayList<>();
	}

	public Identifier(String idString)
	{
		classNames = new ArrayList<>();
		typeNames = new ArrayList<>();
		idNames = new ArrayList<>();

		String[] ids = idString.split("(?=\\.)|(?=#)");

		for(int i = 0; i < ids.length; i++)
		{
			String id = ids[i];
			if(id.startsWith(CLASS_PREFIX))
				classNames.add(id.trim());
			if(id.startsWith(ID_PREFIX))
				idNames.add(id.trim());
		}
	}

	public Identifier(Identifier compId)
	{
		classNames = new ArrayList<>();
		typeNames = new ArrayList<>();
		idNames = new ArrayList<>();

		classNames.addAll(compId.classNames);
		idNames.addAll(compId.idNames);
	}

	public void addClassName(String name)
	{
		classNames.add("#"+name);
	}

	public void addIdName(String name)
	{
		idNames.add("."+name);
	}

	public boolean isTypeOf(Identifier id)
	{
		for(String name : classNames)
			for(String name2 : id.classNames)
				if(name.equals(name2))
					return true;

		for(String name : idNames)
			for(String name2 : id.idNames)
				if(name.equals(name2))
					return true;

		return false;
	}

	public int getRelevanceScore(Identifier id)
	{
		int i = 0;

		for(String name : classNames)
			for(String name2 : id.classNames)
				if(name.equals(name2))
					i++;

		for(String name : idNames)
			for(String name2 : id.idNames)
				if(name.equals(name2))
					i++;

		return i;
	}

	public String getId()
	{
		if(idNames.size() == 0)
			return "";

		return idNames.get(0);
	}

	public boolean isInClass(String className)
	{
		return classNames.contains(className);
	}

	public boolean hasId(String idName)
	{
		return idNames.contains(idName);
	}
}
