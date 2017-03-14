package parsing.lisf;

import utils.math.linear.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 1/11/2017.
 */
public class LisfData
{
	public int version;
	public String modelName;
	public String texturePath;
	public String normalPath;
	public Vector2f rowColumn;
	public List<LisfSprite> spriteModels;
	public List<LisfAnimation> spriteAnimations;
	public boolean textureSmoothing;

	public LisfData()
	{
		rowColumn = new Vector2f();
		spriteAnimations = new ArrayList<>();
		spriteModels = new ArrayList<>();
	}

	public void addSprite(String name, String[] animations)
	{
		LisfSprite sprite = new LisfSprite(name, animations);
		spriteModels.add(sprite);
	}

	public void addSpriteAnimation(String animName, int[] seqData, int fps, int endMode, int loopPoint)
	{
		LisfAnimation spriteAnimation = new LisfAnimation(animName, seqData, fps, endMode, loopPoint);
		spriteAnimations.add(spriteAnimation);
	}

	public void debugPrint()
	{
		System.out.println("model name : " + modelName);
		System.out.println("texture path : " + texturePath);
		System.out.println("normal path : " + normalPath);
		System.out.println("rows : " + rowColumn.x());
		System.out.println("col : " + rowColumn.y());
		System.out.println("animations : ");
		for (int i = 0; i < spriteAnimations.size(); i++)
		{
			LisfAnimation animation = spriteAnimations.get(i);
			System.out.println("\tanimation name : " + animation.name);
			System.out.println("\tanimation seq : " + animation.sequence);
			System.out.println("\tanimation fps : " + animation.fps);
			System.out.println("\tanimation end mode : " + animation.endMode);
			System.out.println("\tanimation loop point : " + animation.loopPoint);
		}
		System.out.println("models : ");
		for (int i = 0; i < spriteModels.size(); i++)
		{
			LisfSprite sprite = spriteModels.get(i);
			System.out.println("\tmodel name : " + sprite.name);
			for (int j = 0; j < sprite.animations.length; j++)
				System.out.println("\tmodel animation : " + sprite.animations[j]);
		}
	}

	public class LisfSprite
	{
		public String name;
		public String[] animations;

		protected LisfSprite(String name, String[] animations)
		{
			this.name = name;
			this.animations = animations;
		}
	}

	public class LisfAnimation
	{
		public String name;
		public int[] sequence;
		public float fps;
		public int loopPoint;
		public int endMode;

		public LisfAnimation(String name, int[] sequence, float fps, int endMode, int loopPoint)
		{
			this.name = name;
			this.sequence = sequence;
			this.fps = fps;
			this.endMode = endMode;
			this.loopPoint = loopPoint;
		}
	}
}
