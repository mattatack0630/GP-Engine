package rendering.post;

import rendering.Color;
import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class ColorShiftAffect extends PostProcessingEffect
{
	private Color shiftBy;

	public void setAffectVars(Color sb)
	{
		shiftBy = sb;
	}

	@Override
	protected void preAffect()
	{

	}

	@Override
	protected FboObject doAffect(FboObject currentScreen)
	{
		PostProcessor.colorShader.start();
		PostProcessor.colorShader.loadTexture("", currentScreen.getColorAttachment(0), 0);
		PostProcessor.colorShader.loadInteger("mode", 1);
		PostProcessor.colorShader.loadVector3("RGBShift", shiftBy.rgb());
		render();

		return outputFbo;
	}

	@Override
	protected void postAffect()
	{
		shiftBy = Color.NONE;
	}

	@Override
	public void cleanAffect()
	{
		outputFbo.cleanUp();
	}
}
