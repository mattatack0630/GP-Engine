package gui_m3;

import gui.GuiTexture;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 5/31/17.
 */
public class TextureComponent extends ElementComponent
{
    private GuiTexture texture;
    private Vector2f textureSize;

    public TextureComponent()
    {
        this.texture = new GuiTexture(Color.NONE, new Vector2f(), new Vector2f());
        this.textureSize = new Vector2f();
    }

    @Override
    public void onTick()
    {

    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

        renderer.processGuiTexture(texture);
    }

    @Override
    public void onEvent(Event event)
    {

    }

    @Override
    public void onBuild()
    {
        // possible change
        parentElement.setContentSize(textureSize);
    }

    @Override
    public void postBuild()
    {
        texture.setSize(parentElement.getElementSize());
        texture.setRenderLevel(parentElement.getLayer());
        texture.setPosition(parentElement.getAbsolutePosition());
        texture.setClippingBounds(parentElement.getClippingBounds());
    }

    public void setColor(Color color)
    {
        this.texture.setColor(color);
    }

    public void setOpacity(float f)
    {
        this.texture.setOpacity(f);
    }

    public void setTexture(int tid)
    {
        this.texture.setTexture(tid);
    }

    public void setSize(Vector2f size)
    {
        this.textureSize = size;
    }
}
