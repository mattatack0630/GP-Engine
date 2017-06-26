package gui_m3;

import engine.Engine;
import rendering.renderers.MasterRenderer;
import utils.math.Interpolation;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/9/17.
 */
public class EventScalingComponent extends ElementComponent
{
    private Event eventTriggerBegin;
    private Event eventTriggerEnd;

    private boolean hardBuild;
    private boolean animating;
    private float beginTime;

    private float duration;
    private GuiSize sizeInitGS;
    private GuiSize sizeBeginGS;
    private GuiSize sizeEndGS;

    private Vector2f sizeBegin;
    private Vector2f sizeEnd;
    private Vector2f sizeNow;

    public EventScalingComponent(Event eventTrigger, GuiSize sizeBegin, GuiSize sizeEnd, float duration)
    {
        this.eventTriggerBegin = eventTrigger;
        this.sizeBeginGS = sizeBegin;
        this.sizeEndGS = sizeEnd;
        this.duration = duration;

        this.sizeBegin = new Vector2f();
        this.sizeEnd = new Vector2f();
        this.sizeNow = new Vector2f();

        this.hardBuild = false;
    }

    public EventScalingComponent(Event eventTrigger, Event eventTriggerEnd, GuiSize sizeBegin, GuiSize sizeEnd, float duration)
    {
        this.eventTriggerBegin = eventTrigger;
        this.eventTriggerEnd = eventTriggerEnd;
        this.sizeBeginGS = sizeBegin;
        this.sizeEndGS = sizeEnd;
        this.duration = duration;

        this.sizeBegin = new Vector2f();
        this.sizeEnd = new Vector2f();
        this.sizeNow = new Vector2f();

        this.hardBuild = false;
    }

    @Override
    public void onTick()
    {
        if (animating)
        {
            // Calculate current size
            float dt = (Engine.getTime() - beginTime) / duration;

            if (dt <= 1.0f)
            {
                sizeNow.setX(Interpolation.linearInterpolate(sizeBegin.x(), sizeEnd.x(), dt));
                sizeNow.setY(Interpolation.linearInterpolate(sizeBegin.y(), sizeEnd.y(), dt));
                parentElement.setMinSize(sizeNow);
            } else if (eventTriggerEnd == null)
            {
                // Check finished
                parentElement.setMinSize(sizeInitGS);
                sizeInitGS = null;
                animating = false;
            }

            // Update build
            if (!hardBuild) parentElement.build();
            else doHardBuild();
        }

    }

    private void doHardBuild()
    {

    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

    }

    @Override
    public void onEvent(Event event)
    {
        if (event.equals(eventTriggerBegin) && !animating)
        {
            this.animating = true;
            this.sizeInitGS = parentElement.getMinSize();
            this.sizeBegin = sizeBeginGS.resolve(parentElement);
            this.sizeEnd = sizeEndGS.resolve(parentElement);
            this.beginTime = Engine.getTime();
        }

        if (eventTriggerEnd != null)
        {
            if (event.equals(eventTriggerEnd) && animating)
            {
                parentElement.setMinSize(sizeInitGS);
                sizeInitGS = null;
                animating = false;
                if (!hardBuild) parentElement.build();
                else doHardBuild();
            }
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
