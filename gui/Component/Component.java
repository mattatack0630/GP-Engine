package gui.Component;

import gui.Align;
import gui.CSS.Identifier;
import gui.Component.Content.Content;
import gui.Component.Panel.HPanel;
import gui.Component.Panel.Panel;
import gui.Component.Panel.VPanel;
import gui.InteractiveComponents.InteractiveBase;
import gui.Text.TextAttributes;
import gui.XML.XMLNode;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;

/**
 * The Component Class
 * This Class is used to Display content on the screen.
 * Has various attributes used to display the content differently.
 * Also Stores data used to position the elements.
 * */
public class Component extends InteractiveBase
{
	public String printableName;

	public Identifier identifier;

	public boolean shouldInheritSizeX;
	public boolean shouldInheritSizeY;
	public boolean hasUpdatedDimensions;

	public Panel parent;
	public Content content;

	public Vector2f relativePos;
	public Vector2f absolutePos;

	public Vector4f margin; // l t r b
	public Vector4f padding; // l t r b

	public Vector2f minSize;
	public Vector2f maxSize;
	public Vector2f prefSize;
	public Vector2f absoluteSize;

	public Align alignment;

	// For rendering a box vaoObject
	public float opacity;
	public Color backgroundColor;
	public Color marginColor;
	public Color paddingColor;
	public Color contentColor;

	public Component(Vector2f relativePos)
	{
		super(relativePos, new Vector2f());

		this.identifier = new Identifier();
		// Give a random id to begin with
		this.identifier.addIdName("" + (Math.random() * 1000 + 1000));

		this.hasUpdatedDimensions = false;
		this.shouldInheritSizeX = false;
		this.shouldInheritSizeY = false;

		this.relativePos = new Vector2f(relativePos);
		this.absolutePos = new Vector2f(relativePos);
		this.absoluteSize = new Vector2f();

		this.prefSize = new Vector2f();
		this.maxSize = null;
		this.minSize = null;

		this.margin = new Vector4f(0.01f, 0.01f, 0.01f, 0.01f);
		this.alignment = Align.CENTER;

		this.minSize = new Vector2f(0, 0);
		this.maxSize = new Vector2f(100, 100);

		opacity = 1.0f;
		marginColor = Color.NONE;
		paddingColor = Color.NONE;
		contentColor = Color.NONE;
		backgroundColor = Color.NONE;

		String[] packageName = this.getClass().toString().split("(\\.)| ");
		String className = (packageName.length > 0) ? packageName[packageName.length - 1] : "";
		printableName = className + "@" + hashCode() / 100000;
	}

	/**
	 * Set the parent panel
	 *
	 * @param parent the parent panel
	 */
	public void setParent(Panel parent)
	{
		this.parent = parent;
	}

	/**
	 * Set the inner content this Component contains
	 *
	 * @param content the content
	 */
	public void setContent(Content content)
	{
		this.content = content;
		this.content.setParent(this);
		calculateSize(content.size);

		this.content.updateDimension(absolutePos, absoluteSize);

		super.updateInteractive(absolutePos, absoluteSize);
	}

	public void updatePosition(Vector2f updatedPos)
	{
		absolutePos = new Vector2f(updatedPos);

		if (content != null)
		{
			content.updateDimension(absolutePos, absoluteSize);
			content.update();
		}
		super.updateInteractive(absolutePos, absoluteSize);
	}

