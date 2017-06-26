package gui_m3;

import rendering.Color;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.*;

/**
 * Created by matthew on 5/31/17.
 * <p>
 * TODO Disable some elements like render/tick/etc for "dull" elements (like textures or labels)
 */
public class GuiElement
{
    private static final Vector4f SCREEN_BOUNDS = new Vector4f(-1, -1, 1, 1);
    
    // The builder used to make this element (or none if manual)
    private GuiElementBuilder elementBuilder;

    // box format
    private Vector4f margin;
    private Vector4f padding;

    // Sizes
    private Vector2f elementSize;
    private Vector2f boundingSize;

    private GuiSize minSize;
    private GuiSize maxSize;
    private GuiSize contentSize;

    // relative ^ size flag (for containers wrapping)
    private boolean usesRelativeSizes;

    // Positions
    private Vector2f relativePosition;
    private Vector2f absolutePosition;

    // Parent Element (default screen)
    private GuiElement parentElement;

    // Gui Layer for render/ events
    private int layer;
    private boolean setLayer;
    private Vector4f clippingBounds;

    // Identifications/ Accessors
    private String id;
    private List<String> classes;

    // Events/ States Queue
    private PriorityQueue<Event> events;

    // Component-Based Architecture for logic/rendering
    private Map<Class, ElementComponent> components;

    public GuiElement()
    {
        this.margin = new Vector4f();
        this.padding = new Vector4f();

        this.minSize = new GuiSize("0vp", "0vp");
        this.maxSize = new GuiSize("100vp", "100vp");
        this.contentSize = new GuiSize("0vp", "0vp");
        this.usesRelativeSizes = false;

        this.elementSize = new Vector2f();
        this.boundingSize = new Vector2f();

        this.relativePosition = new Vector2f();
        this.absolutePosition = new Vector2f();

        this.parentElement = null;
        this.layer = 0;
        this.clippingBounds = new Vector4f(SCREEN_BOUNDS);

        this.id = String.valueOf(hashCode());
        this.classes = new ArrayList<>();

        this.events = new PriorityQueue<>();
        this.components = new HashMap<>();
    }

    ///////////////////////////////////////////////////////////////////////////

    public void tick()
    {
        for (Class c : components.keySet())
        {
            ElementComponent elementComponent = components.get(c);
            elementComponent.onTick();
        }

        resolveEvents();
    }

    public void render(MasterRenderer renderer)
    {
        for (Class c : components.keySet())
        {
            ElementComponent elementComponent = components.get(c);
            elementComponent.onRender(renderer);
        }
    }

    public void build()
    {
        for (Class c : components.keySet())
        {
            ElementComponent elementComponent = components.get(c);
            elementComponent.onBuild();
        }

        calculateSizes();
        calculatePositions();
        calculateClippingBounds();

        postBuild();
    }

    protected void postBuild()
    {
        // Maybe move later, this may work
        for (Class c : components.keySet())
        {
            ElementComponent elementComponent = components.get(c);
            elementComponent.postBuild();
        }
    }

    public void resolveEvents()
    {
        while (!events.isEmpty())
        {
            Event event = events.poll();

            for (Class c : components.keySet())
            {
                ElementComponent elementComponent = components.get(c);
                elementComponent.onEvent(event);
            }
        }
    }

    public void pushEvent(Event e)
    {

        events.add(e);
    }

    protected void calculateClippingBounds()
    {
        if (parentElement != null)
        {
            Vector2f pos = parentElement.getAbsolutePosition();
            Vector2f size = parentElement.getElementSize();
            clippingBounds.setX(pos.x() - size.x() / 2.0f);
            clippingBounds.setY(pos.y() - size.y() / 2.0f);
            clippingBounds.setZ(pos.x() + size.x() / 2.0f);
            clippingBounds.setW(pos.y() + size.y() / 2.0f);
        } else
        {
            clippingBounds.set(SCREEN_BOUNDS);
        }
    }

    protected void calculatePositions()
    {
        // After parent is built // remove later when screen parent is added
        if (parentElement != null)
        {
            Vector2f.add(relativePosition, parentElement.absolutePosition, absolutePosition);
        } else
        {
            absolutePosition.set(relativePosition);
        }
    }

