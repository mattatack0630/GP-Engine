package rendering.post;

import org.lwjgl.opengl.Display;
import rendering.fbo.ColorTextureAttachment;
import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/20/2016.
 */
public class BloomAffect extends PostProcessingEffect
{
	private FboObject mutScreen;
	private float clipValue;
	private float blurValue;
	private float addValue;

	public BloomAffect()
	{
		mutScreen = new FboObject(Display.getWidth(), Display.getHeight());
		mutScreen.addColorAttachment(new ColorTextureAttachment(mutScreen.getDimensions()));
		mutScreen.finishSetup();
		clipValue = 0.5f;
		blurValue = 0.3f;
		addValue = 0.2f;
	}

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
	protected FboObject doAffect(FboObject inScreen, FboObject outScreen)
	{
		PostProcessor.colorClipAffect.setAffectVars(clipValue);
		PostProcessor.colorClipAffect.processAffect(inScreen, outScreen);

		PostProcessor.blurAffect.setAffectVars(blurValue);
		PostProcessor.blurAffect.processAffect(outScreen, mutScreen);

		outScreen.bindFrameBuffer();

		PostProcessor.additionShader.start();
		PostProcessor.additionShader.loadFloat("ratio", addValue);
		PostProcessor.additionShader.loadTexture("textureSampler1", inScreen.getColorAttachment(0), 0);
		PostProcessor.additionShader.loadTexture("textureSampler2", mutScreen.getColorAttachment(0), 1);
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
