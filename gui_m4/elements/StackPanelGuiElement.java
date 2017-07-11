package gui_m4.elements;

/**
 * Created by matthew on 6/26/17.
 */
public class StackPanelGuiElement extends ContainerGuiElement
{
    public StackPanelGuiElement()
    {
        super();
        this.layout = new FlatLayout();
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return StackPanelGuiElement.BUILDER;
    }

    public static final GuiElementBuilder<StackPanelGuiElement> BUILDER = new GuiElementBuilder<StackPanelGuiElement>()
    {
        @Override
        public StackPanelGuiElement genInstance()
        {
            return new StackPanelGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "s-panel";
        }

        @Override
        public boolean isParentable()
        {
            return true;
        }
    };
}
