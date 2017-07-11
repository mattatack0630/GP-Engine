package rendering.renderers;

import gui_m4.GuiRenderable;
import gui_m4.GuiTexture;
import gui_m4.text.GuiText;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import rendering.RenderData;
import rendering.VaoObject;
import shaders.GuiShader;
import utils.VaoLoader;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

import java.util.List;

public class GuiRenderer
{
	private static final float[] POSITIONS = new float[]{-.5f, .5f, -.5f, -.5f, .5f, .5f, .5f, .5f, -.5f, -.5f, .5f, -.5f};
	private static final VaoObject GUI_QUAD = VaoLoader.loadModel(2, POSITIONS,6);
	public static final Vector4f DEF_TEXTURE_COORDS = new Vector4f(0,0,1,1);

	public GuiShader combinedShader = new GuiShader();

	public GuiRenderer()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	private void prepareGuiText(GuiText texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getFont().getFontSheet());
		RenderData transform = texture.getTransform();

		Matrix4f transformationMatrix = MatrixGenerator.genTransformMatrix(
				new Vector3f(transform.getPosition().x(), transform.getPosition().y(), 0), transform.getRotation(),
				new Vector3f(texture.getAttribs().getFontSize(), texture.getAttribs().getFontSize(), 1), null);

		combinedShader.loadType(GuiShader.TEXT_TYPE);
		combinedShader.loadTextAttribs(texture.getAttribs());
		combinedShader.loadOverallAlpha(texture.getOpacity());
		combinedShader.loadTransformationMatrix(transformationMatrix);
		combinedShader.loadClippingBounds(texture.getClippingBounds());
	}

	private void prepareGuiTexture(GuiTexture texture)
	{
		if (texture.getTexture() != GuiTexture.NO_TEXTURE)
		{
			combinedShader.loadTexture("textureSampler", texture.getTexture(), 0);
		}

		Matrix4f transformationMatrix = MatrixGenerator.genTransformMatrix(
				new Vector3f(texture.getPosition().x(), texture.getPosition().y(), 0),
				new Euler(0, 0, texture.getRotation()),
				new Vector3f(texture.getSize().x(), texture.getSize().y(), 1), null);

		combinedShader.loadType(texture.getType());
		combinedShader.loadGtColor(texture.getColor());
		combinedShader.loadFloat("overallAlpha", texture.getOpacity());
		combinedShader.loadMatrix("transformation", transformationMatrix);
		combinedShader.loadVector4("v_texture_tc", texture.getTextureCoords());
		combinedShader.loadClippingBounds(texture.getClippingBounds());
	}

	public void renderGuis(List<GuiRenderable> renderables)
	{
		combinedShader.start();
		GUI_QUAD.bind();
		prepare();

		VaoObject lastMesh = GUI_QUAD;

		for (GuiRenderable renderable : renderables)
		{
			VaoObject mesh = null;

			// GuiTexture
			if (renderable instanceof GuiTexture)
			{
				GuiTexture t = (GuiTexture) renderable;
				prepareGuiTexture(t);
				mesh = GUI_QUAD;

				if(mesh != lastMesh)
					mesh.bind();

				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.vertexCount);
			}

			// GuiText
			if (renderable instanceof GuiText)
			{
				GuiText t = (GuiText) renderable;
				mesh = t.getMesh();
				prepareGuiText(t);

				if(mesh != lastMesh)
					mesh.bind();

				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.vertexCount);
			}

			lastMesh = mesh;
		}

		finish();
		lastMesh.unbind();
		combinedShader.stop();
	}

	public void prepare()
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	private void finish()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void cleanUp()
	{

		combinedShader.cleanUp();
	}
}