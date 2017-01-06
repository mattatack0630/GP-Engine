package gui.Component.Panel;

import gui.Component.Component;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/5/2016.
 */
public class HPanel extends Panel
{
	public HPanel()
	{
		super(new Vector2f());
	}

	@Override
	public void placeComponents()
	{
		super.placeComponents();

		/*X Left Positioning*/
		if (alignment.left)
		{
			float overX = -absoluteSize.x() / 2f;
			for (Component c : components)
			{
				c.relativePos.setX(overX + (c.absoluteSize.x() / 2f) + c.margin.x());
				overX += (c.absoluteSize.x() + c.margin.x() + c.margin.z());
			}
		}

		/*X Right Positioning*/
		if (alignment.right)
		{
			float overX = absoluteSize.x() / 2f;
			for (int i = components.size() - 1; i >= 0; i--)
			{
				Component c = components.get(i);
				c.relativePos.setX(overX - (c.absoluteSize.x() / 2f) - c.margin.z());
				overX -= (c.absoluteSize.x() + c.margin.x() + c.margin.z());
			}
		}

		/*X Center Positioning*/
		if (alignment.h_center)
		{
			float overX = -absoluteSize.x() / 2f;
			for (Component c : components)
			{
				c.relativePos.setX(overX + (c.absoluteSize.x() / 2f) + c.margin.x());
				overX += (c.absoluteSize.x() + c.margin.x() + c.margin.z());
			}

			float centerOver = (absoluteSize.x() - (overX + absoluteSize.x() / 2f)) / 2f;
			for (Component c : components)
				c.relativePos.setX(c.relativePos.x() + centerOver);
		}

		if (alignment.h_burst_center)
		{
			Vector2f autoMargin = calculateAutoMargin();

			float overX = -absoluteSize.x() / 2f + autoMargin.x();
			for (Component c : components)
			{
				c.relativePos.setX(overX + (c.absoluteSize.x() / 2f) + c.margin.x());
				overX += (c.absoluteSize.x() + c.margin.x() + c.margin.z() + autoMargin.x() * 2);
			}
		}

		/*Y Top Positioning*/
		if (alignment.top)
			for (Component c : components)
				c.relativePos.setY((absoluteSize.y() / 2f) - (c.absoluteSize.y() / 2f) - c.margin.y());

		/*Y Bottom Positioning*/
		if (alignment.bottom)
			for (Component c : components)
				c.relativePos.setY(-(absoluteSize.y() / 2f) + (c.absoluteSize.y() / 2f) + c.margin.w());

		/*Y Center Positioning*/
		if (alignment.v_center)
			for (Component c : components)
				c.relativePos.setY(-(c.margin.y() - c.margin.w()) / 2);


		for (Component c : components)
			c.updatePosition(Vector2f.add(c.relativePos, absolutePos, null));
	}

	@Override
	public void calculateFinalSize()
	{
		Vector2f opSize = calculateOptimalSize();
		prefSize.set(opSize.x(), maxComponentSizes.y());
		calculateSize(prefSize);
	}

	@Override
	public void calculateComponentsSize()
	{
		Vector2f fillSize = fillParentAmount;
		for (Component c : components)
		{
			// Setting minSize may be incorrect
			if (c.shouldInheritSizeX)
				c.minSize.setX(c.absoluteSize.x() + fillSize.x());
			if (c.shouldInheritSizeY)
				c.minSize.setY(absoluteSize.y() - (c.margin.y() + c.margin.w()));
			c.calculateSize(c.prefSize);
		}
	}
}
