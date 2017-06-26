package gui_m3;

import gui.text.GuiText;
import gui.text.TextAttributes;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/1/17.
 */
public class TextComponent extends ElementComponent
{
    private GuiText text;

    public TextComponent()
    {
        this.text = new GuiText("", "Arial", new Vector2f());
    }

    @Override
    public void onTick()
    {

    }

    @Override
    public void onRender(MasterRenderer renderer)
    {
        renderer.processGuiText(text);
    }

    @Override
    public void onEvent(Event event)
    {

    }

    @Override
    public void onBuild()
    {
        parentElement.setContentSize(text.getSize());

    }

    @Override
    public void postBuild()
    {
        text.setPosition(parentElement.getAbsolutePosition());
        text.setClippingBounds(parentElement.getClippingBounds());
        text.update();
    }

    public void setText(String text)
    {
        this.text.setText(text);
    }

    public TextAttributes getTextAttributes()
    {
        return this.text.getAttribs();
    }

    public void updateText()
    {
        this.text.update();
    }

    public void appendText(String s)
    {
        this.text.setText(this.text.getText() + s);
    }

    public void removeTextFromEnd(int i)
    {
        String t = text.getText();

        if(t.length() - i >= 0)
        {
            text.setText(t.substring(0, t.length() - i));
        }
    }
}
