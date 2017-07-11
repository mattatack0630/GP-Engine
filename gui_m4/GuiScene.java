package gui_m4;

import engine.Engine;
import gui_m4.elements.GuiElement;
import gui_m4.events.ConsumableEventsHandler;
import rendering.renderers.MasterRenderer;

import java.util.*;

/**
 * Created by matthew on 6/28/17.
 */
public class GuiScene
{
    // private StyleApplicator
    private List<GuiElement> elementStack;
    private List<GuiElement> rootElements;
    private GuiRenderStack renderableStack;
    private GuiElementLocator elementLocator;
    private ConsumableEventsHandler eventHandler;

    private GuiScene()
    {
        this.eventHandler = new ConsumableEventsHandler(Engine.getInputManager());
        this.elementLocator = new GuiElementLocator(elementStack);
        this.renderableStack = new GuiRenderStack(0);
        this.elementStack = new LinkedList<>();
        this.rootElements = new LinkedList<>();
    }

    /**
     * Tick method is called on all elements in the layer
     * sorted stack. This way, the program can pass a Consumable
     * Event object, and allow events to be consumed by the nearest
     * relevant element. (ei. only once element can be clicked)
     */
    public void tick()
    {
        eventHandler.updateEvents();

        ListIterator<GuiElement> listIterator = elementStack.listIterator(elementStack.size());

        while (listIterator.hasPrevious())
        {
            GuiElement element = listIterator.previous();
            element.tick(eventHandler);
        }

    }

    /**
     * Render method is called on all elements in the
     * layer sorted stack. This way, the program doesnt need to recalculate the
     * GuiElements render order ever frame, only once per build().
     **/
    public void render(MasterRenderer renderer)
    {
        for (GuiElement element : elementStack)
            element.render(renderableStack);

        // Set layer to the first roots layer
        renderableStack.setLayer(rootElements.size() > 0 ? rootElements.get(0).getLayer() : 0);
        // Send to renderer
        renderer.processGuiRenderableStack(renderableStack.getRenderables(), renderableStack.getLayer());
        // clear stack and set layer to 0
        renderableStack.resetStack();
    }

    /**
     * Building is only called on the root elements becuase most elements
     * like containers are better off using a tree like recursion
     * method. That way, they can build their children elements and
     * calculate their sizes using that information.
     * <p>
     * Therefore, the build method within elements, as well as the
     * post build method call the corresponding methods on their child
     * elements.
     */
    public void build()
    {
        for (GuiElement element : rootElements)
            element.build();

        for (GuiElement element : rootElements)
            element.postBuild();

        // restructure the overall element stack
        resetElementStack();
        // reset locators reference to the list
        elementLocator.resetElementReference(elementStack);
    }

    public void addRootElement(GuiElement rootElement)
    {

        rootElements.add(rootElement);
    }

    /* ***** Used to sort elements UNIMPLEMENTED ******* */
    private static final Comparator<GuiElement> elementComparator = (o1, o2) ->
    {
        float layer1 = o1.getLayer();
        float later2 = o2.getLayer();
        if (layer1 < later2) return +1;
        if (layer1 > later2) return -1;
        return 0;
    };

    // Receive and sort elements in stack
    private void resetElementStack()
    {
        elementStack.clear();
        sortIntoStack(rootElements);
    }

    // Recursive sort function
    private void sortIntoStack(List<GuiElement> elements)
    {
        for (GuiElement element : elements)
        {
            elementStack.add(element);
            //Sorter.dynamicSort(elementStack, element, elementComparator);
            sortIntoStack(element.getChildren());
        }
    }

    // An element locating object for this scene
    public GuiElementLocator getElementLocator()
    {

        return elementLocator;
    }

    // Apply a style to this scenes decedents
    public void applyStyle(GuiStyle style)
    {

        style.apply(elementStack);
    }

    /* ** Factories / Generators ** */
    public static GuiScene fromRootElement(GuiElement rootElement)
    {
        GuiScene scene = new GuiScene();
        scene.addRootElement(rootElement);
        scene.resetElementStack();

        return scene;
    }

    public static GuiScene fromSourceFile(String fileName)
    {
        GuiElement rootElement = SXMLParser.parse3(fileName);
        return fromRootElement(rootElement);
    }
}
