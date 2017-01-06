package gui;

import gui.CSS.Style;
import gui.Component.Component;
import gui.Component.Panel.Panel;
import gui.Transition.Transition;
import gui.XML.XMLParser;
import rendering.renderers.MasterRenderer;
import utils.ExtraUtils;
import utils.math.linear.vector.Vector2f;

import java.util.ArrayList;

/**
 * Created by mjmcc on 10/11/2016.
 */
public class GuiScene
{
	private String id;
	private boolean visible;
	private boolean isRemovable;
	protected ArrayList<Panel> directChildren;
	public Vector2f size;
	public Vector2f position;

	public GuiScene()
	{
		id = "";
		visible = false;
		isRemovable = false;
		directChildren = new ArrayList<>();
		size = new Vector2f();
		position = new Vector2f();
	}

	public GuiScene(String srcXml)
	{
		this();
		directChildren.addAll(XMLParser.parseXMLFromFile(srcXml).getDirectChildren());
		build();
		show();
	}

	/**
	 * Add a panel to the scene
	 *
	 * @param p the Panel to add to the scene
	 */
	public void addPanel(Panel p)
	{
		if (!directChildren.contains(p))
			directChildren.add(p);
	}

	public void addPanels(ArrayList<Panel> panels)
	{
		for (Panel p : panels)
			addPanel(p);
	}

	/**
	 * Set the position and of this scene
	 *
	 * @param p the new position to set the scene to
	 */
	public void setPosition(Vector2f p)
	{
		position = new Vector2f(p);

		if (directChildren.isEmpty())
			return;

		for (Panel panel : directChildren)
			panel.updatePosition(p);
	}

	public void setOpacity(float opacity)
	{
		ArrayList<Component> c = getAllChildren();
		for (Component component : c)
			component.opacity = opacity;
	}

	/**
	 * Apply a transition to this entire scene.
	 * Move, Fade, Spin, ect
	 *
	 * @t the transition to apply
	 */
	public void applyTransition(Transition t)
	{
		t.applyTransition(this);
	}

	/**
	 * Get the root panel
	 */
	public Panel getRoot()
	{
		if (directChildren.isEmpty())
			return new Panel(new Vector2f());

		return directChildren.get(0);
	}

	// Show the scene. Tick and Render
	public void show()
	{
		visible = true;
	}

	// Hide the scene. Tick and Render
	public void hide()
	{
		visible = false;
	}

	/**
	 * Tick the panels in this scene.  Which tick their child components
	 */
	public void tick()
	{
		if (!visible)
			return;

		boolean changed = false;

		// Check if any of the direct panels has added more children
		for (Panel p : directChildren)
		{
			p.tick();
			changed = changed || p.getHasUpdatedDimensions();
		}

		if (changed)
			build();
	}

	/**
	 * Render the scene's components and panels.
	 *
	 * @param renderer the MasterRenderer to use
	 */
	public void render(MasterRenderer renderer)
	{
		////if (!visible)
		//	return;

		for (Panel p : directChildren)
			p.render(renderer);
	}

	/**
	 * Apply CSS styles to the scene. Not working right now
	 */
	public void applyCSS(ArrayList<Style> sheet)
	{
		ArrayList<Component> components = getAllChildren();

		for (Component c : components)
		{
			Style overallStyle = new Style("");
			for (Style style : sheet)
			{
				if (c.identifier.isTypeOf(style.applyToIdentifier))
				{
				}
			}
		}
	}

	/**
	 * Get all of the components in this scene, children first structure
	 */
	public ArrayList<Component> getAllChildren()
	{
		ArrayList<Component> allChildren = new ArrayList<>();

		for (Panel p : directChildren)
		{
			// Get all children (deep)
			allChildren.addAll(p.getChildren());
			allChildren.add(p);
		}
		return allChildren;
	}

	/**
	 * Get a list of components that have this class name
	 */
	public ArrayList<Component> getChildrenByClassName(String className)
	{
		ArrayList<Component> components = getAllChildren();
		ArrayList<Component> result = new ArrayList<>();

		for (Component c : components)
			if (c.identifier.isInClass("#" + className))
				result.add(c);

		// Change later
		if (result.size() == 0)
			System.out.println("There is no child with the Class : " + className);

		return result;
	}

	/**
	 * Get a component in this scene with this id
	 */
	public Component getChildById(String id)
	{
		ArrayList<Component> components = getAllChildren();

		for (Component c : components)
			if (c.identifier.hasId("." + id))
				return c;

		// Change later
		System.out.println("There is no child with the ID : " + id);
		return null;
	}

	/**
	 * Rebuild the scene. Replace the components and recalculate sizes
	 */
	public void build()
	{
		// Get every component (children first)
		ArrayList<Component> components = getAllChildren();
		// Calculate the size based on just prefSize and min/max
		for (Component c : components)
			c.calculateFinalSize();

		// Calculate fillParent sizes
		for (Component c : components)
		{
			if (c instanceof Panel)
			{
				((Panel) c).calculateFillParent();
				((Panel) c).calculateComponentsSize();
			}
		}
		// Get the panels to place their components (Children first)
		for (Component c : components)
			if (c instanceof Panel)
				((Panel) c).placeComponents();

		calculateSize();
	}

	public void calculateSize()
	{
		Vector2f min = new Vector2f(1000, 1000);
		Vector2f max = new Vector2f(-1000, -1000);

		for (Panel p : directChildren)
		{
			Vector2f pMin = Vector2f.sub(p.absolutePos, new Vector2f(p.absoluteSize.x() / 2f, p.absoluteSize.y() / 2f), null);
			Vector2f pMax = Vector2f.add(p.absolutePos, new Vector2f(p.absoluteSize.x() / 2f, p.absoluteSize.y() / 2f), null);
			min.setX((pMin.x() < min.x()) ? pMin.x() : min.x());
			min.setY((pMin.y() < min.y()) ? pMin.y() : min.y());
			max.setX((pMax.x() > max.x()) ? pMax.x() : max.x());
			max.setY((pMax.y() > max.y()) ? pMax.y() : max.y());
		}

		size = Vector2f.sub(max, min, null);
	}

	public void attachNewSource(String srcPath)
	{
		directChildren.clear();
		directChildren.addAll(XMLParser.parseXML(ExtraUtils.getFileAsString(srcPath)).directChildren);
		build();
	}

	/**
	 * Methods called by the GuiManager when an action is
	 * submitted on a GuiScene
	 */
	public void onScreenPlace()
	{
	} // When the gui is added to a screen

	public void onScreenRemove()
	{
	} // When the gui is removed from the screen

	public void onScreenClose()
	{
	} // When the screen this gui is on becomes hidden

	public void onScreenOpen()
	{
	} // When the screen this gui is on becomes shown

	/**
	 * Return whether or not this guiScene should be removed
	 */
	public boolean isRemovable()
	{
		return isRemovable;
	}

	public void setRemovable(boolean removable)
	{
		this.isRemovable = removable;
	}

	public String getId()
	{
		return id;
	}

	public ArrayList<Panel> getDirectChildren()
	{
		return directChildren;
	}
}
