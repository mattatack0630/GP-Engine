package rendering.post;

import rendering.Color;
import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 3/14/2017.
 */
public class BorderlandsEffectInstance extends PostAffectInstance
{
	private FboObject objectMap;
	private Color borderColor;
	private float boldness;
	private int kernel;

	public BorderlandsEffectInstance(FboObject objectMap)
	{
		this.borderColor = new Color(0, 0, 0, 1);
		this.objectMap = objectMap;
		this.boldness = .5f;
		this.kernel = 5;
	}

	public BorderlandsEffectInstance(Color borderColor, float boldness, int kernel, FboObject objectMap)
	{
		this.borderColor = borderColor;
		this.objectMap = objectMap;
		this.boldness = boldness;
		this.kernel = kernel;
	}

	@Override
	public FboObject callAffect(FboObject inScreen, FboObject outScreen)
	{
		PostProcessor.borderlandsEffect.setKernel(kernel);
		PostProcessor.borderlandsEffect.setBoldness(boldness);
		PostProcessor.borderlandsEffect.setObjectMap(objectMap);
		PostProcessor.borderlandsEffect.setBorderColor(borderColor.rgba());

		return PostProcessor.borderlandsEffect.processAffect(inScreen, outScreen);
	}
}
