package gui_m4.events;

/**
 * Created by matthew on 6/28/17.
 */
public class ConsumableScrollEvent extends ConsumableEvent
{
    private float scrollVal;

    public ConsumableScrollEvent(float scrollVal)
    {
        this.scrollVal = scrollVal;
    }

    public float getScrollVal()
    {
        return scrollVal;
    }
}
