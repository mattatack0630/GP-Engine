package gui_m4.elements;

import engine.Engine;
import gui_m4.GuiRenderStack;
import gui_m4.GuiStyle;
import gui_m4.GuiTexture;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.props.*;
import rendering.Color;
import resources.TextureResource;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.Map;

/**
 * Created by matthew on 6/26/17.
 */
public class GuiTextureElement extends GuiElement
{
    private GuiTexture texture;
    private ColorGuiProperty colorGP;
    private StringGuiProperty imageGP;
    private IntegerGuiProperty imageIdGP;
    private Vec4GuiProperty textureCoordsGP;

    public GuiTextureElement()
    {
        // super called
        super();
    }

    public GuiTextureElement(Color color)
    {
        this.colorGP.getPropertyValue().set(color);
        this.colorGP.resolvePotentialValueChange();
    }

    public GuiTextureElement(String texture)
    {

        this.imageGP.setPropertyValue(texture);
    }

    public GuiTextureElement(GuiTexture texture)
    {

        this.setImage(texture);
    }

    public void setColor(Color color)
    {

        this.colorGP.setPropertyValue(color);
    }

    public void setOpacity(float opacity)
    {
        this.texture.setOpacity(opacity);
    }

    public void setImage(int img)
    {

        this.imageIdGP.setPropertyValue(img);
    }

    public void setImage(String img)
    {

        this.imageGP.setPropertyValue(img);
    }

    public void setImage(GuiTexture texture)
    {
        this.texture.setFromSource(texture);
        this.onPostBuildImp();
    }

    public void setTextureCoords(Vector4f textureCoords)
    {

        this.textureCoordsGP.setPropertyValue(textureCoords);
    }

    @Override
    public void initialize()
    {
        this.colorGP = new ColorGuiProperty(new Color(), "COLOR");
        this.imageGP = new StringGuiProperty(null, "TEXTURE");
        this.imageIdGP = new IntegerGuiProperty(-1, "TEXTURE_ID");
        this.textureCoordsGP = new Vec4GuiProperty(new Vector4f(0, 0, 1, 1), "TEXTURE_COORDS");
        this.texture = new GuiTexture(new Color(), new Vector2f(), new Vector2f());

        this.colorGP.addChangeListener((property, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                this.texture.setColor(newValue);
            }
        });

        this.imageGP.addChangeListener((property, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                TextureResource resource = new TextureResource(newValue, newValue);
                resource = Engine.getResourceManager().directLoadResource(resource);
                this.texture.setTexture(resource.getId());
            }
        });

        this.imageIdGP.addChangeListener(((property, oldValue, newValue) ->
        {
            this.texture.setTexture(imageIdGP.getPropertyValue());
        }));

        this.textureCoordsGP.addChangeListener(((property, oldValue, newValue) ->
        {
            texture.setTextureCoords(textureCoordsGP.getPropertyValue());
        }));
    }

    @Override
    public void onTickImp(ConsumableEventsHandler eventsHandler)
    {
    }

    @Override
    public void onRenderImp(GuiRenderStack renderer)
    {

        renderer.setNextRenderable(texture);
    }

    @Override
    public void onBuildImp()
    {
    }

    @Override
    public void onPostBuildImp()
    {
        texture.setSize(getElementSize());
        texture.setRenderLevel(getLayer());
        texture.setPosition(getAbsolutePosition());
        texture.setClippingBounds(getClippingBounds());
        texture.setTextureCoords(textureCoordsGP.getPropertyValue());
    }

    @Override
    public void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
        propertyMap.put(colorGP.getPropertyName(), colorGP);
        propertyMap.put(imageGP.getPropertyName(), imageGP);
        propertyMap.put(imageIdGP.getPropertyName(), imageIdGP);
        propertyMap.put(textureCoordsGP.getPropertyName(), textureCoordsGP);
    }

    @Override
    protected GuiStyle getDefaultStyle()
    {
        GuiStyle defaultStyle = new GuiStyle();

        defaultStyle.addProperty(this, new ColorGuiProperty(new Color(1), "COLOR"));

        return defaultStyle;
    }

    @Override
    public GuiElementBuilder getBuilder()
    {

        return GuiTextureElement.BUILDER;
    }

    public static final GuiElementBuilder<GuiTextureElement> BUILDER = new GuiElementBuilder<GuiTextureElement>()
    {
        @Override
        public boolean isParentable()
        {
            return false;
        }

        @Override
        public String getElementName()
        {
            return "texture";
        }

        @Override
        public GuiTextureElement genInstance()
        {
            return new GuiTextureElement();
        }
    };

}
