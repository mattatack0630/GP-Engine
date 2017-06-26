package engine;

import gui.AnimatedGuiTexture;
import gui.GuiTexture;
import gui_m3.*;
import gui_m3.props.BooleanGuiProperty;
import gui_m3.props.FloatGuiProperty;
import gui_m3.props.GuiProperty;
import models.SpriteSequence;
import models.SpriteSheet;
import parsing.gxml.SXMLParser;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import resources.SpriteSheetResource;
import resources.TextureResource;
import states.GameState;
import states.GameStateManager;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.List;

/**
 * Created by mjmcc on 3/16/2017.
 */
public class TestState extends GameState
{
     GuiScene element;

    @Override
    public void init()
    {
        element = new GuiScene("res/gui_files/test44.xml");
        element.build();

        List<GuiElement> elements = element.getByClass("menu_button");
        for(GuiElement el : elements)
            el.addComponent(new EventScalingComponent(MouseInteractionEvent.HOVER, MouseInteractionEvent.EXIT, el.getMinSize(), new GuiSize("1.1vp", "0.25vp"), 0.25f));

        BooleanGuiProperty property = new BooleanGuiProperty(false, "Test");

        property.addChangeListener((propertyRef, oldValue, newValue) -> {
            System.out.println("The value of " +propertyRef.getPropertyName() +" has changed!!!");
        });

        property.addChangeListener((propertyRef, oldValue, newValue) -> {
            System.out.println(oldValue + " -> "+ newValue);
        });

        property.setPropertyValue(true);
    }

    @Override
    public void cleanUp()
    {
    }

    @Override
    public void tick(GameStateManager stateManager)
    {
        element.tick();
    }

    @Override
    public void render(MasterRenderer renderer)
    {
        element.render(renderer);
        //renderer.processAnimatedGuiTexture(animatedGuiTexture);
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }
}
