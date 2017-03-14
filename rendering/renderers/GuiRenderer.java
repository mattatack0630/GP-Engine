package rendering.renderers;

import gui.GuiTexture;
import gui.Text.GuiText;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.RenderData;
import shaders.FontShader;
import shaders.GuiShader;
import utils.VaoLoader;
import utils.VaoObject;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;

import java.util.List;

public class GuiRenderer
{
	private static final float[] POSITIONS = new float[]{-.5f, .5f, 0, -.5f, -.5f, 0, .5f, .5f, 0, .5f, -.5f, 0};
	private static final VaoObject QUAD = VaoLoader.loadModel(3, POSITIONS);

	public GuiShader GuiTexureShader = new GuiShader();
	public FontShader fontShader = new FontShader();

	public GuiRenderer()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public void prepare()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	private void prepareGuiText(GuiText texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getFont().getFontSheet());
		RenderData transform = texture.getTransform();

		Matrix4f transformationMatrix = MatrixGenerator.genTransformMatrix(
				new Vector3f(transform.getPosition().x(), transform.getPosition().y(), 0), transform.getRotation(),
				new Vector3f(texture.getAttribs().getFontSize(), texture.getAttribs().getFontSize(), 1), null);

		fontShader.loadOverallAlpha(texture.getOpacity());
		fontShader.loadTransformationMatrix(transformationMatrix);
		fontShader.loadTextAttribs(texture.getAttribs());
	}

	public void renderGuiText(List<GuiText> text)
	{
		fontShader.start();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		for (GuiText texture : text)
		{
			GL30.glBindVertexArray(texture.getMesh().vaoId);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			prepareGuiText(texture);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, texture.getMesh().vertexCount);
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);

		fontShader.stop();
	}

	private void prepareGuiTexture(GuiTexture texture)
	{
		boolean hasTexture = false;

		if (texture.getTexture() != GuiTexture.NO_TEXTURE)
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTexture());
			hasTexture = true;
		}

		Matrix4f transformationMatrix = MatrixGenerator.genTransformMatrix(
				new Vector3f(texture.getPosition().x(), texture.getPosition().y(), 0),
				new Euler(0, 0, texture.getRotation()),
				new Vector3f(texture.getSize().x(), texture.getSize().y(), 1), null);

		GuiTexureShader.loadColor(texture.getColor());
		GuiTexureShader.loadHasTexture(hasTexture);
		GuiTexureShader.loadOverallAlpha(texture.getOpacity());
		GuiTexureShader.loadTransformationMatrix(transformationMatrix);
	}

	public void renderTextures(List<GuiTexture> textures)
	{
		GuiTexureShader.start();
		GL30.glBindVertexArray(QUAD.vaoId);
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		for (GuiTexture texture : textures)
		{
			prepareGuiTexture(texture);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD.vertexCount);
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GuiTexureShader.stop();
	}

	public void cleanUp()
	{
		GuiTexureShader.cleanUp();
		fontShader.cleanUp();
	}
}
