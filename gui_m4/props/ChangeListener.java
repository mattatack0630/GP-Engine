package gui_m4.props;

/**
 * Created by matthew on 6/26/17.
 */
@FunctionalInterface
public interface ChangeListener<T> {

    /**
     * @param property the reference to a the changed gui property
     *                 (used in case the programmer wants to write one listener for multiple properties)
     * @param oldValue The old value
     * @param newValue  The new value
     */
    void changed(GuiProperty<T> property, T oldValue, T newValue);
}
