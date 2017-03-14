package parsing.lisf;

import models.SpriteAnimation;
import models.SpriteModel;
import models.SpriteSequence;
import models.SpriteSheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 1/11/2017.
 */
public class LisfSpriteBuilder
{
	LisfData data;
	Map<String, SpriteModel> models;
	Map<String, SpriteAnimation> animations;

	public LisfSpriteBuilder(LisfData data)
	{
		this.data = data;
		this.models = new HashMap<>(data.spriteModels.size());
		this.animations = new HashMap<>(data.spriteAnimations.size());
	}

	public void buildSpriteAnimations(SpriteSheet spriteSheet)
	{
		List<LisfData.LisfAnimation> lisfAnimations = data.spriteAnimations;

		for (LisfData.LisfAnimation animation : lisfAnimations)
		{
			SpriteSequence spriteSequence = new SpriteSequence(spriteSheet, animation.sequence);
			SpriteAnimation sanimation = new SpriteAnimation(spriteSheet, spriteSequence, animation.fps);
			sanimation.setEndMode(animation.endMode);
			sanimation.setEndPoint(animation.loopPoint);
			animations.put(animation.name, sanimation);
		}
	}

	public void buildSpriteModels(SpriteSheet spriteSheet)
	{
		List<LisfData.LisfSprite> lisfModels = data.spriteModels;

		for (LisfData.LisfSprite model : lisfModels)
		{
			SpriteModel smodel = new SpriteModel(spriteSheet);
			for (int i = 0; i < model.animations.length; i++)
			{
				String animName = model.animations[i];
				smodel.addAnimation(animName, animations.get(animName));
			}
			models.put(model.name, smodel);
		}
	}

	public Map<String, SpriteModel> getModels()
	{
		return models;
	}
}
