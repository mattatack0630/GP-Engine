package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class InvertAffect extends PostProcessingEffect
{

	@Override
	public void preAffect()
	{

	}

	@Override
	public FboObject doAffect(FboObject currentScreen, FboObject outScreen)
	{
		outScreen.bindFrameBuffer();

		PostProcessor.invertShader.start();
		PostProcessor.invertShader.loadTexture("textureSampler", currentScreen.getColorAttachment(0), 0);
		render();

		outScreen.unbindFrameBuffer();
		return outScreen;
	}

	@Override
	public void postAffect()
	{

	}

	@Override
	public void cleanAffect()
	{
	}
}
