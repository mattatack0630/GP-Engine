package engine;

import gui_m4.*;
import rendering.renderers.MasterRenderer;
import states.GameState;
import states.GameStateManager;

/**
 * Created by mjmcc on 3/16/2017.
 */
public class TestState extends GameState
{
    GuiScene scene;

    @Override
    public void init()
    {
        scene = GuiScene.fromSourceFile("res/gui_files/test32.xml");
        scene.build();

        GuiStyle style = new GuiStyle();
        scene.applyStyle(style);
    }

    @Override
    public void cleanUp()
    {
    }

    @Override
    public void tick(GameStateManager stateManager)
    {
        scene.tick();
    }

    @Override
    public void render(MasterRenderer renderer)
    {
        scene.render(renderer);
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
