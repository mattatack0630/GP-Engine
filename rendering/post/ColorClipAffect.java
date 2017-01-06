package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class ColorClipAffect extends PostProcessingEffect
{
	private float clipValue;

	public void setAffectVars(float cv)
	{
		clipValue = cv;
	}

	@Override
	protected void preAffect()
	{

	}

	@Override
	protected FboObject doAffect(FboObject currentScreen)
	{
		PostProcessor.colorClipShader.start();
		PostProcessor.colorClipShader.loadTexture("", currentScreen.getColorAttachment(0), 0);
		PostProcessor.colorClipShader.loadFloat("clippingValue", clipValue);
		render();

		return outputFbo;
	}

	@Override
	protected void postAffect()
	{

	}

	@Override
	public void cleanAffect()
	{
		outputFbo.cleanUp();
	}
}
