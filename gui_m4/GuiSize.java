package gui_m4;

import gui_m4.elements.GuiElement;
import rendering.DisplayManager;
import utils.math.linear.vector.Vector2f;

/**
 * Created by matthew on 6/26/17.
 */
public class GuiSize
{
    public final boolean X_AXIS = true;
    public final boolean Y_AXIS = false;
    private static final String PIXEL_REG = "px";
    private static final String VIEWPORT_REG = "vp";
    private static final String PERCENTAGE_REG = "%";

    private GuiMeasurement guiX;
    private GuiMeasurement guiY;
    private Vector2f output;

    public GuiSize(String x, String y)
    {
        output = new Vector2f();
        setXtoValueOf(x);
        setYtoValueOf(y);
    }

    public boolean isRelative()
    {

        return guiX.isRelative() || guiY.isRelative();
    }

    public Vector2f resolve(GuiElement element)
    {
        output.setX(guiX.resolve(element));
        output.setY(guiY.resolve(element));
        return output;
    }

    public void setXtoValueOf(String value)
    {
        this.guiX = valueOf(value);
        this.guiX.setAxis(X_AXIS);
    }

    public void setYtoValueOf(String value)
    {
        this.guiY = valueOf(value);
        this.guiY.setAxis(Y_AXIS);
    }

    public GuiMeasurement valueOf(String value)
    {
        GuiMeasurement gm = null;

        if (value.contains(PIXEL_REG))
        {
            String actVal = value.replace(PIXEL_REG, "");
            gm = new GuiPX(Float.valueOf(actVal));
        }
        if (value.contains(VIEWPORT_REG))
        {
            String actVal = value.replace(VIEWPORT_REG, "");
            gm = new GuiVP(Float.valueOf(actVal));
        }
        if (value.contains(PERCENTAGE_REG))
        {
            String actVal = value.replace(PERCENTAGE_REG, "");
            gm = new GuiPCT(Float.valueOf(actVal) / 100.0f);
        }

        return gm;
    }

    // One component of the size vector
    public abstract class GuiMeasurement
    {
        protected float input;
        public boolean axis;

        public GuiMeasurement(float input)
        {
            this.input = input;
        }

        public void setAxis(boolean axis)
        {
            this.axis = axis;
        }

        public abstract boolean isRelative();

        public abstract float resolve(GuiElement element);
    }

    // pixel measurements (300px, 300px)
    public class GuiPX extends GuiMeasurement
    {

        public GuiPX(float input)
        {
            super(input);
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }

        @Override
        public float resolve(GuiElement element)
        {
            return axis ? ((input / DisplayManager.WIDTH) * 2.0f) : ((input / DisplayManager.HEIGHT) * 2.0f);
        }
    }

    // view port measurement (-1vp, 1vp)
    public class GuiVP extends GuiMeasurement
    {

        public GuiVP(float input)
        {
            super(input);
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }

        @Override
        public float resolve(GuiElement element)
        {
            return input;
        }
    }

    // relative parent percentage measurements (100%, 50%)
    public class GuiPCT extends GuiMeasurement
    {

        public GuiPCT(float input)
        {
            super(input);
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }

        @Override
        public float resolve(GuiElement element)
        {
            GuiElement parent = element.getParentElement();

            if (parent != null)
            {
                Vector2f elSize = parent.getElementSize();
                return axis ? elSize.x() * input : elSize.y() * input;
            }

            return 0;
        }
    }

}
