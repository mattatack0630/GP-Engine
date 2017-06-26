package gui_m3;

import gui.AnimatedGuiTexture;
import gui.GuiTexture;
import models.SpriteSequence;
import models.SpriteSheet;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/12/17.
 */
public class AnimatedTextureComponent extends ElementComponent
{
    private AnimatedGuiTexture animTexture;
    private Vector2f textureSize;

    public AnimatedTextureComponent()
    {
        this.animTexture = new AnimatedGuiTexture();
        this.textureSize = new Vector2f();
    }
    public AnimatedTextureComponent(SpriteSequence seq, float fps)
    {
        this.animTexture = new AnimatedGuiTexture(seq, fps);
        this.textureSize = new Vector2f();
    }
    @Override
    public void onTick()
    {

    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

        renderer.processAnimatedGuiTexture(animTexture);
    }

    @Override
    public void onEvent(Event event)
    {

    }

    @Override
    public void onBuild()
    {

        parentElement.setContentSize(textureSize);
    }

    @Override
    public void postBuild()
    {
        GuiTexture texture = animTexture.getTexture();
        texture.setSize(parentElement.getElementSize());
        texture.setRenderLevel(parentElement.getLayer());
        texture.setPosition(parentElement.getAbsolutePosition());
        texture.setClippingBounds(parentElement.getClippingBounds());
    }

    public void setColor(Color color)
    {
        GuiTexture texture = animTexture.getTexture();
        texture.setColor(color);
    }

    public void setOpacity(float f)
    {
        GuiTexture texture = animTexture.getTexture();
        texture.setOpacity(f);
    }

    public void setTexture(int tid)
    {
        GuiTexture texture = animTexture.getTexture();
        texture.setTexture(tid);
    }

    public void setAnimSequence(SpriteSequence sequence)
    {
        this.animTexture.setSequence(sequence);
    }

    public void setAnimSequence(int[] sequence)
    {
        this.animTexture.getSequence().setTile(sequence);
    }

    public void setSize(Vector2f size)
    {
        textureSize = size;
    }

    public void setAnimSheet(SpriteSheet animSheet)
    {
        SpriteSequence seq = animTexture.getSequence();
        int[] tiles = seq.getTiles();
        animTexture.setSequence(new SpriteSequence(animSheet, tiles));
    }

    public void setFPS(Float FPS)
    {
        this.animTexture.setFPS(FPS);
    }

    public void setLoop(boolean loop)
    {
        this.animTexture.setLoop(loop);
    }
}
