package gui_m4.events;

import utils.math.linear.vector.Vector2f;

import java.util.List;

/**
 * Created by matthew on 6/28/17.
 */
public class ConsumableMouseEvent extends ConsumableEvent
{
    private Vector2f location;
    private List<Integer> mouseDown;
    private List<Integer> mouseReleased;
    private List<Integer> mouseBeganPress;

    public ConsumableMouseEvent(Vector2f location, List<Integer> mouseDown, List<Integer> mouseReleased, List<Integer> mouseBeganPress)
    {
        this.location = location;
        this.mouseDown = mouseDown;
        this.mouseReleased = mouseReleased;
        this.mouseBeganPress = mouseBeganPress;
    }

    public Vector2f getLocation()
    {
        return location;
    }

    public List<Integer> getMouseButtonsDown()
    {
        return mouseDown;
    }

    public List<Integer> getMouseButtonsReleased()
    {
        return mouseReleased;
    }

    public List<Integer> getMouseButtonsClicked()
    {
        return mouseBeganPress;
    }
}
