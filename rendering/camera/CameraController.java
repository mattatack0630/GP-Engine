package rendering.camera;

/**
 * Created by mjmcc on 12/1/2016.
 * camera controller class is used as a framework for different
 * camera control classes (WASD, Mouse Control, ect)
 */
public abstract class CameraController
{
	protected Camera camera;

	public CameraController(Camera camera)
	{
		this.camera = camera;
	}

	public abstract void tick();
}
