package gui;

/**
 * Created by matthew on 5/28/17.
 */
public class ParsableIntegerValue <E extends Integer> extends ParsableValue
{
    private E number;

    public ParsableIntegerValue(E init)
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
        this.number = (E)((Integer) E.parseInt(s));
    }
}
