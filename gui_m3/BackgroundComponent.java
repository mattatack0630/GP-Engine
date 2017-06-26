package gui_m3;

import gui.GuiTexture;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/14/17.
 */
public class BackgroundComponent extends ElementComponent
{
    private GuiTexture backgroundTexture;

    public BackgroundComponent()
    {
        this.backgroundTexture = new GuiTexture(Color.NONE, new Vector2f(), new Vector2f());
    }

    public BackgroundComponent(Color c)
    {
        this.backgroundTexture = new GuiTexture(c, new Vector2f(), new Vector2f());
    }

    public BackgroundComponent(int id)
    {
        this.backgroundTexture = new GuiTexture(id, new Vector2f(), new Vector2f());
    }

    @Override
    public void onTick()
    {

    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

        renderer.processGuiTexture(backgroundTexture);
    }

    @Override
    public void onEvent(Event event)
    {

    }

    @Override
    public void onBuild()
    {

    }

    @Override
    public void postBuild()
    {
        backgroundTexture.setSize(parentElement.getElementSize());
        backgroundTexture.setRenderLevel(parentElement.getLayer() - 1f);
        backgroundTexture.setPosition(parentElement.getAbsolutePosition());
        backgroundTexture.setClippingBounds(parentElement.getClippingBounds());
    }

    public void setColor(Color color)
    {
        this.backgroundTexture.setColor(color);
    }

    public void setOpacity(float f)
    {
        this.backgroundTexture.setOpacity(f);
    }

    public void setTexture(int tid)
    {
        this.backgroundTexture.setTexture(tid);
    }
}
