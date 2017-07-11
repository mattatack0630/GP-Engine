package gui_m4.elements;

import gui_m4.GuiRenderStack;
import gui_m4.GuiStyle;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.props.GuiProperty;

import java.util.Map;

/**
 * Created by matthew on 6/29/17.
 */
public class TextFieldGuiElement extends GuiElement
{
    private StackPanelGuiElement textFieldContainer;
    private LabelGuiElement textLabel;
    private GuiTextureElement blinker;

    public TextFieldGuiElement()
    {

    }

    @Override
    public void initialize()
    {

    }

    @Override
    protected void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
    }

    @Override
    protected GuiStyle getDefaultStyle()
    {

        return new GuiStyle();
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return null;
    }

    @Override
    protected void onTickImp(ConsumableEventsHandler eventsHandler)
    {

    }

    @Override
    protected void onRenderImp(GuiRenderStack renderer)
    {

    }

    @Override
    protected void onBuildImp()
    {

    }

    @Override
    protected void onPostBuildImp()
    {

    }
}
