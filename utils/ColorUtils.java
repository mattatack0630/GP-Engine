package utils;

import rendering.Color;
import utils.math.Maths;

/**
 * Created by mjmcc on 2/17/2017.
 */
public class ColorUtils
{
	public static Color changedBrightness(Color color, float mag)
	{
		float largest = Maths.max(color.getR(), color.getG(), color.getB());
		float c = mag / largest;

		Color newColor = new Color(color.getR() * c, color.getG() * c, color.getB() * c, color.getA());
		return newColor;
	}
}
