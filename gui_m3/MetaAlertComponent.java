package gui_m3;

import rendering.renderers.MasterRenderer;

/**
 * Created by matthew on 6/12/17.
 */
public class MetaAlertComponent extends ElementComponent
{
    public static final Event FIRST_BUILD = new Event("first_build", 12234, 0);
    public static final Event ANY_BUILD = new Event("any_build", 12235, 0);

    public boolean builtBefore;

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

    }

    @Override
    public void onBuild()
    {
        if(!builtBefore) parentElement.pushEvent(FIRST_BUILD);

        parentElement.pushEvent(ANY_BUILD);

        builtBefore = true;
    }

    @Override
    public void postBuild()
    {

    }
}
