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
	protected FboObject doAffect(FboObject inScreen, FboObject outScreen)
	{
		outScreen.bindFrameBuffer();

		PostProcessor.colorShader.start();
		PostProcessor.colorShader.loadTexture("", inScreen.getColorAttachment(0), 0);
		PostProcessor.colorShader.loadInteger("mode", 2);
		PostProcessor.colorShader.loadVector4("RGBShift", shiftBy.rgba());
		render();

		outScreen.unbindFrameBuffer();
		return outScreen;
	}

	@Override
	protected void postAffect()
	{
		shiftBy = Color.NONE;
	}

	@Override
	public void cleanAffect()
	{
	}
}
