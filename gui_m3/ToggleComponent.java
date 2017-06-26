package gui_m3;

import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 6/12/17.
 */
public class ToggleComponent extends ElementComponent
{
    public static final Event TOGGLE_TRG_EVENT = MouseInteractionEvent.CLICK;
    public static final Event TOGGLE_STATE_EVENT = new Event("toggle_state_change", 13452, 2);
    private static final GuiElement NULL_EL = new GuiElement();

    private ContainerComponent parentContainer;
    private List<GuiElement> toggleEls;
    private int toggleVal;

    public ToggleComponent(ContainerComponent parentContainer)
    {
        this.parentContainer = parentContainer;
        this.toggleEls = new ArrayList<>();
        this.toggleVal = 0;
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

        if(event.equals(MetaAlertComponent.FIRST_BUILD))
        {
            List<GuiElement> children = parentContainer.getChildren();
            toggleEls.addAll(children);
            parentContainer.clearChildren();
            parentContainer.addChild(toggleEls.isEmpty() ? NULL_EL: toggleEls.get(toggleVal));

            if(children.isEmpty())
            {
                System.out.println("Toggle Button "+this+" has no state children");
            }
        }

        if(event.equals(TOGGLE_TRG_EVENT))
        {
            toggleVal = (toggleVal + 1) % toggleEls.size();

            parentContainer.clearChildren();
            parentContainer.addChild(toggleEls.isEmpty() ? NULL_EL: toggleEls.get(toggleVal));

            parentElement.pushEvent(TOGGLE_STATE_EVENT);
            parentElement.build();
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

    public int getToggleVal()
    {
        return toggleVal;
    }
}
