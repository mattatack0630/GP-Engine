package shaders;

import rendering.camera.Camera;
import utils.math.linear.MatrixGenerator;
import utils.math.linear.matrix.Matrix4f;

/**
 * Created by mjmcc on 11/25/2016.
 */
public class CubeMapShader extends ShaderProgram
{
	public CubeMapShader()
	{
		super("CubeShaderV11.vp", "CubeShaderV11.fp");
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}

	public void loadViewMatrix(Camera camera)
	{
		Matrix4f vm = MatrixGenerator.genViewMatrix(camera);
		vm.setCol(3, new float[]{0, 0, 0, 1});
		super.loadMatrix("view", vm);
	}

	public void loadProjectionMatrix(Camera camera)
	{
		super.loadMatrix("projection", camera.getProjection());
	}

}
