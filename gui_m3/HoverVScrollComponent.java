package gui_m3;

import engine.Engine;
import rendering.renderers.MasterRenderer;

/**
 * Created by matthew on 6/1/17.
 *
 * Adds Scroll on hover ability to the Scroll Panel
 */
public class HoverVScrollComponent extends ElementComponent
{
    private static final float DEF_SCROLL_SPEED = 0.1f;
    private ScrollComponent scrollComponent;
    private float scrollSpeed;

    public HoverVScrollComponent(ScrollComponent scrollComponent)
    {
        this.scrollSpeed = DEF_SCROLL_SPEED;
        this.scrollComponent = scrollComponent;
    }

    @Override
    public void onTick()
    {

    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

    }

    @Override
    public void onEvent(Event event)
    {

        if(event == MouseInteractionEvent.HOVER)
        {
            float dy = Engine.getInputManager().getMouseScrollDelta();

            if(dy != 0)
            {
                scrollComponent.setScrollY(scrollComponent.getScrollVal().y() - (dy * scrollSpeed));
            }
        }

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
