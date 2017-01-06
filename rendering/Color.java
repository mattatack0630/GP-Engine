package rendering;

import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * The CssColor class
 * <p>
 * Use to create RGBA colors (0-1 components)
 */
public class Color
{
	// Static Colors
	public static final Color NONE = new Color(0,0,0,0);
	public static final Color RED = new Color(1,0,0,.5f);
	public static final Color YELLOW = new Color(1,1,0,.5f);
	public static final Color GREEN = new Color(0,1,0,.5f);
	public static final Color BLACK = new Color(0,0,0,.5f);
	public static final Color BLUE = new Color(0,0,1,.5f);
	public static final Color ORANGE = new Color(1,.6f,.1f,.9f);
	public static final Color PURPLE = new Color(.5f,0,.5f,.5f);
	public static final Color CYAN = new Color(0,1,1,.5f);
	public static final Color MAGENTA = new Color(1,0,1,.5f);
	public static final Color WHITE = new Color(1,1,1,.8f);
	public static final Color BROWN = new Color(.54f,.27f,.07f,.5f);
	public static final Color PINK = new Color(1,.411f, .70f, .5f);
	public static final Color GREY = new Color(.5f, .5f, .5f, .5f);
	public static final Color DARK_GREY = new Color(.3f, .3f, .3f, .7f);
	public static final Color STEEL_BLUE = new Color(.43f, .48f, .54f, .5f);
	public static final Color MIDNIGHT_BLUE = new Color(.09f, .09f, .43f, .5f);
	public static final Color MINT_GREEN = new Color(0, .98f, .60f, .5f);
	public static final Color GREENISH_YELLOW = new Color(.48f, .98f, 0f, .5f);
	public static final Color GOLD = new Color(.80f, .54f, 0, .5f);
	public static final Color KAIKI = new Color(.94f, .90f, .54f, .5f);
	public static final Color CORAL = new Color(.80f, .35f, .27f, .5f);
	public static final Color MAROON = new Color(.89f, 0f, 0f, .5f);

	// The RGBA components
	// Define in float 0 to 1 (not int 0-255)
	private float r;
	private float g;
	private float b;
	private float a;

	public Color(int col)
	{
		this.r = 0;
		this.g = 0;
		this.b = 0;
		this.a = 0;

	}

	public Color(String src)
	{
		if (src.startsWith("#"))
		{
			src = src.substring(src.indexOf("#") + 1);
			if (src.length() < 8)
				src += "FF";

			long hex = Long.parseLong(src, 16);
			r = ((hex >> 24) & 0xFF) / 255f;
			g = ((hex >> 16) & 0xFF) / 255f;
			b = ((hex >> 8) & 0xFF) / 255f;
			a = ((hex >> 0) & 0xFF) / 255f;
		}

		if (src.startsWith("rgb"))
		{
			String[] data = src.split("\\(|\\)");
			String[] rgb = data[1].split(",");
			r = Float.parseFloat(rgb[0]);
			g = Float.parseFloat(rgb[1]);
			b = Float.parseFloat(rgb[2]);
			a = 1;
		}

		if (src.startsWith("rgba"))
		{
			String[] data = src.split("\\(|\\)");
			String[] rgb = data[1].split(",");
			r = Float.parseFloat(rgb[0]);
			g = Float.parseFloat(rgb[1]);
			b = Float.parseFloat(rgb[2]);
			a = Float.parseFloat(rgb[3]);
		}
	}

	public Color(Color src)
	{
		this.r = src.r;
		this.g = src.g;
		this.b = src.b;
		this.a = src.a;
	}

	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color()
	{
		this.r = 0;
		this.g = 0;
		this.b = 0;
		this.a = 0;
	}

	public float getR()
	{
		return r;
	}

	public void setR(float r)
	{
		this.r = r;
	}

	public float getG()
	{
		return g;
	}

	public void setG(float g)
	{
		this.g = g;
	}

	public float getB()
	{
		return b;
	}

	public void setB(float b)
	{
		this.b = b;
	}

	public float getA()
	{
		return a;
	}

	public void setA(float a)
	{
		this.a = a;
	}

	public static Color random()
	{
		return new Color((float) Math.random(),(float) Math.random(),(float) Math.random(),.5f);
	}

	public static Color random(float r)
	{
		return new Color((float) Math.random() * r, (float) Math.random() * r, (float) Math.random() * r, .5f);
	}

	public static Color HSVtoRGB(float h, float s, float v)
	{
		// H is given on [0->6] or -1. S and V are given on [0->1].
		// RGB are each returned on [0->1].
		float m, n, f;
		int i;

		float[] hsv = new float[3];
		float[] rgb = new float[3];

		hsv[0] = h;
		hsv[1] = s;
		hsv[2] = v;

		if (hsv[0] == -1)
		{
			rgb[0] = rgb[1] = rgb[2] = hsv[2];
			return new Color(rgb[0],rgb[1],rgb[2],.5f);
		}
		i = (int) (Math.floor(hsv[0]));
		f = hsv[0] - i;
		if (i % 2 == 0)
		{
			f = 1 - f; // if i is even
		}
		m = hsv[2] * (1 - hsv[1]);
		n = hsv[2] * (1 - hsv[1] * f);
		switch (i)
		{
			case 6:
			case 0:
				rgb[0] = hsv[2];
				rgb[1] = n;
				rgb[2] = m;
				break;
			case 1:
				rgb[0] = n;
				rgb[1] = hsv[2];
				rgb[2] = m;
				break;
			case 2:
				rgb[0] = m;
				rgb[1] = hsv[2];
				rgb[2] = n;
				break;
			case 3:
				rgb[0] = m;
				rgb[1] = n;
				rgb[2] = hsv[2];
				break;
			case 4:
				rgb[0] = n;
				rgb[1] = m;
				rgb[2] = hsv[2];
				break;
			case 5:
				rgb[0] = hsv[2];
				rgb[1] = m;
				rgb[2] = n;
				break;
		}

		return new Color(rgb[0],rgb[1],rgb[2], .5f);

	}

	public Vector4f getVector4f()
	{
		return new Vector4f(r, g, b, a);
	}

	public String toString()
	{
		return "Color : rgba(" + r + ", " + g + ", " + b + ", " + a + ")";
	}

	public Vector2f rg()
	{
		return new Vector2f(r, g);
	}

	public Vector3f rgb()
	{
		return new Vector3f(r, g, b);
	}

	public Vector4f rgba()
	{
		return new Vector4f(r, g, b, a);
	}

}
