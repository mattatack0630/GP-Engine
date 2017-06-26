package gui_m3;

import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 6/9/17.
 */
public class GuiManager
{
    private List<GuiScene> scenes;

    public GuiManager()
    {
        this.scenes = new ArrayList<>();
    }

    public void tick()
    {
        for(GuiScene scene : scenes)
        {
            scene.tick();
        }
    }

    public void render(MasterRenderer renderer)
    {
        for(GuiScene scene : scenes)
        {
            scene.render(renderer);
        }
    }

    public void show(GuiScene scene)
    {
        scenes.add(scene);
    }

    public void hide(GuiScene scene)
    {
        scenes.remove(scene);
    }

}
