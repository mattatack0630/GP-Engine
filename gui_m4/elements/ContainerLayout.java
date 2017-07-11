package gui_m4.elements;

import gui_m4.Align;
import utils.math.linear.vector.Vector2f;

import java.util.List;

/**
 * Created by matthew on 6/26/17.
 */
public abstract class ContainerLayout
{
    protected GuiElement containerEl;
    protected List<GuiElement> childrenEl;
    protected Align containerAlignment;

    public ContainerLayout()
    {
    }

    public void setChildrenEl(List<GuiElement> childrenEl)
    {
        this.childrenEl = childrenEl;
    }

    public void setContainerEl(GuiElement containerEl)
    {
        this.containerEl = containerEl;
    }

    public void setContainerAlignment(Align alignment)
    {
        this.containerAlignment = alignment;
    }

    public abstract void reset();

    public abstract Vector2f place();

}
