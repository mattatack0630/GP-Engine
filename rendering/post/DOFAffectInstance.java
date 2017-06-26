package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/20/2016.
 */
public class DOFAffectInstance extends PostAffectInstance
{
	private float blurValue;
	private float distance;

	public DOFAffectInstance(float blurValue, float distance)
	{
		this.blurValue = blurValue;
		this.distance = distance;
	}

	@Override

	public FboObject callAffect(FboObject inScreen, FboObject outScreen)
	{
		PostProcessor.dofAffect.setAffectVars(blurValue, distance);
		return PostProcessor.dofAffect.processAffect(inScreen, outScreen);
	}
}
