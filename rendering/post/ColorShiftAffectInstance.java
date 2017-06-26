package rendering.post;

import rendering.Color;
import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class ColorShiftAffectInstance extends PostAffectInstance
{
	private int mode;
	private Color shiftBy;

	public ColorShiftAffectInstance(Color sb)
	{
		this.shiftBy = sb;
		this.mode = 1;
	}

	@Override
	public FboObject callAffect(FboObject inScreen, FboObject outScreen)
	{
		PostProcessor.colorShiftAffect.setAffectVars(shiftBy);
		return PostProcessor.colorShiftAffect.processAffect(inScreen, outScreen);
	}
}
