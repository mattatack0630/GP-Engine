package gui;

/**
 * Created by matthew on 5/28/17.
 */
public class ParsableBooleanValue<E extends Boolean> extends ParsableValue
{
    private E number;

    public ParsableBooleanValue(E init)
    {
        this.number = init;
    }

    @Override
    public E getValue()
    {
        return number;
    }

    @Override
    public void parseValue(String s)
    {
        this.number = (E) ((Boolean) E.parseBoolean(s));
    }
}
