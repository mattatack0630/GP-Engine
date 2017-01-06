package models;

import rendering.RenderData;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;

/**
 * Created by mjmcc on 4/13/2016.
 * The Sprite2D class
 * <p>
 * The Sprite is used for animated 2d quads using the Renderer2D
 */
public class Sprite2D
{

	private HashMap<String, Sprite2DAnimation> animations;
	private RenderData transform;
	private Sprite2DAnimation animationOn;

	public Sprite2D(RenderData transform)
	{
		this.animations = new HashMap<String, Sprite2DAnimation>();
		this.transform = transform;
	}

	public void addAnimation(String name, Sprite2DAnimation animation)
	{
		animations.put(name, animation);
	}

	public void tick()
	{
		if (animationOn != null)
			animationOn.tick();
	}

	public void render(MasterRenderer renderer)
	{
		if (animationOn != null)
			renderer.processSprite(this);
	}

	public RenderData getTransform()
	{
		return transform;
	}

	public float[] getStageCoordnates()
	{
		return animationOn != null ? animationOn.getTextureCoords() : new float[]{0, 0, 0, 0};
	}

	public SpriteSheet getSheet()
	{
		return animationOn.getSpriteSheet();
	}

	public void setAnimationOn(String animationName)
	{
		this.animationOn = animations.get(animationName);
	}
}
