package rendering.post;

import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/20/2016.
 */
public class DOFAffect extends PostProcessingEffect
{
	private float blurValue;
	private float distance;

	public void setAffectVars(float bv, float dist)
	{
		blurValue = bv;
		distance = dist;
	}

	@Override
	protected void preAffect()
	{

	}

	@Override
	protected FboObject doAffect(FboObject inScreen, FboObject outputFbo)
	{
/*		PostProcessor.blurAffect.setAffectVars(blurValue);
		FboObject blurScreen = PostProcessor.blurAffect.processAffect(currentScreen);

		outputFbo.bindFrameBuffer();
		PostProcessor.DOFShader.start();
		PostProcessor.DOFShader.loadFloat("dist", distance);
		PostProcessor.DOFShader.loadTexture("screenTexture", inScreen.getColorAttachment(0), 0);
		PostProcessor.DOFShader.loadTexture("depthTexture", inScreen.getDepthAttachment(), 1);
		PostProcessor.DOFShader.loadTexture("blurTexture", blurScreen.getColorAttachment(0), 2);
		render();

		outputFbo.unbindFrameBuffer();*/

		return outputFbo;
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
