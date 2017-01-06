package gui.Component.Panel;

import gui.Component.Component;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/5/2016.
 */
public class VPanel extends Panel
{
	public VPanel()
	{
		super(new Vector2f());
	}

	@Override
	public void placeComponents()
	{
		super.placeComponents();

		/*X Left Positioning*/
		if (alignment.left)
			for (Component c : components)
				c.relativePos.setX((-(absoluteSize.x()) / 2f) + (c.absoluteSize.x() / 2f) + c.margin.x());

		/*X Right Positioning*/
		if (alignment.right)
			for (Component c : components)
				c.relativePos.setX(((absoluteSize.x()) / 2f) - (c.absoluteSize.x() / 2f) - c.margin.z());

		/*X Center Positioning*/
		if (alignment.h_center)
			for (Component c : components)
				c.relativePos.setX(((c.margin.x()) - c.margin.z()) / 2);


		/*Y Top Positioning*/
		if (alignment.top)
		{
			float overY = absoluteSize.y() / 2;
			for (Component c : components)
			{
				c.relativePos.setY(overY - (c.absoluteSize.y() / 2f) - c.margin.y());
				overY -= (c.absoluteSize.y() + c.margin.y() + c.margin.w());
			}
		}

		/*Y Bottom Positioning*/
		if (alignment.bottom)
		{
			float overY = -absoluteSize.y() / 2;
			for (int i = components.size() - 1; i >= 0; i--)
			{
				Component c = components.get(i);
				c.relativePos.setY(overY + (c.absoluteSize.y() / 2f) + c.margin.w());
				overY += (c.absoluteSize.y() + c.margin.y() + c.margin.w());
			}
		}

		/*Y Center Positioning*/
		if (alignment.v_center)
		{
			float overY = absoluteSize.y() / 2;
			for (Component c : components)
			{
				c.relativePos.setY(overY - (c.absoluteSize.y() / 2f) - c.margin.y());
				overY -= (c.absoluteSize.y() + c.margin.y() + c.margin.w());
			}

			float centerOver = (-absoluteSize.y() / 2) + Math.abs(overY - absoluteSize.y() / 2) / 2;
			for (Component c : components)
				c.relativePos.setY(c.relativePos.y() + centerOver);
		}


		if (alignment.v_burst_center)
		{
			Vector2f autoMargin = calculateAutoMargin();

			float overY = absoluteSize.y() / 2f - autoMargin.y();
			for (Component c : components)
			{
				c.relativePos.setY(overY - ((c.absoluteSize.y() / 2f) + c.margin.w()));
				overY -= (c.absoluteSize.y() + c.margin.y() + c.margin.w() + autoMargin.y() * 2);
			}
		}

		for (Component c : components)
			c.updatePosition(Vector2f.add(c.relativePos, absolutePos, null));
	}

	@Override
	public void calculateFinalSize()
	{
		Vector2f opSize = calculateOptimalSize();
		prefSize.set(maxComponentSizes.x(), opSize.y());
		calculateSize(prefSize);
	}

	@Override
	public void calculateComponentsSize()
	{
		Vector2f fillSize = fillParentAmount;
		for (Component c : components)
		{
			if (c.shouldInheritSizeY)
				c.minSize.setY(c.absoluteSize.y() + fillSize.y());
			if (c.shouldInheritSizeX)
				c.minSize.setX(absoluteSize.x() - (c.margin.x() + c.margin.z()));
			c.calculateSize(c.prefSize);
		}
	}
}