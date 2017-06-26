package utils.dstruct;

import utils.math.geom.AABB;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 3/29/2017.
 */
public interface OctItem
{
	void onRegionPlace(OctTree region);
	boolean collides(AABB region);
	boolean contains(AABB region);
	boolean changedTransform();
	Vector3f getPosition();
}
