package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class BlurAffectInstance extends PostAffectInstance
{
	private float blurScale;

	public BlurAffectInstance(float bs)
	{
		blurScale = bs;
	}

	@Override
	public FboObject callAffect(FboObject currentScreen)
	{
		PostProcessor.blurAffect.setAffectVars(blurScale);
		return PostProcessor.blurAffect.processAffect(currentScreen);
	}
}
