package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/20/2016.
 */
public class DOFAffectInstance extends PostAffectInstance
{
	private float blurValue;

	public DOFAffectInstance(float blurValue)
	{
		this.blurValue = blurValue;
	}

	@Override

	public FboObject callAffect(FboObject currentScreen)
	{
		PostProcessor.dofAffect.setAffectVars(blurValue);
		return PostProcessor.dofAffect.processAffect(currentScreen);
	}
}
