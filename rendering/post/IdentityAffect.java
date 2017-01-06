package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class IdentityAffect extends PostProcessingEffect
{
	public IdentityAffect()
	{
		outputFbo.cleanUp();
		outputFbo = FboObject.SCREEN;
		outputFbo.finishSetup();
	}

	@Override
	protected void preAffect()
	{

	}

	@Override
	protected FboObject doAffect(FboObject currentScreen)
	{
		PostProcessor.identityShader.start();
		PostProcessor.identityShader.loadTexture("", currentScreen.getColorAttachment(0), 0);
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