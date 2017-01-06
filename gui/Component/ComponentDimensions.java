package gui.Component;

import com.sun.istack.internal.Nullable;
import javafx.scene.Parent;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 10/6/2016.
 */
public class ComponentDimensions
{
	public Vector2f relativePos;
	public Vector2f absolutePos;

	public Vector4f margin; // l t r b
	public Vector4f padding; // l t r b

	public Vector2f minSize;
	public Vector2f maxSize;
	public Vector2f prefSize;

	public Vector2f absoluteSize;
	public Vector2f absoluteSizeMargin;
	public Vector2f absoluteSizeHalf;

	public Vector2f minPoint;
	public Vector2f maxPoint;

	public ComponentDimensions(Vector2f relativePos, Vector2f prefSize)
	{
		this.margin = new Vector4f();
		this.padding = new Vector4f();

		this.minSize  = new Vector2f();
		this.prefSize = new Vector2f(prefSize);
		this.maxSize  = new Vector2f(100, 100);
		this.absoluteSize = new Vector2f();
		this.absoluteSizeMargin = new Vector2f();

		this.absolutePos = new Vector2f();
		this.relativePos = new Vector2f(relativePos);

		this.minPoint = new Vector2f();
		this.maxPoint = new Vector2f();
	}

	public void updatePos(Vector2f relativePos, @Nullable Parent parent)
	{
		maxPoint = new Vector2f(absolutePos.x() + (absoluteSize.x() / 2f) + margin.z(),
				absolutePos.y() - (absoluteSize.y() / 2f) - margin.w());
		minPoint = new Vector2f(absolutePos.x() - (absoluteSize.x() / 2f) - margin.x(),
				absolutePos.y() + (absoluteSize.y() / 2f) + margin.y());
	}

	public void updateSize(Vector2f prefSize)
	{
		this.prefSize = new Vector2f(prefSize);
		this.absoluteSize = new Vector2f(prefSize);

		if (absoluteSize.x() < minSize.x())
			absoluteSize.setX(minSize.x());
		if (absoluteSize.y() < minSize.y())
			absoluteSize.setY(minSize.y());
		if (absoluteSize.x() > maxSize.x())
			absoluteSize.setX(maxSize.x());
		if (absoluteSize.y() > maxSize.y())
			absoluteSize.setY(maxSize.y());

		this.absoluteSizeMargin = new Vector2f(absoluteSize.x() + margin.x() + margin.z(), absoluteSize.y() + margin.y() + margin.w());
	}

	public void setMinSize(Vector2f min)
	{
		this.minSize = new Vector2f(min);
	}

	public void setMaxSize(Vector2f max)
	{
		this.maxSize = new Vector2f(max);
	}

	public void setMargin(float left, float right, float top, float bottom)
	{
		this.margin.set(left, top, right, bottom);
	}
}