	public void calculateSize(Vector2f prefSize)
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
	}

	// Find out what this is used for
	public void updateContent()
	{
		if (content == null)
			return;

		content.update();

		prefSize = content.getSize();
		calculateSize(prefSize);

		hasUpdatedDimensions = true;
	}

	public void tick()
	{
		super.tick();

		if (content != null)
			content.tick();
	}

	public void render(MasterRenderer renderer)
	{
		Trinket2D.drawRect(absolutePos, absoluteSize, backgroundColor, opacity);

		if (content != null)
			content.render(renderer);
	}

	public Vector2f getSizeWithMargin()
	{
		return new Vector2f(absoluteSize.x() + margin.x() + margin.z(), absoluteSize.y() + margin.y() + margin.w());
	}

	public boolean getHasUpdatedDimensions()
	{
		boolean result = hasUpdatedDimensions;
		hasUpdatedDimensions = false;
		return result;
	}

	public void setId(String id)
	{
		this.identifier.addIdName(id);
	}

	public void addClassName(String name)
	{
		this.identifier.addClassName(name);
	}

	public void calculateFinalSize()
	{
		calculateSize(prefSize);
	}

	public List<Component> getChildren()
	{
		return new ArrayList<>();
	}

	public String toString()
	{
		String s = "";

		s += (parent == null ? "Root@1234" : parent.printableName) + " => " + printableName;

		return s;
	}

	/**XML Stuff*/
	public void applyAttrib(String name, String value)
	{
		switch (name)
		{
			case "id":
				identifier.addIdName(value);
				break;

			case "classname":
				identifier.addClassName(value);
				break;

			case "alignment":
				alignment = Align.parseAlignment(value);
				break;

			case "width":
				if (value.toLowerCase().equals("fillparent"))
					this.shouldInheritSizeX = true;
				else if (content != null)
					content.size.setX(Float.parseFloat(value));
				break;
			case "height":
				if (value.toLowerCase().equals("fillparent"))
					this.shouldInheritSizeY = true;
				else if (content != null)
					content.size.setY(Float.parseFloat(value));
				break;
			case "minwidth":
				if (value.toLowerCase().equals("fillparent"))
					this.shouldInheritSizeX = true;
				else minSize.setX(Float.parseFloat(value));
				break;
			case "minheight":
				if (value.toLowerCase().equals("fillparent"))
					this.shouldInheritSizeY = true;
				else minSize.setY(Float.parseFloat(value));
				break;
			case "maxwidth":
				maxSize.setX(Float.parseFloat(value));
				break;
			case "maxheight":
				maxSize.setY(Float.parseFloat(value));
				break;
			case "margin":
				margin = (Vector4f) ExtraUtils.parseVector(value);
				break;
			case "backgroundcolor":
				backgroundColor = new Color(value);
				break;
			case "image":

				break;
		}
	}

	public void applyAttribList(ArrayList<String> attribList)
	{
		for (String attrib : attribList)
		{
			String[] data = attrib.split("=");
			if (data.length < 2)
				continue;

			String name = data[0].toLowerCase();
			String value = ExtraUtils.stripChars(data[1], '"');

			applyAttrib(name, value);
		}

		if (content != null)
		{
			content.updateDimension(content.position, content.size);
			this.calculateSize(content.size);
		}
	}

	/**
	 * Make a component based off a XML Node, type and attributes
	 */
	public static Component fromXML(XMLNode node)
	{
		Component c = null;

		switch (node.type.toLowerCase())
		{
			case "panel":
				c = new Panel(new Vector2f());
				break;

			case "hpanel":
				c = new HPanel();
				break;

			case "vpanel":
				c = new VPanel();
				break;

			case "animatedtexture":
				c = new AnimatedTextureComponent();
				break;

			case "button":
				c = new ButtonComponent(new Vector2f());
				break;

			case "checkbox":
				c = new CheckBoxComponent();
				break;

			case "component":
				c = new Component(new Vector2f());
				break;

			case "label":
				c = new LabelComponent("","Arial", new TextAttributes());
				break;

			case "radio":
				c = new RadioComponent();
				break;

			case "slider":
				c = new SliderComponent(new Vector2f());
				break;

			case "textinput":
				c = new TextInputComponent();
				break;

			case "texture":
				//c = new TextureComponent();
				break;

			default:
				c = new Component(new Vector2f());
				break;
		}

		c.updateContent();

		return c;
	}
}