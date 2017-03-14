package rendering.renderers;

import models.StaticModel;
import org.lwjgl.opengl.GL11;
import rendering.Color;
import rendering.Light;
import rendering.RenderData;
import rendering.camera.Camera;
import rendering.fbo.FboObject;
import shaders.ShadowMapShader;
import shadows.ShadowBox;
import shadows.Shadowable;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 1/15/2017.
 */
public class ShadowMapRenderer
{

	private Map<StaticModel, List<Shadowable>> shadowableMap;
	private ShadowMapShader shader;
	private ShadowBox shadowBox;
	private FboObject shadowFBO;

	public ShadowMapRenderer(FboObject shadowFbo)
	{
		this.shader = new ShadowMapShader();
		this.shadowBox = new ShadowBox();
		this.shadowableMap = new HashMap<>();
		this.shadowFBO = shadowFbo;
	}

	public void addShadowable(Shadowable shadowObj)
	{
		List<Shadowable> shadowableList = shadowableMap.get(shadowObj.getShadowModel());

		if (shadowableList == null)
		{
			shadowableList = new ArrayList<>();
			shadowableMap.put(shadowObj.getShadowModel(), shadowableList);
		}

		shadowableList.add(shadowObj);
	}

	public void prepareRender(Camera camera, Light shadowLight)
	{
		shader.start();
		shadowBox.update(camera, shadowLight);
		shadowFBO.bindFrameBuffer();
		shadowFBO.clear(Color.BLACK);
	}

	public void prepareModel(StaticModel model)
	{
		model.getVaoObject().bind();
	}

	public void prepareInstance(RenderData data)
	{
		Matrix4f m = data.getTransformMatrix();
		Matrix4f v = shadowBox.getViewMatrix();
		Matrix4f p = shadowBox.getOrthoMatrix();

		Matrix4f mvp = new Matrix4f().setIdentity();
		mvp.mult(p).mult(v).mult(m);

		shader.loadMVP(mvp);
	}

	public void render(Camera camera, Light shadowLight)
	{
		prepareRender(camera, shadowLight);

		for (StaticModel model : shadowableMap.keySet())
		{
			prepareModel(model);
			List<Shadowable> shadowObjs = shadowableMap.get(model);

			for (Shadowable shadowObj : shadowObjs)
			{
				prepareInstance(shadowObj.getRenderData());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVaoObject().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
		}

		finishRender();
	}

	public void finishRender()
	{
		shader.stop();
		shadowableMap.clear();
		shadowFBO.unbindFrameBuffer();
	}

	public void cleanUp()
	{
		shader.cleanUp();
		shadowFBO.cleanUp();
	}

	public Matrix4f getConversionMat()
	{
		Matrix4f projView = new Matrix4f().setIdentity();
		projView.translate(new Vector3f(0.5f)).scale(new Vector3f(0.5f));
		projView.mult(shadowBox.getOrthoMatrix());
		projView.mult(shadowBox.getViewMatrix());
		return projView;
	}
}
