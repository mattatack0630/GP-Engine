package rendering.post;

import rendering.fbo.FboObject;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 3/14/2017.
 */
public class BorderlandsEffect extends PostProcessingEffect
{
	private FboObject objectMap;
	private Vector4f borderColor;
	private float boldness;
	private int kernel;

	@Override
	protected void preAffect()
	{

	}

	@Override
	protected FboObject doAffect(FboObject currentScreen, FboObject outScreen)
	{

		outScreen.bindFrameBuffer();

		PostProcessor.borderShader.start();
		PostProcessor.borderShader.loadInteger("kernel", kernel);
		PostProcessor.borderShader.loadFloat("boldness", boldness);
		PostProcessor.borderShader.loadVector4("borderColor", borderColor);
		PostProcessor.borderShader.loadTexture("colorTexture", currentScreen.getColorAttachment(0), 0);
		PostProcessor.borderShader.loadTexture("depthTexture", currentScreen.getDepthAttachment(), 2);
		PostProcessor.borderShader.loadTexture("objectTexture", objectMap.getColorAttachment(0), 1);
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

	public void setObjectMap(FboObject objectMap)
	{
		this.objectMap = objectMap;
	}

	public void setKernel(int kernel)
	{
		this.kernel = kernel;
	}

	public void setBoldness(float boldness)
	{
		this.boldness = boldness;
	}

	public void setBorderColor(Vector4f borderColor)
	{
		this.borderColor = borderColor;
	}
}
