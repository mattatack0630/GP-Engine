package parsing.utils;

/**
 * Created by mjmcc on 10/29/2016.
 */
public class Validator
{

	public static boolean isNumber(String s)
	{
		boolean result = false;

		for (int i = 0; i < s.length(); i++)
			result = result | (!Character.isDigit(s.charAt(i)) && !Character.isSpaceChar(s.charAt(i)) && !(s.charAt(i) == '.'));

		return !result;
	}

	public static boolean containsRegex(String in, String... regex)
	{
		boolean result = false;

		for (String r : regex)
			result = result || !(in.contains(r));

		return !result;
	}
}
