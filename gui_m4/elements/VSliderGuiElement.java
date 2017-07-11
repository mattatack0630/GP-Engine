package gui_m4.elements;

import gui_m4.props.GuiProperty;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by matthew on 6/29/17.
 */
public class VSliderGuiElement extends AreaSliderGuiElement
{

    public VSliderGuiElement(){
        super();
        this.lockX.setPropertyValue(true);
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return VSliderGuiElement.BUILDER;
    }

    @Override
    protected void onBuildImp()
    {
        calculateSizes();
        Vector2f elSize = getElementSize();
        sliderButton.setContentSize(new Vector2f(elSize.x(), elSize.y() * DEFUALT_BUTTON_SCALE));
        sliderRail.setContentSize(new Vector2f(getElementSize()));
    }

    @Override
    protected void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
    }

    public static final GuiElementBuilder<AreaSliderGuiElement> BUILDER = new GuiElementBuilder<AreaSliderGuiElement>()
    {
        @Override
        public AreaSliderGuiElement genInstance()
        {
            return new VSliderGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "v-slider";
        }

        @Override
        public boolean isParentable()
        {
            return false;
        }
    };
}
