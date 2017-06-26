package gui_m3;

import engine.Engine;
import rendering.renderers.MasterRenderer;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/2/17.
 */
public class SliderComponent extends ElementComponent
{
    private GuiElement backgroundElement;
    private GuiElement buttonElement;

    private Vector2f grapplePos;
    private Vector2f value;

    private boolean lockX;
    private boolean lockY;

    public SliderComponent(GuiElement backgroundElement, GuiElement buttonElement)
    {
        this.backgroundElement = backgroundElement;
        this.buttonElement = buttonElement;
        this.grapplePos = new Vector2f();
        this.value = new Vector2f();
        this.lockX = false;
        this.lockY = false;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

    }

    @Override
    public void onEvent(Event event)
    {
        Vector2f mouse = new Vector2f(Engine.getInputManager().getGLCursorCoords());

        if (event == MouseInteractionEvent.CLICK)
        {
            Vector2f.sub(mouse, buttonElement.getAbsolutePosition(), grapplePos);
            Vector2f buttonSize = buttonElement.getBoundingSize();

            if (Maths.abs(grapplePos.x()) > buttonSize.x() / 2.0f)
                grapplePos.setX(0);
            if (Maths.abs(grapplePos.y()) > buttonSize.y() / 2.0f)
                grapplePos.setY(0);
        }

        if (event == MouseInteractionEvent.HOLD)
        {
            Vector2f size = parentElement.getElementSize();
            Vector2f pos = parentElement.getAbsolutePosition();
            Vector2f buttonSize = buttonElement.getBoundingSize();
            Vector2f sliderSize = Vector2f.sub(size, buttonSize, null).scale(0.5f);

            Vector2f min = new Vector2f(pos.x() - sliderSize.x(), pos.y() - sliderSize.y());
            Vector2f max = new Vector2f(pos.x() + sliderSize.x(), pos.y() + sliderSize.y());
            float dx = Maths.map(mouse.x() - grapplePos.x(), min.x(), max.x(), 0.0f, 1.0f);
            float dy = Maths.map(mouse.y() - grapplePos.y(), min.y(), max.y(), 0.0f, 1.0f);
            dx = Maths.clamp(dx, 0.0f, 1.0f);
            dy = Maths.clamp(dy, 0.0f, 1.0f);

            Vector2f newButtonRel = new Vector2f(buttonElement.getRelativePosition());
            if (!lockX) newButtonRel.setX(Maths.map(dx, 0, 1, -sliderSize.x(), sliderSize.x()));
            if (!lockY) newButtonRel.setY(Maths.map(dy, 0, 1, -sliderSize.y(), sliderSize.y()));

            buttonElement.setRelativePosition(newButtonRel);
            buttonElement.build();

            value.setX(dx);
            value.setY(dy);
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

    public void setLockX(boolean lockX)
    {
        this.lockX = lockX;
    }

    public void setLockY(boolean lockY)
    {
        this.lockY = lockY;
    }

    public Vector2f getValue()
    {
        return value;
    }
}
