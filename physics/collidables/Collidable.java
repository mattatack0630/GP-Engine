package physics.collidables;

import models.StaticModel;
import rendering.RenderData;
import rendering.renderers.MasterRenderer;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 8/25/2016.
 */
public abstract class Collidable
{
	public static final int AABB = 0;
	public static final int SPHERE = 1;
	public static final int PLANE = 2;
	public static final int POLY = 3;

	public int TYPE;

	public StaticModel model;
	public RenderData data;

	public Collidable(int type){this.TYPE = type;}

	public abstract void update(Vector3f p, Vector3f COM, Vector3f r);

	public abstract void tempRender(MasterRenderer renderer);
}
