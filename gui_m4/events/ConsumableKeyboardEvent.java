package gui_m4.events;

import utils.math.linear.vector.Vector2f;

import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 7/6/17.
 */
public class ConsumableKeyboardEvent extends ConsumableEvent
{
    private Map<Integer, Character> keysDown;
    private Map<Integer, Character> KeysClicked;
    private Map<Integer, Character> keysReleased;

    public ConsumableKeyboardEvent(Map<Integer, Character> keysDown, Map<Integer, Character> keysClicked, Map<Integer, Character> keysReleased)
    {
        this.keysDown = keysDown;
        KeysClicked = keysClicked;
        this.keysReleased = keysReleased;
    }

    public Map<Integer, Character> getKeysDown()
    {
        return keysDown;
    }

    public Map<Integer, Character> getKeysClicked()
    {
        return KeysClicked;
    }

    public Map<Integer, Character> getKeysReleased()
    {
        return keysReleased;
    }
}
