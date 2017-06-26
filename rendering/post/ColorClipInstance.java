package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class ColorClipInstance extends PostAffectInstance
{
	private float clipValue;

	public ColorClipInstance(float cv)
	{
		clipValue = cv;
	}

	@Override
	public FboObject callAffect(FboObject inScreen, FboObject outScreen)
	{
		PostProcessor.colorClipAffect.setAffectVars(clipValue);
		return PostProcessor.colorClipAffect.processAffect(inScreen, outScreen);
	}
}
