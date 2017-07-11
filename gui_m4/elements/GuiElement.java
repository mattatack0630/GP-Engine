package gui_m4.elements;

import gui_m4.*;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.props.*;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.*;

/**
 * Created by matthew on 5/31/17.
 */
public abstract class GuiElement
{
    // Default clipping bounds (Set to the screens/windows bounds)
    private static final Vector4f SCREEN_BOUNDS = new Vector4f(-1, -1, 1, 1);

    // Properties which can be parsed by css or xml
    private HashMap<String, GuiProperty> parsableProperties;
    private List<UpdateListener> updateListeners;

    // box format
    private Vec4GuiProperty margin;
    private Vec4GuiProperty padding;

    // Sizes
    private SizeGuiProperty minSize;
    private SizeGuiProperty maxSize;
    private SizeGuiProperty contentSize;

    private Vec2GuiProperty elementSize;
    private Vec2GuiProperty boundingSize;

    // Positions
    private Vec2GuiProperty relativePosition;
    private Vec2GuiProperty absolutePosition;

    // relative ^ size flag (for containers wrapping)
    private boolean usesRelativeSizes;

    // Parent Element (default screen)
    protected GuiElement parentElement;
    private List<GuiElement> childrenElements;

    // Bounds in which to render this element
    private Vector4f clippingBounds;

    // Identifications/ Accessors
    private StringGuiProperty id;
    private StringListGuiProperty classes;

    public GuiElement()
    {
        this.updateListeners = new ArrayList<>();

        this.margin = new Vec4GuiProperty(new Vector4f(), "MARGIN");
        this.padding = new Vec4GuiProperty(new Vector4f(), "PADDING");

        this.minSize = new SizeGuiProperty(new GuiSize("0vp", "0vp"), "MIN_SIZE");
        this.maxSize = new SizeGuiProperty(new GuiSize("100vp", "100vp"), "MAX_SIZE");
        this.contentSize = new SizeGuiProperty(new GuiSize("0vp", "0vp"), "CONTENT_SIZE");
        this.usesRelativeSizes = false;

        this.elementSize = new Vec2GuiProperty(new Vector2f(), "ELEMENT_SIZE_OBSERVE_ONLY");
        this.boundingSize = new Vec2GuiProperty(new Vector2f(), "BOUNDING_SIZE_OBSERVE_ONLY");

        this.relativePosition = new Vec2GuiProperty(new Vector2f(), "RELATIVE_POSITION");
        this.absolutePosition = new Vec2GuiProperty(new Vector2f(), "ABS_POSITION_OBSERVE_ONLY");

        this.classes = new StringListGuiProperty(new ArrayList<>(), "CLASS");
        this.id = new StringGuiProperty(String.valueOf(hashCode()), "ID");

        this.parentElement = null;
        this.childrenElements = new ArrayList<>();
        this.clippingBounds = new Vector4f(SCREEN_BOUNDS);

        // update relative size flags
        ChangeListener updateRel = (property, oldValue, newValue) -> updateUsesRelativeSize();
        this.contentSize.addChangeListener(updateRel);
        this.minSize.addChangeListener(updateRel);
        this.maxSize.addChangeListener(updateRel);

        // init element and properties
        initialize();

        // add properties to a HashMap
        this.parsableProperties = new HashMap<>();
        addParsableProperties(this.parsableProperties);
        addBaseParsableProperties(this.parsableProperties);

        // apply the default skin
        GuiStyle defaultStyle = getDefaultStyle();
        defaultStyle.apply(this);
    }

    /* *************************************************************************
    * These are used to get the parable properties used to generate element from
    * xml and css sheets
    * **************************************************************************/

    protected abstract GuiStyle getDefaultStyle();

    protected abstract GuiElementBuilder getBuilder();

    protected abstract void addParsableProperties(Map<String, GuiProperty> map);

