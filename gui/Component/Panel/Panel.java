package gui.Component.Panel;

import gui.Component.Component;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Panel extends Component
{
	protected List<Component> components;
	protected Vector2f maxComponentSizes;
	protected Vector2f fillParentAmount;

	public Panel(Vector2f position)
	{
		super(position);
		components = new ArrayList<>();
		maxComponentSizes = new Vector2f();
		fillParentAmount = new Vector2f();
	}

	public void addChild(Component child)
	{
		child.setParent(this);
		components.add(child);
		hasUpdatedDimensions = true;
	}

	public void placeComponents()
	{
		updateInteractive(absolutePos, absoluteSize);
	}

	public void tick()
	{
		super.tick();

		for (Component component : components)
		{
			component.tick();
			hasUpdatedDimensions = hasUpdatedDimensions || component.getHasUpdatedDimensions();
		}
	}

	public void render(MasterRenderer renderer)
	{
		super.render(renderer);

		for (Component component : components)
			component.render(renderer);
	}

	public void updatePosition(Vector2f updatedPos)
	{
		super.updatePosition(updatedPos);

		for (Component c : components)
			c.updatePosition(Vector2f.add(c.relativePos, absolutePos, null));
	}

	@Override
	public List<Component> getChildren()
	{
		ArrayList<Component> allChildren = new ArrayList<>();

		// Add all of children's children
		for (Component c : components)
		{
			// Specifically add the children first
			// (Maybe make a different method to get parent first)
			allChildren.addAll(c.getChildren());
			allChildren.add(c);
		}

		return allChildren;
	}

	// Used to make the burst alignment work
	protected Vector2f calculateAutoMargin()
	{
		Vector2f spaceLeft = new Vector2f();
		Vector2f componentSpace = new Vector2f();

		for (Component c : components)
			componentSpace.add(c.getSizeWithMargin());
		//Vector2f.add(componentSpace, c.getSizeWithMargin(), componentSpace);

		Vector2f.sub(absoluteSize, componentSpace, spaceLeft);
		spaceLeft.scale(1.0f / components.size());
		spaceLeft.scale(0.5f);

		return spaceLeft;
	}

	// Calculate the diagonal that can fit every component
	public Vector2f calculateOptimalSize()
	{
		Vector2f opSize = new Vector2f();

		prefSize.set(0, 0);
		maxComponentSizes.set(0, 0);
		for (Component c : components)
		{
			Vector2f childMarginAbsolute = c.getSizeWithMargin();
			maxComponentSizes.setX((childMarginAbsolute.x() > maxComponentSizes.x()) ?
					childMarginAbsolute.x() : maxComponentSizes.x());
			maxComponentSizes.setY((childMarginAbsolute.y() > maxComponentSizes.y()) ?
					childMarginAbsolute.y() : maxComponentSizes.y());

			Vector2f.add(opSize, childMarginAbsolute, opSize);
		}

		return opSize;
	}

	// Calculate the fill amount to apply to inherited components sizes
	public void calculateFillParent()
	{
		Vector2f spaceLeft = new Vector2f();
		Vector2f componentSpace = new Vector2f();
		Vector2f fillUsers = new Vector2f();

		for (Component c : components)
		{
			if (c.shouldInheritSizeX)
				fillUsers.setX(fillUsers.x() + 1);
			if (c.shouldInheritSizeY)
				fillUsers.setY(fillUsers.y() + 1);

			//Vector2f.add(componentSpace, c.getSizeWithMargin(), componentSpace);
			componentSpace.add(c.getSizeWithMargin());
		}
		Vector2f.sub(absoluteSize, componentSpace, spaceLeft);

		spaceLeft.setX(spaceLeft.x() * 1f / fillUsers.x());
		spaceLeft.setY(spaceLeft.y() * 1f / fillUsers.y());
		spaceLeft.setX(Math.max(spaceLeft.x(), 0));
		spaceLeft.setY(Math.max(spaceLeft.y(), 0));

		fillParentAmount = new Vector2f(spaceLeft);
	}

	public void calculateComponentsSize()
	{
	}

	@Override
	public void applyAttrib(String name, String value)
	{
		super.applyAttrib(name, value);
		switch (name)
		{
			case "maxchildcount":

				break;
		}
	}

	public String toString()
	{
		String s = "";
		s += super.toString() + "\n";

		for (Component c : components)
			s += c + "\n";

		return s;
	}
}
