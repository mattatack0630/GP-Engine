package rendering.post;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.fbo.FboObject;
import utils.VaoLoader;
import utils.VaoObject;

/**
 * Created by mjmcc on 9/27/2016.
 */
public abstract class PostProcessingEffect
{
	private static final float[] POSITIONS = new float[]{-1f, 1f, 0, -1f, -1f, 0, 1f, 1f, 0, 1f, -1f, 0};
	private static final VaoObject SCREEN_VAO = VaoLoader.loadModel(3, POSITIONS);
	protected FboObject outputFbo;

	public PostProcessingEffect()
	{
		this.outputFbo = new FboObject(Display.getWidth(), Display.getHeight(), FboObject.DEPTH_COLOR_TEXTURES);
		this.outputFbo.finishSetup();
	}

	public void prepare()
	{
		preAffect();
		outputFbo.bindFrameBuffer();
		GL30.glBindVertexArray(SCREEN_VAO.vaoId);
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public FboObject processAffect(FboObject currentScreen)
	{
		prepare();
		FboObject result = doAffect(currentScreen);
		finish();
		return result;
	}

	public void render()
	{
		//outputFbo.bindFrameBuffer(); // just in case
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, SCREEN_VAO.vertexCount);
	}

	public void finish()
	{
		postAffect();
		outputFbo.unbindFrameBuffer();
		GL20.glUseProgram(0); // Maybe change, fine for now
	}

	protected abstract void preAffect();

	protected abstract FboObject doAffect(FboObject currentScreen);

	protected abstract void postAffect();

	public abstract void cleanAffect();
}
