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
	protected FboObject doAffect(FboObject inScreen, FboObject outScreen)
	{
		outScreen.bindFrameBuffer();

		PostProcessor.colorClipShader.start();
		PostProcessor.colorClipShader.loadTexture("", inScreen.getColorAttachment(0), 0);
		PostProcessor.colorClipShader.loadFloat("clippingValue", clipValue);
		render();

		outScreen.unbindFrameBuffer();
		return outScreen;
	}

	@Override
	protected void postAffect()
	{

	}

	@Override
	public void cleanAffect()
	{
	}
}
