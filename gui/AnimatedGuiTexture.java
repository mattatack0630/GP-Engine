package gui;

import engine.Engine;
import gui.text.GuiText;
import models.SpriteSequence;
import models.SpriteSheet;
import rendering.Color;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by matthew on 6/12/17.
 */
public class AnimatedGuiTexture
{
    private SpriteSequence animSeq;
    private GuiTexture texture;
    private boolean loop;
    private float fps;

    private float startTime;
    private float lastTime;

    public AnimatedGuiTexture()
    {
        this.texture = new GuiTexture(Color.NONE, new Vector2f(), new Vector2f());
        this.animSeq = new SpriteSequence(null, new int[]{});
        this.fps = 0;

        this.lastTime = Engine.getTime();
        this.texture.setTextureCoords(new Vector4f());
        this.loop = true;
    }

    public AnimatedGuiTexture(SpriteSequence animSeq, float fps)
    {
        SpriteSheet spriteSheet = animSeq.getSheet();
        this.texture = new GuiTexture(spriteSheet.getTextureID(), new Vector2f(), new Vector2f());
        this.animSeq = animSeq;
        this.fps = fps;
        this.loop = true;

        this.lastTime = Engine.getTime();
        this.texture.setTextureCoords(animSeq.getNextTileMinMax());
    }

    public AnimatedGuiTexture(SpriteSequence animSeq, float fps, Vector2f pos, Vector2f size)
    {
        SpriteSheet spriteSheet = animSeq.getSheet();
        this.texture = new GuiTexture(spriteSheet.getTextureID(), pos, size);
        this.animSeq = animSeq;
        this.fps = fps;
        this.loop = true;

        this.lastTime = Engine.getTime();
        this.texture.setTextureCoords(animSeq.getNextTileMinMax());
    }

    /**
     * Called from renderer
     */
    public void updateTexture()
    {
        float dt = Engine.getTime() - lastTime;
        float dts = Engine.getTime() - startTime;
        System.out.println(dts + "   " + (animSeq.getTileCount() / fps));

        // move one frame ahead
        if (animSeq.getSheet() != null)
        {
            if (dts < (animSeq.getTileCount() / fps) || loop)
            {
                if (dt > 1.0f / fps)
                {
                    Vector4f texCoords = animSeq.getNextTileMinMax();
                    texture.setTextureCoords(texCoords);
                    lastTime = Engine.getTime();
                }
            }
        }
    }

    public GuiTexture getTexture()
    {
        return texture;
    }

    public void setSequence(SpriteSequence sequence)
    {
        this.animSeq = sequence;
        this.lastTime = Engine.getTime();
        this.texture.setTexture(animSeq.getSheet().getTextureID());
        this.texture.setTextureCoords(animSeq.getNextTileMinMax());
    }

    public SpriteSequence getSequence()
    {
        return animSeq;
    }

    public void setFPS(Float FPS)
    {
        this.fps = FPS;
    }

    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }
}
