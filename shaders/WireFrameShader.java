package shaders;

/**
 * Created by mjmcc on 9/28/2016.
 */
public class WireFrameShader extends ShaderProgram
{
	public static final String VERTEX_LOC = "WireFrameV11.vp";
	public static final String GEOMETRY_LOC = "WireFrameV11.gp";
	public static final String FRAGMENT_LOC = "WireFrameV11.fp";

	public WireFrameShader()
	{
		super(VERTEX_LOC, GEOMETRY_LOC, FRAGMENT_LOC);
	}

	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}
}
