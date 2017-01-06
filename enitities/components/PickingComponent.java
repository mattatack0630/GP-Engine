package enitities.components;

import enitities.Entity;
import input.picker.Pickable;
import input.picker.PickingData;

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
	public void onPick()
	{
		System.out.println("Test");
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
