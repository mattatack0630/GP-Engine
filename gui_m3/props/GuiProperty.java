package gui_m3.props;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 6/26/17.
 */
public abstract class GuiProperty <T>
{
    private T propertyValue;
    private T lastPropertyValue;

    private String propertyName;
    private List<ChangeListener<T>> listeners;

    public GuiProperty(T value, String propertyName){
        this.propertyValue = value;
        this.propertyName = propertyName;
        this.listeners = new ArrayList<>();
        resolvePotentialValueChange();
    }

    public GuiProperty(String propertyName){
        this(null, propertyName);
    }

    public void parseValue(String value){
        this.lastPropertyValue = this.propertyValue;
        this.propertyValue = parseValueImplementation(value);
        resolvePotentialValueChange();
    }

    protected abstract T parseValueImplementation(String value);

    public T getPropertyValue(){
        return propertyValue;
    }

    public String getPropertyName(){
        return propertyName;
    }

    public void setPropertyValue(T propertyValue){
        this.lastPropertyValue = this.propertyValue;
        this.propertyValue = propertyValue;
        resolvePotentialValueChange();
    }

    private void resolvePotentialValueChange(){
        if(lastPropertyValue != propertyValue)
        {
            for (ChangeListener<T> changeListener : listeners)
            {
                changeListener.changed(this, lastPropertyValue, propertyValue);
            }
        }
    }

    public void addChangeListener(ChangeListener<T> listener){
        listeners.add(listener);
    }
}
