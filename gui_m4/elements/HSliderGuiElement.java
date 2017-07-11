package gui_m4.elements;

import gui_m4.props.GuiProperty;
import utils.math.linear.vector.Vector2f;

import java.util.Map;

/**
 * Created by matthew on 6/29/17.
 */
public class HSliderGuiElement extends AreaSliderGuiElement
{
    public HSliderGuiElement(){
        super();
        this.lockY.setPropertyValue(true);
    }

    @Override
    protected void onBuildImp()
    {
        calculateSizes();
        Vector2f elSize = getElementSize();
        sliderButton.setContentSize(new Vector2f(elSize.x() * DEFUALT_BUTTON_SCALE, elSize.y()));
        sliderRail.setContentSize(new Vector2f(getElementSize()));
    }
    @Override
    protected void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
    }

    @Override
    public GuiElementBuilder getBuilder()
    {
        return HSliderGuiElement.BUILDER;
    }

    public static final GuiElementBuilder<HSliderGuiElement> BUILDER = new GuiElementBuilder<HSliderGuiElement>()
    {
        @Override
        public HSliderGuiElement genInstance()
        {
            return new HSliderGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "h-slider";
        }

        @Override
        public boolean isParentable()
        {
            return false;
        }
    };
}
