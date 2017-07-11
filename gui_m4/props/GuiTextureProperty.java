package gui_m4.props;

import engine.Engine;
import gui_m4.GuiTexture;
import rendering.Color;
import resources.TextureResource;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/26/17.
 */
public class GuiTextureProperty extends GuiProperty<GuiTexture>
{
    private static final String IS_COLOR_REG = "rgb";

    public GuiTextureProperty(GuiTexture value, String propertyName)
    {
        super(value, propertyName);
    }

    public GuiTextureProperty(String propertyName)
    {
        super(propertyName);
    }

    @Override
    protected GuiTexture parseValueImplementation(String value)
    {
        if (value.contains(IS_COLOR_REG))
        {
            return new GuiTexture(new Color(value), new Vector2f(), new Vector2f());
        } else
        {
            TextureResource resource = new TextureResource(value, value);
            Engine.getResourceManager().directLoadResource(resource);
            return new GuiTexture(resource.getId(), new Vector2f(), new Vector2f());
        }
    }
}
