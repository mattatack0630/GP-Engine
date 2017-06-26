package gui_m3;

import rendering.renderers.MasterRenderer;

/**
 * Created by matthew on 5/31/17.
 */
public abstract class ElementComponent
{
    public GuiElement parentElement;

    public void setParent(GuiElement element)
    {
        this.parentElement = element;
    }

    public abstract void onTick();

    public abstract void onRender(MasterRenderer renderer);

    public abstract void onEvent(Event event);

    public abstract void onBuild();

    public abstract void postBuild(); // syncing content
}
