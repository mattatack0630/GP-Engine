package gui_m3;

import parsing.gxml.SXMLParser;
import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 6/9/17.
 */
public class GuiScene
{
    private GuiElement rootElement;

    public GuiScene(String srcFile)
    {
        this.rootElement = SXMLParser.parse2(srcFile);
    }

    public GuiScene(GuiElement root)
    {
        this.rootElement = root;
    }

    public void render(MasterRenderer renderer)
    {
        rootElement.render(renderer);
    }

    public void tick()
    {
        rootElement.tick();
    }

    public void build()
    {
        rootElement.build();
    }

    public GuiElement getRoot()
    {
        return rootElement;
    }

    public GuiElement getById(String id)
    {
        ContainerComponent containerComponent = rootElement.getComponent(ContainerComponent.class);

        if (rootElement.hasId(id))
            return rootElement;
        else if (containerComponent != null)
            return getById(id, rootElement.getComponent(ContainerComponent.class));
        else
            return null;
    }

    private GuiElement getById(String id, ContainerComponent container)
    {
        for (GuiElement child : container.getChildren())
        {
            // checking
            if (child.hasId(id))
                return child;

            // recursion
            ContainerComponent childContainer = child.getComponent(ContainerComponent.class);
            if (childContainer != null)
            {
                GuiElement result = getById(id, childContainer);
                if (result != null)
                    return result;
            }
        }

        return null;
    }

    public List<GuiElement> getByClass(String className)
    {
        List<GuiElement> elements = new ArrayList<>();
        ContainerComponent containerComponent = rootElement.getComponent(ContainerComponent.class);

        if (rootElement.hasClass(className))
            elements.add(rootElement);

        if (containerComponent != null)
            getByClass(className, containerComponent, elements);

        return elements;
    }

    private void getByClass(String className, ContainerComponent container, List<GuiElement> classList)
    {
        for (GuiElement child : container.getChildren())
        {
            // checking
            if (child.hasClass(className))
                classList.add(child);

            // recursion
            ContainerComponent childContainer = child.getComponent(ContainerComponent.class);
            if (childContainer != null)
                getByClass(className, childContainer, classList);
        }
    }

}
