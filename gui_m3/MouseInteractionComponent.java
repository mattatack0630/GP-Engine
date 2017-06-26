package gui_m3;

import gui.interactive.ClickChecker;
import gui.interactive.Clickable;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 5/31/17.
 */
public class MouseInteractionComponent extends ElementComponent implements Clickable
{
    private ClickChecker checker;

    public MouseInteractionComponent()
    {
        checker = new ClickChecker(0, 0, 0, 0, this);
    }

    @Override
    public void onTick()
    {
        checker.checkClick();
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

        Vector2f size = parentElement.getElementSize();
        Vector2f pos = parentElement.getAbsolutePosition();
        checker.setClickDimensions(pos.x(), pos.y(), size.x() / 2.0f, size.y() / 2.0f);
    }

    @Override
    public void onClick()
    {
        parentElement.pushEvent(MouseInteractionEvent.CLICK);
    }

    @Override
    public void onRelease()
    {
        parentElement.pushEvent(MouseInteractionEvent.RELEASE);
    }

    @Override
    public void onHold()
    {
        parentElement.pushEvent(MouseInteractionEvent.HOLD);
    }

    @Override
    public void onHover()
    {
        parentElement.pushEvent(MouseInteractionEvent.HOVER);
    }

    @Override
    public void onBeginHover()
    {
        parentElement.pushEvent(MouseInteractionEvent.ENTER);
    }

    @Override
    public void onEndHover()
    {
        parentElement.pushEvent(MouseInteractionEvent.EXIT);
    }
}
