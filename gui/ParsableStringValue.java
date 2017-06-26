package gui;

import gui.ParsableValue;

/**
 * Created by matthew on 5/28/17.
 */
public class ParsableStringValue <E extends String> extends ParsableValue
{
    private E value;

    public ParsableStringValue(E init)
    {
        this.value = init;
    }

    @Override
    public E getValue()
    {
        return value;
    }

    @Override
    public void parseValue(String s)
    {
        this.value = (E) s;
    }
}
