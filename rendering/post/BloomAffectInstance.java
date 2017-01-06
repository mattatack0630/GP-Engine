package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/20/2016.
 */
public class BloomAffectInstance extends PostAffectInstance
{
	private float clipValue;
	private float blurValue;
	private float addValue;

	public BloomAffectInstance(float cv, float bv, float av)
	{
		clipValue = cv;
		blurValue = bv;
		addValue = av;
	}

	public BloomAffectInstance()
	{
		// Give nice result, use if you don't know
		this(.1f, .2f, .5f);
	}

	@Override
	public FboObject callAffect(FboObject currentScreen)
	{
		PostProcessor.bloomAffect.setAffectVars(clipValue, blurValue, addValue);
		return PostProcessor.bloomAffect.processAffect(currentScreen);
	}
}
