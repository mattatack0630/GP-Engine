package gui_m4.elements;

import engine.Engine;
import gui_m4.events.ConsumableEventsHandler;
import gui_m4.props.FloatGuiProperty;
import gui_m4.props.GuiProperty;
import utils.math.linear.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 7/6/17.
 */
public class AnimatedTexture extends GuiTextureElement
{
    private FloatGuiProperty framesPerSec;
    private List<Vector4f> frameCoords;
    private float lastTime;
    private float frameOn;

    public void setNextFrame(Vector4f frame)
    {

        frameCoords.add(frame);
    }

    public void setFramesPerSecond(float fps)
    {

        this.framesPerSec.setPropertyValue(fps);
    }

    @Override
    public void initialize()
    {
        super.initialize();
        this.frameOn = 0;
        this.lastTime = Engine.getTime();
        this.frameCoords = new ArrayList<>();
        this.framesPerSec = new FloatGuiProperty(0.0f, "FPS");
    }

    @Override
    public void onTickImp(ConsumableEventsHandler handler)
    {
        super.onTickImp(handler);

        if(frameCoords.size() > 0)
        {
            float deltaTime = Engine.getTime() - lastTime;
            float deltaFrames = (framesPerSec.getPropertyValue() * deltaTime);
            frameOn = (frameOn + deltaFrames) % frameCoords.size();
            Vector4f newCoords = frameCoords.get((int) (frameOn));
            lastTime = Engine.getTime();

            super.setTextureCoords(newCoords);
        }
    }

    @Override
    public void addParsableProperties(Map<String, GuiProperty> propertyMap)
    {
        super.addParsableProperties(propertyMap);
        propertyMap.put(framesPerSec.getPropertyName(), framesPerSec);
    }

    @Override
    public GuiElementBuilder getBuilder()
    {

        return AnimatedTexture.BUILDER;
    }

    public static final GuiElementBuilder<AnimatedTexture> BUILDER = new GuiElementBuilder<AnimatedTexture>()
    {
        @Override
        public boolean isParentable()
        {
            return false;
        }

        @Override
        public String getElementName()
        {
            return "animated-texture";
        }

        @Override
        public AnimatedTexture genInstance()
        {
            return new AnimatedTexture();
        }
    };
}
