package rendering.post;

import org.lwjgl.opengl.Display;
import rendering.fbo.FboObject;

/**
 * Created by mjmcc on 11/19/2016.
 */
public class BlurAffect extends PostProcessingEffect
{
	private final int SCREEN_COUNT = 6; // The amount of screens created
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
			float r = R_FACTOR * (i + 1);
			stepScreens[i] = new FboObject((int) (Display.getWidth() / r), (int) (Display.getHeight() / r),
					FboObject.DEPTH_COLOR_TEXTURES);
		}

	}

	public void setAffectVars(float s)
	{
		scale = s;
	}

	@Override
	protected void preAffect()
	{
		steps = (int) ((SCREEN_COUNT * KERNEL_MAX) * (scale));
		steps = Math.min(Math.max(steps, 1), SCREEN_COUNT * KERNEL_MAX);
	}

	@Override
	protected FboObject doAffect(FboObject inScreen)
	{
		PostProcessor.blurShader.start();
		PostProcessor.blurShader.loadTexture("", inScreen.getColorAttachment(0), 0);
		int tSteps = (steps - 1) / 11;

		for (int i = 0; i <= tSteps; i++)
		{
			FboObject currScreen = stepScreens[i];
			currScreen.bindFrameBuffer();

			// Calculate kernel
			float kernel = Math.min((i + 1) * KERNEL_MAX, steps) - (i * KERNEL_MAX);
			kernel = (kernel % 2 == 0) ? kernel + 1 : kernel;

			// load shader variables
			PostProcessor.blurShader.loadFloat("kernel", kernel);
			PostProcessor.blurShader.loadVector2("targetSize", currScreen.getDimensions());

			// Horizontal Blur
			PostProcessor.blurShader.loadFloat("dir", 0);
			render();

			// Load hblur texture
			PostProcessor.blurShader.loadTexture("", currScreen.getColorAttachment(0), 0);

			// Vertical Blur
			PostProcessor.blurShader.loadFloat("dir", 1);
			render();

			currScreen.unbindFrameBuffer();
		}

		return stepScreens[tSteps]; // Save for other uses?
	}

	@Override
	protected void postAffect()
	{

	}

	@Override
	public void cleanAffect()
	{
		outputFbo.cleanUp();
		for (FboObject f : stepScreens)
			f.cleanUp();
	}
}
