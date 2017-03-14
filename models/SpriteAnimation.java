package models;

import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 1/6/2017.
 */
public class SpriteAnimation
{
	public static final int END_STOP = 2233;
	public static final int END_LOOP = 2325;

	private SpriteSheet spriteSheet;
	private SpriteSequence spriteSequence;
	private float framesPerSecond;
	private int endPoint;
	private int endMode;

	public SpriteAnimation(SpriteSheet spriteSheet, SpriteSequence sequence, float fps)
	{
		this.spriteSheet = spriteSheet;
		this.spriteSequence = sequence;
		this.framesPerSecond = fps;
		this.endMode = END_STOP;
		this.endPoint = 0;
	}

	public Vector4f getCurrAnimationCoords(float timeOn)
	{
		int frameOn = 0;
		int frames = (int) (timeOn * framesPerSecond);
		int tileCount = spriteSequence.getTileCount();

		if (tileCount > 1)
		{
			if (endMode == END_STOP)
				frameOn = frames < tileCount ? frames : endPoint;
			if (endMode == END_LOOP)
				frameOn = frames < tileCount ? frames : frames % (tileCount - endPoint) + endPoint;
		}

		Vector4f coords = spriteSequence.getTileMinMax(frameOn);
		return coords;
	}

	public float getAnimationDuration()
	{
		return (spriteSequence.getTileCount() / framesPerSecond);
	}

	public void setFramesPerSecond(float fps)
	{
		this.framesPerSecond = fps;
	}

	public void setEndPoint(int endPoint)
	{
		this.endPoint = endPoint;
	}

	public void setEndMode(int endMode)
	{
		this.endMode = endMode;
	}

}
