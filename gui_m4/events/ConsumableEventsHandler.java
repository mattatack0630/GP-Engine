package gui_m4.events;

import input.InputManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthew on 6/28/17.
 */
public class ConsumableEventsHandler
{
    private Map<Class<? extends ConsumableEvent>, ConsumableEvent> consumableEventMap;
    private InputManager inputManager;

    public ConsumableEventsHandler(InputManager inputManager){
        this.inputManager = inputManager;
        this.consumableEventMap = new HashMap<>();
    }

    public void add(ConsumableEvent event)
    {
        consumableEventMap.put(event.getClass(), event);
    }

    public void consume(ConsumableEvent event)
    {
        consumableEventMap.remove(event.getClass());
    }

    public <E extends ConsumableEvent> E getEvent(Class<E> classType)
    {
        return (E) consumableEventMap.get(classType);
    }

    public void updateEvents()
    {
        consumableEventMap.clear();

        ConsumableKeyboardEvent keyboardEvent  = new ConsumableKeyboardEvent(
                inputManager.getKeysDown(),
                inputManager.getKeysClicked(),
                inputManager.getKeysReleased()
        );

        ConsumableMouseEvent mouseEvent = new ConsumableMouseEvent(
                inputManager.getGLCursorCoords(),
                inputManager.getMouseDown(),
                inputManager.getMouseReleasing(),
                inputManager.getMouseClicked());

        ConsumableScrollEvent scrollEvent = new ConsumableScrollEvent(
                inputManager.getMouseScrollDelta());

        consumableEventMap.put(ConsumableKeyboardEvent.class, keyboardEvent);
        consumableEventMap.put(ConsumableScrollEvent.class, scrollEvent);
        consumableEventMap.put(ConsumableMouseEvent.class, mouseEvent);
    }
}
