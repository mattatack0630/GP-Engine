package input.picker;

import engine.Engine;
import gui.GuiTexture;
import input.InputManager;
import input.MouseRay;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import rendering.RenderData;
import rendering.camera.Camera;
import rendering.fbo.FboObject;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/3/2016.
 */
public class PickingManager
{
	private static List<Pickable> pickableList;

	private static PBODownloader pboColorDownloader;
	private static Pickable currPicked;

	private static Vector3f pickingPosition;
	private static MouseRay pickingRay;

	public static void init()
	{
		pboColorDownloader = new PBODownloader(GL11.GL_RGBA, 1, 1);
		pickableList = new ArrayList<>();
		currPicked = Pickable.NONE;

		pickingPosition = new Vector3f();
		pickingRay = new MouseRay();
	}

	public static void processPickingMesh(Pickable pickingObject, RenderData renderData)
	{
		renderData.setPickingId(pickingObject.getMesh().getCid());
		pickableList.add(pickingObject);
	}

	public static void doPickingCheck(FboObject pickingFbo, Vector2f coords, Camera camera)
	{
		pickingFbo.bindFrameBuffer();

		// Download pixel data
		pboColorDownloader.downloadData(pickingFbo, (int) (coords.x()), (int) (coords.y()));

		// Get pickable object using cid
		currPicked = getPickableFromCid(pboColorDownloader.getResults());
		currPicked.onPick();

		// Get mouse positions
		pickingPosition = (currPicked == Pickable.NONE) ? pickingRay.getRayScaled(camera.far) :
				calculateMousePosition(camera, pboColorDownloader.getResults());

		// Debug render texture
		GuiTexture t = new GuiTexture(pickingFbo.getColorAttachment(0), new Vector2f(), new Vector2f(2, 2));
		if (InputManager.isKeyDown(Keyboard.KEY_F))
			Engine.getRenderer().processGuiTexture(t);

		pickingFbo.unbindFrameBuffer();
		pickableList.clear();
	}

	private static Vector3f calculateMousePosition(Camera camera, byte[] color)
	{
		// Calculate mouses ray
		pickingRay.calculateRay(camera);

		// Get pixel color (green contains depth values)
		float depthPixel = (color[2] & 0xFF) / 255f;

		// Decode value into a z-depth value
		float distance = (float) Math.pow(Math.E, (depthPixel / camera.getLogRange())) - 1;

		// Find camera position using camera direction vector
		Vector3f v = pickingRay.getRayScaled(distance);
		Vector3f.add(camera.getPosition(), v, v);

		return v;
	}

	public static Pickable getPickableFromCid(byte[] color)
	{
		// color from the byte
		Vector2f c = new Vector2f(
				Maths.round((color[0] & 0xFF) / 255f, 2),
				Maths.round((color[1] & 0xFF) / 255f, 2));

		// Compare to the hundreds place
		for (Pickable pickable : pickableList)
		{
			PickingData mesh = pickable.getMesh();
			Vector2f v = mesh.getCid();

			// Change to a color based on index system
			if (Math.abs(c.x() - v.x()) < 0.01 && Math.abs(c.y() - v.y()) < 0.01)
				return pickable;
		}

		// No Pickable was found
		return Pickable.NONE;
	}

	public static Vector3f getPickedPosition()
	{
		return pickingPosition;
	}

	public static Pickable getPicked()
	{
		return currPicked;
	}

	public static void cleanUp()
	{
		pboColorDownloader.cleanUp();
	}
}