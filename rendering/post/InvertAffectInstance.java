package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class InvertAffectInstance extends PostAffectInstance
{
	@Override
	public FboObject callAffect(FboObject currentScreen)
	{
		return PostProcessor.invertAffect.processAffect(currentScreen);
	}
}
