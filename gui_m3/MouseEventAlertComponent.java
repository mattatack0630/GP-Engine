package gui_m3;

import engine.Engine;
import input.InputManager;
import rendering.renderers.MasterRenderer;

/**
 * Created by matthew on 6/8/17.
 */
public class MouseEventAlertComponent extends ElementComponent
{
    public static final Event PUSHING = new Event("PUSHING", -11234, 0);
    public static final Event RELEASING = new Event("RELEASING", -11235, 1);
    public static final Event SCROLLING = new Event("SCROLLING", -11236, 2);
    public static final Event CLICKING = new Event("CLICKING", -11237, 3);

    @Override
    public void onTick()
    {
        InputManager manager = Engine.getInputManager();
        if (manager.getMouseReleasing().size() > 0) parentElement.pushEvent(RELEASING);
        if (manager.getMouseClicked().size() > 0) parentElement.pushEvent(CLICKING);
        if (manager.getMouseDown().size() > 0) parentElement.pushEvent(PUSHING);
        if (manager.getMouseScrollDelta() != 0) parentElement.pushEvent(SCROLLING);
    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

    }

    @Override
    public void onEvent(Event event)
    {

    }

    @Override
    public void onBuild()
    {

    }

    @Override
    public void postBuild()
    {

    }
}
