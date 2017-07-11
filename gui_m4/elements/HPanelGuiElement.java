package gui_m4.elements;

/**
 * Created by matthew on 6/26/17.
 */
public class HPanelGuiElement extends ContainerGuiElement
{
    public HPanelGuiElement()
    {
        super();
        this.layout = new HorizontalLayout();
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return HPanelGuiElement.BUILDER;
    }

    public static final GuiElementBuilder<HPanelGuiElement>  BUILDER = new GuiElementBuilder<HPanelGuiElement>()
    {
        @Override
        public HPanelGuiElement genInstance()
        {
            return new HPanelGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "h-panel";
        }

        @Override
        public boolean isParentable()
        {
            return true;
        }
    };

}
