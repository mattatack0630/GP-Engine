package gui;

/**
 * Created by matthew on 5/28/17.
 */
public class ParsableFloatValue<E extends Float> extends ParsableValue
{
    private E number;

    public ParsableFloatValue(E init)
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
        this.number = (E) ((Float) E.parseFloat(s));
    }
}
