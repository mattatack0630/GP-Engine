package gui.CSS;

/**
 * Created by mjmcc on 10/11/2016.
 */
public class CssNode
{
	String identifiers;
	String[] innerAttribs;

	CssNode(String identifiers, String[] innerAttribs)
	{
		this.identifiers = identifiers.trim();
		this.innerAttribs = innerAttribs;


		for(String i : innerAttribs)
			i = i.trim();
	}

	@Override
	public String toString()
	{
		String s = "";

		s+="Inner Attributes\n";
		for(String i : innerAttribs)
			s+="\t"+i+"\n";

		return s;
	}
}
