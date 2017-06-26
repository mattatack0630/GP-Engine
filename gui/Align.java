package gui;

import utils.math.linear.vector.Vector2f;

/**
 * Alignment Class
 * A data class that stores an alignment type
 */
public class Align
{
	/**
	 * Static alignment objects that can be used to define an alignment
	 */
	public static final Align TOP_LEFT = new Align(0, 0);
	public static final Align CENTER_LEFT = new Align(1, 0);
	public static final Align VBURST_LEFT = new Align(2, 0);
	public static final Align BOTTOM_LEFT = new Align(3, 0);

	public static final Align TOP_CENTER = new Align(0, 1);
	public static final Align CENTER = new Align(1, 1);
	public static final Align VBURST_CENTER = new Align(2, 1);
	public static final Align BOTTOM_CENTER = new Align(3, 1);

	public static final Align TOP_HBURST = new Align(0, 2);
	public static final Align CENTER_HBURST = new Align(1, 2);
	public static final Align VBURST_HBURST = new Align(2, 2);
	public static final Align BOTTOM_HBURST = new Align(3, 2);

	public static final Align TOP_RIGHT = new Align(0, 3);
	public static final Align CENTER_RIGHT = new Align(1, 3);
	public static final Align VBURST_RIGHT = new Align(2, 3);
	public static final Align BOTTOM_RIGHT = new Align(3, 3);

	// Different Horizontal direction
	public boolean left = false;
	public boolean right = false;
	public boolean h_center = false;
	public boolean h_burst_center = false;
	// Different Vertical direction
	public boolean top = false;
	public boolean bottom = false;
	public boolean v_center = false;
	public boolean v_burst_center = false;

	public Align(int vertical, int horizontal)
	{
		// Set the vertical and horizontal directions as separate values

		if (horizontal == 0)
			left = true;
		if (horizontal == 1)
			h_center = true;
		if (horizontal == 2)
			h_burst_center = true;
		if (horizontal == 3)
			right = true;

		if (vertical == 0)
			top = true;
		if (vertical == 1)
			v_center = true;
		if (vertical == 2)
			v_burst_center = true;
		if (vertical == 3)
			bottom = true;
	}

	public Vector2f toVector2f()
	{
		Vector2f result = new Vector2f();

		if (top)
			result.setY(1);
		if (bottom)
			result.setY(-1);
		if (v_center || v_burst_center)
			result.setY(0);

		if (left)
			result.setX(-1);
		if (right)
			result.setX(1);
		if (h_center || h_burst_center)
			result.setX(0);

		return result;
	}

	/**
	 * Parse a string value into an Align object (vertical-horizontal format)
	 */
	public static Align parseAlignment(String src)
	{
		Align result = null;
		switch (src)
		{
			///////////////////////////////////////////////
			case "top-left":
				result = TOP_LEFT;
				break;

			case "top-center":
				result = TOP_CENTER;
				break;

			case "top-hburst":
				result = TOP_HBURST;
				break;

			case "top-right":
				result = TOP_RIGHT;
				break;

			/////////////////////////////////////
			case "center-left":
				result = CENTER_LEFT;
				break;

			case "center":
				result = CENTER;
				break;

			case "center-hburst":
				result = CENTER_HBURST;
				break;

			case "center-right":
				result = CENTER_RIGHT;
				break;

			/////////////////////////////////////
			case "vburst-left":
				result = VBURST_LEFT;
				break;

			case "vburst-center":
				result = VBURST_CENTER;
				break;

			case "burst":
				result = VBURST_HBURST;
				break;

			case "vburst-right":
				result = VBURST_RIGHT;
				break;

			/////////////////////////////////////
			case "bottom-left":
				result = BOTTOM_LEFT;
				break;

			case "bottom-center":
				result = BOTTOM_CENTER;
				break;

			case "bottom-hburst":
				result = BOTTOM_HBURST;
				break;

			case "bottom-right":
				result = BOTTOM_RIGHT;
				break;
		}

		if (result == null)
		{
			System.err.println("Align-Format Exception : " + src + " is not a valid input");
			return CENTER;
		}

		return result;
	}
}
