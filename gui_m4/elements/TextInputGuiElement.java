package gui_m4.elements;

import gui_m4.events.KeyChecker;
import gui_m4.events.KeyUsable;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.events.ConsumableKeyboardEvent;
import gui_m4.props.BooleanGuiProperty;
import org.lwjgl.input.Keyboard;

/**
 * Created by matthew on 6/29/17.
 */
public class TextInputGuiElement extends ButtonGuiElement implements KeyUsable
{
    private KeyChecker keyChecker;

    private LabelGuiElement text;
    private GuiTextureElement blinker;
    private BooleanGuiProperty pressed;

    @Override
    public void initialize()
    {
        // Initialize button
        super.initialize();

        // Initial textInput
        this.keyChecker = new KeyChecker(this);

        this.text = new LabelGuiElement();
        this.blinker = new GuiTextureElement();
        this.pressed = new BooleanGuiProperty(false, "PRESSED");

        // Adds to content panel
        addChild(this.text);
        addChild(this.blinker);
    }

    @Override
    protected void onTickImp(ConsumableEventsHandler eventsHandler)
    {
        super.onTickImp(eventsHandler);

        if (pressed.getPropertyValue())
        {
            ConsumableKeyboardEvent keyboardEvent = eventsHandler.getEvent(ConsumableKeyboardEvent.class);

            if (keyboardEvent != null)
            {
                keyChecker.checkKey();
                eventsHandler.consume(keyboardEvent);
            }
        }
    }

    @Override
    public void onClick()
    {
        super.onClick();
        pressed.setPropertyValue(!pressed.getPropertyValue().booleanValue());
    }

    protected void addLetter(char c)
    {
        String textStr = text.getText();
        textStr += c;

        text.setText(textStr);
    }

    protected void removeLetter(int i)
    {
        String textStr = text.getText();

        if (textStr.length() >= i)
        {
            String subStr = textStr.substring(0, textStr.length() - i);
            text.setText(subStr);
        }
    }

    private void handleKey(int i, char c)
    {
        // Meh
        if (c >= 32 && c <= 126) {
            addLetter(c);
        } else if (i == Keyboard.KEY_BACK) {
            removeLetter(1);
        }
    }

    @Override
    public void onKeyPress(int i, char c)
    {
    }

    @Override
    public void onKeyRelease(int i, char c)
    {
    }

    @Override
    public void onKeyClick(int i, char c)
    {
        handleKey(i, c);
    }

    @Override
    public void onKeyRepress(int i, char c)
    {
        handleKey(i, c);
    }


    @Override
    public GuiElementBuilder getBuilder()
    {
        return TextInputGuiElement.BUILDER;
    }

    public static final GuiElementBuilder<TextInputGuiElement> BUILDER = new GuiElementBuilder<TextInputGuiElement>()
    {
        @Override
        public TextInputGuiElement genInstance()
        {
            return new TextInputGuiElement();
        }

        @Override
        public String getElementName()
        {
            return "text-input";
        }

        @Override
        public boolean isParentable()
        {
            return false;
        }
    };
}
