package models;

import utils.math.geom.AABBmm;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 1/6/2017.
 */
public class SpriteModel
{
	private AABBmm boundingBox;
	private SpriteSheet spriteSheet;
	private Map<String, SpriteAnimation> animations;

	public SpriteModel(SpriteSheet spriteSheet)
	{
		this.spriteSheet = spriteSheet;
		this.animations = new HashMap<>();

		Vector2f size = Vector2f.scale(spriteSheet.getTileSize(), 0.5f, null);
		this.boundingBox = new AABBmm(new Vector3f(-size.x(), -size.y(), 0), new Vector3f(size.x(), size.y(), 0));
	}

	public void addAnimation(String name, SpriteAnimation animation)
	{
		animations.put(name, animation);
	}

	public SpriteAnimation getAnimation(String animationName)
	{
		return animations.get(animationName);
	}

	public SpriteSheet getSpriteSheet()
	{
		return spriteSheet;
	}

	public AABBmm getBoundingBox()
	{
		return boundingBox;
	}
}
