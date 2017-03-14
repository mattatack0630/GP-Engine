package input.picker;

import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 12/3/2016.
 * <p>
 * Picking data class is used to hold information about a
 * pickable object.
 */
public class PickingData
{
	private static float idCounter = 0;

	// Unique color id
	private Vector2f cid;

	public PickingData(Vector2f cid)
	{
		this.cid = cid;
	}

	// Random Color id
	public PickingData()
	{
		//this(new Color());
		this(new Vector2f(idCounter % 1.0f, ((int) (idCounter / 1.0f) * (1.0f / 255.0f))));
		idCounter += (1.0 / 255.0f);
	}

	public Vector2f getCid()
	{
		return cid;
	}
}
