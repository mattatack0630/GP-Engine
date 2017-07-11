package gui_m4.elements;

/**
 * Created by matthew on 6/26/17.
 */
public class VPanelGuiElement extends ContainerGuiElement
{
    public VPanelGuiElement()
    {
        super();
        this.layout = new VerticalLayout();
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return VPanelGuiElement.BUILDER;
    }

    public static final GuiElementBuilder<VPanelGuiElement> BUILDER = new GuiElementBuilder<VPanelGuiElement>()
    {
        @Override
        public boolean isParentable()
        {
            return true;
        }

        @Override
        public String getElementName()
        {
            return "v-panel";
        }

        @Override
        public VPanelGuiElement genInstance()
        {
            return new VPanelGuiElement();
        }
    };
}
