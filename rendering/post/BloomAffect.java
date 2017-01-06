package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/20/2016.
 */
public class BloomAffect extends PostProcessingEffect
{
	private float clipValue;
	private float blurValue;
	private float addValue;

	public void setAffectVars(float cv, float bv, float av)
	{
		clipValue = cv;
		blurValue = bv;
		addValue = av;
	}

	@Override
	protected void preAffect()
	{

	}

	@Override
	protected FboObject doAffect(FboObject currentScreen)
	{
		PostProcessor.colorClipAffect.setAffectVars(clipValue);
		FboObject clippedScreen = PostProcessor.colorClipAffect.processAffect(currentScreen);
		PostProcessor.blurAffect.setAffectVars(blurValue);
		FboObject blurredScreen = PostProcessor.blurAffect.processAffect(clippedScreen);

		outputFbo.bindFrameBuffer();
		PostProcessor.additionShader.start();
		PostProcessor.additionShader.loadFloat("ratio", addValue);
		PostProcessor.additionShader.loadTexture("textureSampler1", currentScreen.getColorAttachment(0), 0);
		PostProcessor.additionShader.loadTexture("textureSampler2", blurredScreen.getColorAttachment(0), 1);
		render();
		outputFbo.unbindFrameBuffer();

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
