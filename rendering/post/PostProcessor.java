package rendering.post;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.fbo.FboObject;
import shaders.PostProcessingShader;

/**
 * Created by mjmcc on 9/27/2016.
 */
public class PostProcessor
{
	/**
	 * Affect shaders
	 */
	public static PostProcessingShader contrastShader = new PostProcessingShader("ContrastShiftV11.fp");
	public static PostProcessingShader colorClipShader = new PostProcessingShader("ColorClipV11.fp");
	public static PostProcessingShader identityShader = new PostProcessingShader("IdentityV11.fp");
	public static PostProcessingShader additionShader = new PostProcessingShader("AdditionV11.fp");
	public static PostProcessingShader colorShader = new PostProcessingShader("ColorShiftV11.fp");
	public static PostProcessingShader invertShader = new PostProcessingShader("InvertV11.fp");
	public static PostProcessingShader blurShader = new PostProcessingShader("GBlurV11.fp");
	public static PostProcessingShader DOFShader = new PostProcessingShader("DOFV11.fp");

	/**
	 * Affects
	 */
	public static ColorShiftAffect colorShiftAffect = new ColorShiftAffect();
	public static ColorClipAffect colorClipAffect = new ColorClipAffect();
	public static IdentityAffect identityAffect = new IdentityAffect();
	public static InvertAffect invertAffect = new InvertAffect();
	public static BloomAffect bloomAffect = new BloomAffect();
	public static BlurAffect blurAffect = new BlurAffect();
	public static DOFAffect dofAffect = new DOFAffect();

	public void prepare()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public void finish()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public void renderToDisplay(FboObject finalScreen)
	{
		identityAffect.processAffect(finalScreen);
	}

	public void cleanUp()
	{
		colorClipShader.cleanUp();
		additionShader.cleanUp();
		contrastShader.cleanUp();
		identityShader.cleanUp();
		invertShader.cleanUp();
		colorShader.cleanUp();
		blurShader.cleanUp();
		DOFShader.cleanUp();

		colorShiftAffect.cleanAffect();
		colorClipAffect.cleanAffect();
		identityAffect.cleanAffect();
		invertAffect.cleanAffect();
		blurAffect.cleanAffect();
	}
}
