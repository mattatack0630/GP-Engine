package gui;

/**
 * Created by matthew on 5/28/17.
 */
public abstract class ParsableValue <E>
{
    public abstract E getValue();
    public abstract void parseValue(String s);
    //public abstract E
}