    protected void addBaseParsableProperties(Map<String, GuiProperty> map)
    {
        map.put(margin.getPropertyName(), margin);
        map.put(minSize.getPropertyName(), minSize);
        map.put(maxSize.getPropertyName(), maxSize);
        map.put(contentSize.getPropertyName(), contentSize);
        map.put(relativePosition.getPropertyName(), relativePosition);
        map.put(classes.getPropertyName(), classes);
        map.put(id.getPropertyName(), id);
    }

    /* *************************************************************************
    * These are used to implement element specific functions that will be called
    * by the regular functions "onTickImp() --called_by--> tick()"
    * **************************************************************************/

    protected abstract void initialize();

    protected abstract void onBuildImp();

    protected abstract void onPostBuildImp();

    protected abstract void onRenderImp(GuiRenderStack renderer);

    protected abstract void onTickImp(ConsumableEventsHandler eventsHandler);

    /* *************************************************************************
    * These are called by the programmer and used to
    * run the method on all of the elements children and call its implementation
    * method ^^
    * **************************************************************************/

    public void tick(ConsumableEventsHandler eventsHandler)
    {
        onTickImp(eventsHandler);

        for(UpdateListener updateListener : updateListeners)
            updateListener.onUpdate();
    }

    public void render(GuiRenderStack renderer)
    {
        onRenderImp(renderer);

        // Debugging
        //Trinket2D.setDrawColor(Color.RED);
        //Trinket2D.drawRectangle(getAbsolutePosition(), getBoundingSize());
    }

    public void build()
    {
        onBuildImp();

        calculateSizes();

        for (GuiElement element : childrenElements)
            element.build();
    }

    public void postBuild()
    {
        calculateSizes();
        calculatePositions();
        calculateClippingBounds();

        onPostBuildImp();

        for (GuiElement element : childrenElements)
            element.postBuild();
    }

    /*************************************************************************/

    protected void calculateClippingBounds()
    {
        // Possible clipping bounds input

        if (parentElement != null)
        {
            Vector4f cb = parentElement.getClippingBounds();
            clippingBounds.set(cb);
        } else
        {
            Vector2f pos = getAbsolutePosition();
            Vector2f size = getElementSize();

            clippingBounds.setX(pos.x() - size.x() / 2.0f);
            clippingBounds.setY(pos.y() - size.y() / 2.0f);
            clippingBounds.setZ(pos.x() + size.x() / 2.0f);
            clippingBounds.setW(pos.y() + size.y() / 2.0f);
        }
    }

    protected void calculatePositions()
    {
        Vector2f relativePosition = this.relativePosition.getPropertyValue();
        Vector2f absolutePosition = this.absolutePosition.getPropertyValue();

        // After parent is built // remove later when screen parent is added
        if (parentElement != null)
        {
            Vector2f.add(relativePosition, parentElement.getAbsolutePosition(), absolutePosition);
        } else
        {
            absolutePosition.set(relativePosition);
        }

        // to update the position's hasChanged value
        this.relativePosition.setPropertyValue(relativePosition);
        this.absolutePosition.setPropertyValue(absolutePosition);
    }

    protected void calculateSizes()
    {
        // After parent is built
        Vector2f minSizeR = minSize.getPropertyValue().resolve(this);
        Vector2f maxSizeR = maxSize.getPropertyValue().resolve(this);
        Vector2f contentSizeR = contentSize.getPropertyValue().resolve(this);

        Vector2f elementSize = this.elementSize.getPropertyValue();
        Vector2f boundingSize = this.boundingSize.getPropertyValue();
        Vector4f margin = this.margin.getPropertyValue();

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

        // to update the sizes' hasChanged value
        this.boundingSize.setPropertyValue(boundingSize);
        this.elementSize.setPropertyValue(elementSize);
    }

    /**************************************************************************/

    public void setRelativePosition(Vector2f relativePosition)
    {
        Vector2f mutRelPos = this.relativePosition.getPropertyValue();
        mutRelPos.set(relativePosition);

        // update hasChanged value
        this.relativePosition.setPropertyValue(mutRelPos);
    }

    public void setParentElement(GuiElement parentElement)
    {

        this.parentElement = parentElement;
    }

    public void setContentSize(Vector2f contentSize)
    {
        this.contentSize.setPropertyValue(new GuiSize(contentSize.x() + "vp", contentSize.y() + "vp"));
    }

