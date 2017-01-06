package input.picker;

/**
 * Created by mjmcc on 12/3/2016.
 */
public interface Pickable
{
	int t = 0;
	// Used as blank for no pickable object (PickingManager)
	Pickable NONE = new Pickable()
	{

		@Override
		public PickingData getMesh()
		{
			return null;
		}

		@Override
		public void onPick()
		{
		}
	};

	PickingData getMesh();

	void onPick();
}
