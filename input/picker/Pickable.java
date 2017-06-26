package input.picker;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/3/2016.
 */
public interface Pickable
{
	// Used as blank for no pickable object (PickingManager)
	Pickable NONE = new Pickable()
	{

		@Override
		public PickingData getMesh()
		{
			return null;
		}

		@Override
		public Vector3f getPosition()
		{
			return null;
		}

		@Override
		public float getRoughSize()
		{
			return 0;
		}

		@Override
		public void onPick()
		{
		}
	};

	PickingData getMesh();

	Vector3f getPosition();

	float getRoughSize();

	void onPick();
}
