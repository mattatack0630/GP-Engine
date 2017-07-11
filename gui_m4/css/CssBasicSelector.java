package gui_m4.css;

/**
 * Created by matthew on 6/30/17.
 */
public abstract class CssBasicSelector extends CssSelector
{
    protected String selectorName;

    public CssBasicSelector(String selectorName)
    {
        this.selectorName = selectorName;
    }
}
