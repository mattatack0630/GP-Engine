package gui_m3;

import engine.Engine;
import input.InputManager;
import rendering.renderers.MasterRenderer;

/**
 * Created by matthew on 6/6/17.
 */
public class KeyboardEventAlertComponent extends ElementComponent
{
    @Override
    public void onTick()
    {
        InputManager manager = Engine.getInputManager();
        if(manager.getKeyCount() > 0) {
            boolean keysDown = (manager.getKeysDown().size() > 0);
            boolean keysClicked = (manager.getKeysClicked().size() > 0);
            boolean keysReleased = (manager.getKeysReleased().size() > 0);
            if(keysDown) parentElement.pushEvent(KeyboardEvent.DOWN);
            if(keysClicked) parentElement.pushEvent(KeyboardEvent.CLICK);
            if(keysReleased) parentElement.pushEvent(KeyboardEvent.RELEASE);
        }
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
