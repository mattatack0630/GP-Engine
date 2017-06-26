package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class IdentityAffect extends PostProcessingEffect
{
	public IdentityAffect()
	{
	}

	@Override
	protected void preAffect()
	{

	}

	@Override
	public FboObject doAffect(FboObject currentScreen, FboObject outScreen)
	{
		outScreen.bindFrameBuffer();

		PostProcessor.identityShader.start();
		PostProcessor.identityShader.loadTexture("", currentScreen.getColorAttachment(0), 0);
		render();

		outScreen.bindFrameBuffer();
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