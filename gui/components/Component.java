package gui.components;

import gui.Identifier;
import gui.interactive.InteractiveBase;
import parsing.gxml.GXMLParsable;
import parsing.gxml.ParseAction;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.Map;

/**
 * Created by mjmcc on 3/17/2017.
 */
public abstract class Component extends InteractiveBase implements GXMLParsable
{
	public Identifier identifier;

	public boolean shouldInheritSizeX;
	public boolean shouldInheritSizeY;
	public boolean hasUpdatedDimensions;

	public Vector4f clippingBounds;
	public float renderLevel;

	public Panel parent;

	public Vector2f relativePos;
	public Vector2f absolutePos;

	// l t r b
	public Vector4f margin;
	public Vector4f padding;

	public Vector2f minSize;
	public Vector2f maxSize;
	public Vector2f prefSize;
	public Vector2f absoluteSize;
	public Vector2f boundingSize;

	public Color backgroundColor;
	public Color paddingColor;
	public Color contentColor;
	public Color marginColor;
	public float opacity;

	public Component(Vector2f position)
	{
		super(position, new Vector2f());

		// Give a random id to begin with
		this.identifier = new Identifier();
		this.identifier.addIdName("" + (Math.random() * 1000 + 1000));

		this.hasUpdatedDimensions = false;
		this.shouldInheritSizeX = false;
		this.shouldInheritSizeY = false;

		this.relativePos = new Vector2f(position);
		this.absolutePos = new Vector2f(relativePos);
		this.absoluteSize = new Vector2f();

		this.prefSize = new Vector2f();
		this.maxSize = null;
		this.minSize = null;

		this.margin = new Vector4f(0.0f);

		this.minSize = new Vector2f(0, 0);
		this.maxSize = new Vector2f(100, 100);

		this.renderLevel = 0;
		this.clippingBounds = new Vector4f(Trinket2D.FULL_SCREEN_BOUNDS);

		opacity = 1.0f;
		marginColor = new Color(Color.NONE);
		paddingColor = new Color(Color.NONE);
		contentColor = new Color(Color.NONE);
		backgroundColor = new Color(Color.NONE);
	}

	public void tick()
	{
		super.tick();
		tickContent();
	}

	public void render(MasterRenderer renderer)
	{
		Trinket2D.setClippingBounds(clippingBounds);

		Trinket2D.setRenderLevel(renderLevel + 0.0f);
		if (marginColor.getA() != 0.0)
		{
			Trinket2D.setDrawColor(new Color(marginColor.getR(), marginColor.getG(),
					marginColor.getB(), opacity * marginColor.getA()));
			Trinket2D.drawRectangle(absolutePos, boundingSize);
		}

		Trinket2D.setRenderLevel(renderLevel + 0.1f);
		if (backgroundColor.getA() != 0.0)
		{
			Trinket2D.setDrawColor(new Color(backgroundColor.getR(), backgroundColor.getG(),
					backgroundColor.getB(), opacity * backgroundColor.getA()));
			Trinket2D.drawRectangle(absolutePos, absoluteSize);
		}

		Trinket2D.setClippingBounds(Trinket2D.FULL_SCREEN_BOUNDS);
		renderContent();
	}

	public void build()
	{
		// Calculate new absolute size based on content
		calculateSize(getContentSize());
		// Update the interactive base area
		super.updateInteractive(absolutePos, absoluteSize);
		// set the bounding size (includes margin)
		boundingSize = new Vector2f(absoluteSize.x() + margin.x() + margin.z(), absoluteSize.y() + margin.y() + margin.w());
		// set the render levels, to one above parent
		if (parent != null) renderLevel = parent.renderLevel + 1;
		// resynchronize Content
		syncContent();
	}

	public Vector2f calculateSize(Vector2f size)
	{
		this.prefSize = new Vector2f(size);
		this.absoluteSize = new Vector2f(prefSize);

		if (absoluteSize.x() < minSize.x())
			absoluteSize.setX(minSize.x());
		if (absoluteSize.y() < minSize.y())
			absoluteSize.setY(minSize.y());
		if (absoluteSize.x() > maxSize.x())
			absoluteSize.setX(maxSize.x());
		if (absoluteSize.y() > maxSize.y())
			absoluteSize.setY(maxSize.y());

		return absoluteSize;
	}

	public void setPosition(Vector2f pos)
	{
		this.absolutePos = new Vector2f(pos);
		build();
	}

	public abstract void tickContent();

	public abstract void renderContent();

	public abstract void syncContent();

	public abstract Vector2f getContentSize();

	@Override
	public void addToParsingFuncMap(Map<String, ParseAction> parseMap)
	{
		parseMap.put("id", (c, value) -> c.identifier.addIdName(value));

		parseMap.put("class", (c, value) -> {
			String[] classNames = value.split(",");
			for(String name : classNames)
				c.identifier.addClassName(name);
		});

		parseMap.put("max-width", (c, value) -> c.maxSize.setX(Float.parseFloat(value)));

		parseMap.put("max-height", (c, value) -> c.maxSize.setY(Float.parseFloat(value)));

		parseMap.put("renderlevel", (c, value) -> c.renderLevel = Integer.parseInt(value));

		parseMap.put("backgroundcolor", (c, value) -> c.backgroundColor = new Color(value));

		parseMap.put("margin", (c, value) -> c.margin = (Vector4f) ExtraUtils.parseVector(value));

		parseMap.put("min-width", (c, value) -> {
			if (value.toLowerCase().equals("fillparent"))
				c.shouldInheritSizeX = true;
			else c.minSize.setX(Float.parseFloat(value));
		});

		parseMap.put("min-height", (c, value) -> {
			if (value.toLowerCase().equals("fillparent"))
				c.shouldInheritSizeY = true;
			else c.minSize.setY(Float.parseFloat(value));
		});
	}


	public void addClassName(String name)
	{
		identifier.addClassName(name);
	}
}
