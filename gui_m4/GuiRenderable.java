package gui_m4;

import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 4/5/2017.
 */
public class GuiRenderable implements Comparable
{
	public Vector4f clippingBounds;
	public float renderLevel;
	public int type;

	public GuiRenderable(int type)
	{
		this.type = type;
		this.renderLevel = 0;
		this.clippingBounds = new Vector4f(-1, -1, 1, 1);
	}

	@Override
	public int compareTo(Object o)
	{
		if (o instanceof GuiRenderable)
		{
			GuiRenderable other = (GuiRenderable) o;

			float thisRL = this.renderLevel;
			float otherRL = other.renderLevel;

			if (otherRL > thisRL)
				return -1;
			if (otherRL < thisRL)
				return 1;
		}

		return 0;
	}

	public int getType()
	{

		return type;
	}

	public float getRenderLevel()
	{

		return renderLevel;
	}

	public void setRenderLevel(float renderLevel)
	{

		this.renderLevel = renderLevel;
	}

	public void setClippingBounds(Vector4f bounds)
	{

		this.clippingBounds.set(bounds.x(), bounds.y(), bounds.z(), bounds.w());
	}

	public void setClippingBounds(Vector2f min, Vector2f max)
	{

		this.clippingBounds.set(min.x(), min.y(), max.x(), max.y());
	}

	public void setClippingMin(Vector2f min)
	{
		this.clippingBounds.setX(min.x());
		this.clippingBounds.setY(min.y());
	}

	public void setClippingMax(Vector2f max)
	{
		this.clippingBounds.setZ(max.x());
		this.clippingBounds.setW(max.y());
	}

	public Vector4f getClippingBounds()
	{
		return clippingBounds;
	}
}
