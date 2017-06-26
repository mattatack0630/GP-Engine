package gui_m3;

import engine.Engine;
import org.lwjgl.input.Keyboard;
import rendering.renderers.MasterRenderer;

import java.util.Map;

/**
 * Created by matthew on 6/5/17.
 */
public class TextInputComponent extends ElementComponent
{
    private ContainerComponent labelContainer;
    private TextComponent textCtrlComponent;
    private TextureComponent blinkerCtrlComponent;
    private TextureComponent backgroundCtrlComponent;

    private boolean selected;

    public TextInputComponent(ContainerComponent labelContainer, TextComponent textCtrlComponent,
                              TextureComponent blinkerCtrlComponent, TextureComponent backgroundCtrlComponent)
    {
        this.labelContainer = labelContainer;
        this.textCtrlComponent = textCtrlComponent;
        this.blinkerCtrlComponent = blinkerCtrlComponent;
        this.backgroundCtrlComponent = backgroundCtrlComponent;
    }

    @Override
    public void onTick()
    {
        if(selected)
        {
            if (Engine.getTime() % 0.5 < 0.03f) blinkerCtrlComponent.setOpacity(0);
            if (Engine.getTime() % 1.0 < 0.03f) blinkerCtrlComponent.setOpacity(1);
        }else{
            blinkerCtrlComponent.setOpacity(0);
        }
    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

    }

    @Override
    public void onEvent(Event event)
    {
        if (event == MouseInteractionEvent.CLICK)
        {
            selected = !selected;
        }

        if (event == KeyboardEvent.DOWN && selected)
        {
            Map<Integer, Character> keysClick = Engine.getInputManager().getKeysClicked();
            Map<Integer, Character> keysDown = Engine.getInputManager().getKeysRepressing();

            for(int key : keysClick.keySet())
            {
                char c = keysClick.get(key);
                handleKey(key,c);
            }

            for(int key : keysDown.keySet())
            {
                char c = keysDown.get(key);
                handleKey(key, c);
            }

            textCtrlComponent.updateText();
            parentElement.build();
        }
    }

    private void handleKey(int key, char c)
    {
        if(c >= 32 && c <= 126)
            textCtrlComponent.appendText("" + c);
        else
            useControlKey(key);
    }

    private void useControlKey(int key)
    {
        switch (key)
        {
            case Keyboard.KEY_BACK:
                textCtrlComponent.removeTextFromEnd(1);
                break;
        }
    }

    @Override
    public void onBuild()
    {

    }

    @Override
    public void postBuild()
    {

    }
}
