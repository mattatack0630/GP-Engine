package gui_m3;

import gui.Align;
import utils.math.linear.vector.Vector2f;

import java.util.List;

/**
 * Created by matthew on 5/31/17.
 */
public abstract class ContainerLayout
{
    protected GuiElement containerEl;
    protected List<GuiElement> childrenEl;

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

    public abstract void reset();
    public abstract Vector2f place();

}
