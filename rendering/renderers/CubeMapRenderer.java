package rendering.renderers;

import org.lwjgl.opengl.GL11;
import rendering.VaoObject;
import rendering.camera.Camera;
import rendering.post.CubeMap;
import shaders.CubeMapShader;
import utils.VaoLoader;

/**
 * Created by mjmcc on 11/26/2016.
 */
public class CubeMapRenderer
{
	private static final float SIZE = 500f;

	private static final float[] CUBE_POSITIONS = {
			-SIZE, SIZE, -SIZE,
			-SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,
			SIZE, SIZE, -SIZE,
			-SIZE, SIZE, -SIZE,

			-SIZE, -SIZE, SIZE,
			-SIZE, -SIZE, -SIZE,
			-SIZE, SIZE, -SIZE,
			-SIZE, SIZE, -SIZE,
			-SIZE, SIZE, SIZE,
			-SIZE, -SIZE, SIZE,

			SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, SIZE,
			SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE,
			SIZE, SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,

			-SIZE, -SIZE, SIZE,
			-SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE,
			SIZE, -SIZE, SIZE,
			-SIZE, -SIZE, SIZE,

			-SIZE, SIZE, -SIZE,
			SIZE, SIZE, -SIZE,
			SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE,
			-SIZE, SIZE, SIZE,
			-SIZE, SIZE, -SIZE,

			-SIZE, -SIZE, -SIZE,
			-SIZE, -SIZE, SIZE,
			SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,
			-SIZE, -SIZE, SIZE,
			SIZE, -SIZE, SIZE
	};

	private static final VaoObject CUBE_VAO = VaoLoader.loadModel(3, CUBE_POSITIONS, 36);

	private CubeMapShader cubeMapShader;

	public CubeMapRenderer()
	{
		this.cubeMapShader = new CubeMapShader();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	/*Static Model Stuff*/
	private void prepare(Camera camera)
	{
		cubeMapShader.loadViewMatrix(camera);
		cubeMapShader.loadProjectionMatrix(camera);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public void renderCubeMap(CubeMap map, Camera camera)
	{
		cubeMapShader.start();
		CUBE_VAO.bind();

		prepare(camera);
		cubeMapShader.loadTextureCube("cubeMap", map.getId(), 0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, CUBE_VAO.vertexCount);

		CUBE_VAO.unbind();
		cubeMapShader.stop();
	}

	public void cleanUp()
	{
		cubeMapShader.cleanUp();
	}
}
