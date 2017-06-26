package shaders;

import rendering.DisplayManager;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 9/27/2016.
 */
public class PostProcessingShader extends ShaderProgram
{
	public static final String POST_SHADERS_PATH = "post_processing_shaders/";
	public static final String VERTEX_SHADER_FILE = "PostProcessingV11.vp";

	public PostProcessingShader(String fragmentShader)
	{
		super(POST_SHADERS_PATH + VERTEX_SHADER_FILE, POST_SHADERS_PATH + fragmentShader);
	}

	@Override
	public void start()
	{
		super.start();
		loadVector2("pixelSize", new Vector2f(1.0f/DisplayManager.HEIGHT, 1.0f/DisplayManager.WIDTH));
	}

	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}
}
