package gui_m4;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by matthew on 6/28/17.
 */
public class GuiRenderStack
{
    private List<GuiRenderable> renderables;
    private float layer;

    public GuiRenderStack(int layer)
    {
        this.layer = layer;
        this.renderables = new LinkedList<>();
    }

    public void setNextRenderable(GuiRenderable renderable)
    {
        this.renderables.add(renderable);
        this.layer = renderable.getRenderLevel();
    }

    public void resetStack()
    {
        this.renderables.clear();
        this.layer = 0;
    }

    public void setLayer(float layer)
    {
        this.layer = layer;
    }

    public List<GuiRenderable> getRenderables()
    {
        return renderables;
    }

    public float getLayer()
    {
        return layer;
    }
}