    protected void calculateSizes()
    {
        // After parent is built
        Vector2f minSizeR = minSize.resolve(this);
        Vector2f maxSizeR = maxSize.resolve(this);
        Vector2f contentSizeR = contentSize.resolve(this);

        elementSize.set(contentSizeR);

        if (elementSize.x() < minSizeR.x())
            elementSize.setX(minSizeR.x());
        if (elementSize.y() < minSizeR.y())
            elementSize.setY(minSizeR.y());
        if (elementSize.x() > maxSizeR.x())
            elementSize.setX(maxSizeR.x());
        if (elementSize.y() > maxSizeR.y())
            elementSize.setY(maxSizeR.y());

        boundingSize.setX(elementSize.x() + margin.x() + margin.z());
        boundingSize.setY(elementSize.y() + margin.y() + margin.w());
    }

    ////////////////////////////////////////////////////////////////////////////

    public void setContentSize(Vector2f contentSize)
    {
        this.contentSize = new GuiSize(contentSize.x() + "vp", contentSize.y() + "vp");
        updateUsesRelativeSize();
    }

    public void setContentSize(GuiSize contentSize)
    {
        this.contentSize = contentSize;
        updateUsesRelativeSize();
    }

    public void setMinSize(Vector2f minSize)
    {
        this.minSize = new GuiSize(minSize.x() + "vp", minSize.y() + "vp");
        updateUsesRelativeSize();
    }

    public void setMaxSize(Vector2f maxSize)
    {
        this.maxSize = new GuiSize(maxSize.x() + "vp", maxSize.y() + "vp");
        updateUsesRelativeSize();
    }

    public void setMinSize(GuiSize minSize)
    {
        this.minSize = minSize;
        updateUsesRelativeSize();
    }

    public void setMaxSize(GuiSize maxSize)
    {
        this.maxSize = maxSize;
        updateUsesRelativeSize();
    }

    public void setRelativePosition(Vector2f relativePosition)
    {
        this.relativePosition = relativePosition;
    }

    public void setParentElement(GuiElement parentElement)
    {
        this.parentElement = parentElement;
    }

    public void setPadding(Vector4f padding)
    {
        this.padding = padding;
    }

    public boolean hasId(String idName)
    {
        return this.id.equals(idName);
    }

    public void setMargin(Vector4f margin)
    {
        this.margin = margin;
    }

    public void setLayer(int layer)
    {
        this.layer = layer;
        this.setLayer = true;
    }


    public <E extends ElementComponent> E getComponent(Class className)
    {
        ElementComponent ec = components.get(className);
        return ec == null ? null : (E) ec;
    }

    public Map<Class, ElementComponent> getComponents()
    {
        return components;
    }

    public Vector2f getRelativePosition()
    {
        return relativePosition;
    }

    public Vector2f getAbsolutePosition()
    {
        return absolutePosition;
    }

    public boolean usesRelativeSizes()
    {
        return usesRelativeSizes;
    }

    public Vector4f getClippingBounds()
    {
        return clippingBounds;
    }

    public GuiElement getParentElement()
    {
        return parentElement;
    }

    public Vector2f getBoundingSize()
    {
        return boundingSize;
    }

    public Vector2f getElementSize()
    {
        return elementSize;
    }

    public void clearComponents()
    {
        components.clear();
    }

    public Vector4f getPadding()
    {
        return padding;
    }

    public GuiSize getMinSize()
    {
        return minSize;
    }

    public GuiSize getMaxSize()
    {
        return maxSize;
    }

    public Vector4f getMargin()
    {
        return margin;
    }

    public int getLayer()
    {
        if(!setLayer)
            return parentElement == null ? 0 : parentElement.getLayer();
        return layer;
    }


    public void addComponent(ElementComponent component)
    {
        components.put(component.getClass(), component);
        component.setParent(this);
    }

    public boolean hasClass(String className)
    {
        for (String cn : classes)
            if (cn.equals(className))
                return true;

        return false;
    }

    public void updateUsesRelativeSize()
    {
        usesRelativeSizes = contentSize.isRelative() || minSize.isRelative() || maxSize.isRelative();
    }

    public void addClass(String className)
    {
        this.classes.add(className);
    }

    public void setId(String id)
    {
        this.id = id;
    }

}
