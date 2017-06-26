package gui_m3;

/**
 * Created by matthew on 6/6/17.
 */
public class KeyboardEvent
{
    public static final Event DOWN = new Event("KEY_DOWN", 123, 0);
    public static final Event CLICK = new Event("KEY_CLICK", 124, 2);
    public static final Event RELEASE = new Event("KEY_RELEASE",  125, 1);
}
