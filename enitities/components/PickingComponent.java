package enitities.components;

import engine.Engine;
import enitities.Entity;
import input.picker.Pickable;
import input.picker.PickingData;
import utils.math.Maths;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/5/2016.
 */
public class PickingComponent extends EntityComponent implements Pickable
{
	private boolean isPicked;
	private PickingData pickingMesh;

	public PickingComponent(Entity parent)
	{
		super(parent);
		this.pickingMesh = new PickingData();
	}

	@Override
	public PickingData getMesh()
	{
		return pickingMesh;
	}

	@Override
	public Vector3f getPosition()
	{
		return parent.getPosition();
	}

	@Override
	public float getRoughSize()
	{
		Vector3f s = parent.getScale();
		return Maths.max(s.x(), s.y(), s.z()) * 0.5f;
	}

	@Override
	public void onPick()
	{
		isPicked = true;
	}

	public boolean isPicked()
	{
		return isPicked;
	}

	public void setPicked(boolean picked)
	{
		this.isPicked = picked;
	}
}
