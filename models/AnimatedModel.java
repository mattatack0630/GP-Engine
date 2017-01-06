package models;

import animation.AnimationData;
import animation.Bone;
import animation.ModelAnimation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mjmcc on 10/29/2016.
 */
public class AnimatedModel extends StaticModel
{
	public AnimationData animationData;

	public AnimatedModel(StaticModel model, List<Bone> bones, HashMap<String, ModelAnimation> animations)
	{
		super(model.getVaoObject(), model.getMaterial(), model.getBoundingBox(),
				model.getTexture(), model.getNormalMap(), model.getInfoMap());

		this.animationData = new AnimationData(animations, bones);
	}

	public AnimationData getAnimationData()
	{
		return animationData;
	}
}
