package rendering.post;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.VaoObject;
import rendering.fbo.FboObject;
import shaders.ShaderProgram;
import utils.VaoLoader;

/**
 * Created by mjmcc on 9/27/2016.
 */
public abstract class PostProcessingEffect
{
	private static final float[] POSITIONS = new float[]{-1f, 1f, 0, -1f, -1f, 0, 1f, 1f, 0, 1f, -1f, 0};
	private static final VaoObject SCREEN_VAO = VaoLoader.loadModel(3, POSITIONS, 4);

	public PostProcessingEffect()
	{
	}

	public void prepare()
	{
		preAffect();
		GL30.glBindVertexArray(SCREEN_VAO.vaoId);
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public FboObject processAffect(FboObject currentScreen, FboObject outputFbo)
	{
		prepare();
		FboObject result = doAffect(currentScreen, outputFbo);
		finish();
		return result;
	}

	public void render()
	{
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, SCREEN_VAO.vertexCount);
	}

	public void finish()
	{
		postAffect();
		ShaderProgram.stopActiveShaders();
	}

	protected abstract void preAffect();

	protected abstract FboObject doAffect(FboObject inScreen, FboObject outScreen);

	protected abstract void postAffect();

	public abstract void cleanAffect();
}
