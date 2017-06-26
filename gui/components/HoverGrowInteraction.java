package gui.components;

import engine.Engine;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 4/18/2017.
 */
public class HoverGrowInteraction extends ComponentInteraction
{
	private Component component;
	private float distPerSec;
	private float time;
	private Vector2f max;
	private Vector2f min;

	public HoverGrowInteraction(Component component, Vector2f min, Vector2f max, float dps)
	{
		this.time = 0;
		this.distPerSec = dps;
		this.min = new Vector2f(min);
		this.max = new Vector2f(max);
		this.component = component;
	}

	public HoverGrowInteraction(Component component, float gain, float dps)
	{
		this.time = 0;
		this.min = new Vector2f(component.absoluteSize);
		this.max = new Vector2f(min.x() + gain, min.y() + gain);
		this.distPerSec = dps;

		this.component = component;
	}

	public void tick()
	{
		if (component.isEntered())
			time = Engine.getTime();

		if (component.isExited())
			time = Engine.getTime();

		if (component.isHovered())
		{
			float dt = distPerSec * (Engine.getTime() - time);
			float agx = Maths.min(component.absoluteSize.x() + dt, max.x());
			float agy = Maths.min(component.absoluteSize.y() + dt, max.y());
			component.absoluteSize.setX(agx);
			component.absoluteSize.setY(agy);
			time = Engine.getTime();
		}

		if (component.isStatic())
		{
			float dt = distPerSec * (Engine.getTime() - time);
			float agx = Maths.max(component.absoluteSize.x() - dt, min.x());
			float agy = Maths.max(component.absoluteSize.y() - dt, min.y());
			component.absoluteSize.setX(agx);
			component.absoluteSize.setY(agy);
			time = Engine.getTime();
		}
	}
}
