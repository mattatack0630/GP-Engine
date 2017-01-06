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
	public FboObject doAffect(FboObject currentScreen)
	{
		PostProcessor.invertShader.start();
		PostProcessor.invertShader.loadTexture("", currentScreen.getColorAttachment(0), 0);
		render();

		return outputFbo;
	}

	@Override
	public void postAffect()
	{

	}

	@Override
	public void cleanAffect()
	{
		outputFbo.cleanUp();
	}
}