    public void setContentSize(GuiSize contentSize)
    {

        this.contentSize.setPropertyValue(contentSize);
    }

    public void setMinSize(Vector2f minSize)
    {
        this.minSize.setPropertyValue(new GuiSize(minSize.x() + "vp", minSize.y() + "vp"));
    }

    public void setMaxSize(Vector2f maxSize)
    {
        this.maxSize.setPropertyValue(new GuiSize(maxSize.x() + "vp", maxSize.y() + "vp"));
    }

    public void setMinSize(GuiSize minSize)
    {

        this.minSize.setPropertyValue(minSize);
    }

    public void setMaxSize(GuiSize maxSize)
    {

        this.maxSize.setPropertyValue(maxSize);
    }

    public void setPadding(Vector4f padding)
    {
        Vector4f mutPadding = this.padding.getPropertyValue();
        mutPadding.set(padding);

        // update hasChanged value
        this.padding.setPropertyValue(padding);
    }

    public void setMargin(Vector4f margin)
    {
        Vector4f mutMargin = this.margin.getPropertyValue();
        mutMargin.set(margin);

        // update hasChanged value
        this.margin.setPropertyValue(mutMargin);
    }

    public void setId(String id)
    {

        this.id.setPropertyValue(id);
    }

    /***************************************************************************/

    public Vector2f getRelativePosition()
    {
        return relativePosition.getPropertyValue();
    }

    public Vector2f getAbsolutePosition()
    {
        return absolutePosition.getPropertyValue();
    }

    public Vector2f getBoundingSize()
    {
        return boundingSize.getPropertyValue();
    }

    public Vector2f getElementSize()
    {
        return elementSize.getPropertyValue();
    }

    public Vector4f getPadding()
    {
        return padding.getPropertyValue();
    }

    public GuiSize getMinSize()
    {
        return minSize.getPropertyValue();
    }

    public GuiSize getMaxSize()
    {
        return maxSize.getPropertyValue();
    }

    public Vector4f getMargin()
    {
        return margin.getPropertyValue();
    }

    public boolean usesRelativeSizes()
    {
        return usesRelativeSizes;
    }

    public GuiElement getParentElement()
    {
        return parentElement;
    }

    public Vector4f getClippingBounds()
    {
        return clippingBounds;
    }

    public String getId()
    {
        return id.getPropertyValue();
    }

    public float getLayer()
    {
        return 0.0f;
    }

    public boolean hasClass(String cName)
    {
        List<String> classes = this.classes.getPropertyValue();

        for (String cn : classes)
            if (cn.equals(cName))
                return true;

        return false;
    }

    public String getElementTypeName()
    {
        GuiElementBuilder builder = getBuilder();
        return builder != null ? builder.getElementName() : getClass().getSimpleName();
    }

    /***************************************************************************/

    public void addUpdateListener(UpdateListener listener)
    {

        updateListeners.add(listener);
    }

    public HashMap<String, GuiProperty> getProperties()
    {

        return parsableProperties;
    }

    public void addChild(GuiElement child)
    {
        childrenElements.add(child);
        child.setParentElement(this);
    }

    public List<GuiElement> getChildren()
    {

        return childrenElements;
    }

    public void addClass(String className)
    {
        List<String> classes = this.classes.getPropertyValue();
        classes.add(className);

        // update hasChanged value
        this.classes.setPropertyValue(classes);
    }

    private void updateUsesRelativeSize()
    {
        GuiSize contentSize = this.contentSize.getPropertyValue();
        GuiSize minSize = this.minSize.getPropertyValue();
        GuiSize maxSize = this.maxSize.getPropertyValue();

        usesRelativeSizes = contentSize.isRelative() || minSize.isRelative() || maxSize.isRelative();
    }

    public String toString()
    {
        String id = this.id.getPropertyValue();
        return this.getClass().getSimpleName() + "-> id:[" + id + "] layer:[" + getLayer() + "]" + " hash:[" + hashCode() + "]";
    }
}
