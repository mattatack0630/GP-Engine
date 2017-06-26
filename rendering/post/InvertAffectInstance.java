package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class InvertAffectInstance extends PostAffectInstance
{
	@Override
	public FboObject callAffect(FboObject inScreen, FboObject outScreen)
	{
		return PostProcessor.invertAffect.processAffect(inScreen, outScreen);
	}
}
