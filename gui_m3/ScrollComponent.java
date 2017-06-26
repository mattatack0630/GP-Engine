package gui_m3;

import rendering.renderers.MasterRenderer;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/1/17.
 */
public class ScrollComponent extends ElementComponent
{
    private ContainerComponent containerComponent;
    private boolean updated;

    // input
    private Vector2f scrollVal;
    private Vector2f containerSize;
    private Vector2f contentsSize;

    // output
    private Vector2f contentsRelativeOffset;


    public ScrollComponent(ContainerComponent containerComponent)
    {
        this.containerComponent = containerComponent;

        this.scrollVal = new Vector2f();
        this.containerSize = new Vector2f();
        this.containerSize = new Vector2f();

        this.contentsRelativeOffset = new Vector2f();

        this.updated = false;
    }

    public void calculateRelativeOffset()
    {
        Vector2f min = Vector2f.sub(containerSize, contentsSize, null).scale(0.5f);
        Vector2f max = Vector2f.sub(contentsSize, containerSize, null).scale(0.5f);
        float dx = Maths.map(scrollVal.x(), 0.0f, 1.0f, min.x(), max.x());
        float dy = Maths.map(scrollVal.y(), 0.0f, 1.0f, min.y(), max.y());
        contentsRelativeOffset.setX(contentsSize.x() > containerSize.x() ? dx : 0.0f);
        contentsRelativeOffset.setY(contentsSize.y() > containerSize.y() ? dy : 0.0f);
    }

    @Override
    public void onTick()
    {
        // If values have changed recalculate and reset contents of container
        if (!updated)
        {
            calculateRelativeOffset();
            containerComponent.offsetChildren(contentsRelativeOffset);
            updated = true;
        }
    }

    @Override
    public void onRender(MasterRenderer renderer)
    {

    }

    @Override
    public void onEvent(Event event)
    {

    }

    @Override
    public void onBuild()
    {

    }

    @Override
    public void postBuild()
    {

    }

    public Vector2f getContentsRelativeOffset()
    {
        // Just making sure that the latest value is returned
        if (!updated) calculateRelativeOffset();
        return contentsRelativeOffset;
    }

    public Vector2f getScrollVal()
    {
        return scrollVal;
    }

    public void setScrollVal(Vector2f scrollVal)
    {
        this.scrollVal.setX(Maths.clamp(scrollVal.x(), 0, 1));
        this.scrollVal.setY(Maths.clamp(scrollVal.y(), 0, 1));
        this.updated = false;
    }

    public Vector2f getContainerSize()
    {
        return containerSize;
    }

    public void setContainerSize(Vector2f containerSize)
    {
        this.containerSize = containerSize;
        this.updated = false;
    }

    public Vector2f getContentsSize()
    {
        return contentsSize;
    }

    public void setContentsSize(Vector2f contentsSize)
    {
        this.contentsSize = contentsSize;
        this.updated = false;
    }

    public void setScrollX(float dx)
    {
        this.scrollVal.setX(Maths.clamp(dx, 0, 1));
        this.updated = false;
    }

    public void setScrollY(float dy)
    {
        this.scrollVal.setY(Maths.clamp(dy, 0, 1));
        this.updated = false;
    }
}
