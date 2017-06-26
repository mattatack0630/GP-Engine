package rendering.post;

import org.lwjgl.opengl.Display;
import rendering.fbo.ColorTextureAttachment;
import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class BlurAffect extends PostProcessingEffect
{
	private final int SCREEN_COUNT = 8; // The amount of screens created
	private final int KERNEL_MAX = 7; // The max kernel for each blur pass
	private final float R_FACTOR = 1.5f; // How much each screen's resolution is reduced by

	private FboObject[] stepScreens;

	private float scale;
	private int steps;

	public BlurAffect()
	{
		stepScreens = new FboObject[SCREEN_COUNT];

		for (int i = 0; i < stepScreens.length; i++)
		{
			float r = R_FACTOR * ((i/2) + 1);
			stepScreens[i] = new FboObject((int) (Display.getWidth() / r), (int) (Display.getHeight() / r));
			stepScreens[i].addColorAttachment(new ColorTextureAttachment(stepScreens[i].getDimensions()));
			stepScreens[i].finishSetup();
		}

	}

	public void setAffectVars(float s)
	{
		scale = s;
	}

	@Override
	protected void preAffect()
	{
		steps = (int) (((SCREEN_COUNT/2) * KERNEL_MAX) * (scale));
		steps = Math.min(Math.max(steps, 1), (SCREEN_COUNT/2) * KERNEL_MAX);
	}

	@Override
	protected FboObject doAffect(FboObject inScreen, FboObject outScreen)
	{
		PostProcessor.blurShader.start();
		PostProcessor.blurShader.loadTexture("textureSampler", inScreen.getColorAttachment(0), 0);
		FboObject currScreen = stepScreens[0];

		int i = 0;
		int screenOn = 0;
		int kernelsLeft = steps;
		int tSteps = (steps - 1) / KERNEL_MAX;

		while (i <= tSteps)
		{
			currScreen = stepScreens[screenOn++];
			currScreen.bindFrameBuffer();

			// Calculate kernel
			float kernel = kernelsLeft > KERNEL_MAX ? KERNEL_MAX : kernelsLeft;
			kernelsLeft -= kernel;
			kernel = (kernel % 2 == 0) ? kernel + 1 : kernel;

			// load shader variables
			PostProcessor.blurShader.loadFloat("kernel", kernel);
			PostProcessor.blurShader.loadVector2("targetSize", currScreen.getDimensions());

			// Horizontal Blur
			PostProcessor.blurShader.loadFloat("dir", 0);
			render();

			// Load hblur texture
			PostProcessor.blurShader.loadTexture("", currScreen.getColorAttachment(0), 0);

			if (i >= tSteps)
				currScreen = outScreen;
			else
				currScreen = stepScreens[screenOn++];

			currScreen.bindFrameBuffer();

			// Vertical Blur
			PostProcessor.blurShader.loadFloat("dir", 1);
			render();

			currScreen.unbindFrameBuffer();

			i += 1;
		}

		return currScreen;
	}

	@Override
	protected void postAffect()
	{

	}

	@Override
	public void cleanAffect()
	{
		for (FboObject f : stepScreens)
			f.cleanUp();
	}
}
