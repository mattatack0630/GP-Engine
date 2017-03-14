package physics.simphysics;

import utils.math.geom.AABB;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 1/23/2017.
 */
public class SimpleCollisionBody
{
	private static final int MAX_IGNORE_MASKS = 15;
	private static final int IGNORE_ALL = 112;
	private static final int IGNORE_NONE = 113;
	private static final int IGNORE_MASKS = 114;

	private AABB bounds;
	private boolean isDynamic;

	private int collidesMask;
	private int[] ignoreMasks;
	private int ignoreAmount;
	private int ignoreMode;

	public SimpleCollisionBody(int collidesMask, AABB bounds, boolean isDynamic)
	{
		this.bounds = bounds;
		this.isDynamic = isDynamic;
		this.ignoreAmount = 0;
		this.ignoreMasks = new int[MAX_IGNORE_MASKS];
		this.ignoreMode = IGNORE_MASKS;
		this.collidesMask = collidesMask;
	}

	public void translate(Vector3f t)
	{
		bounds.setCenter(bounds.getCenter().add(t));
	}

	public Vector3f getPos()
	{
		return bounds.getCenter();
	}

	public AABB getBounds()
	{
		return bounds;
	}

	public boolean isDynamic()
	{
		return isDynamic;
	}

	public void setPos(Vector3f pos)
	{
		this.bounds.setCenter(pos);
	}

	public void setBounds(AABB bounds)
	{
		this.bounds = bounds;
	}

	public void setDynamic(boolean dynamic)
	{
		isDynamic = dynamic;
	}

	public void setIgnoreMode(int type)
	{
		this.ignoreMode = type;
	}

	public void setCollidesMask(int collidesMask)
	{
		this.collidesMask = collidesMask;
	}

	public void addIgnoreMask(int mask)
	{
		ignoreMasks[ignoreAmount] = mask;
		ignoreAmount = Math.min((ignoreAmount + 1), ignoreMasks.length - 1);
	}

	public boolean shouldIgnore(int mask)
	{
		if (ignoreMode == IGNORE_MASKS)
			for (int i = 0; i < ignoreAmount; i++)
				if (ignoreMasks[i] == mask)
					return true;

		if (ignoreMode == IGNORE_ALL)
			return true;

		if (ignoreMode == IGNORE_NONE)
			return false;

		return false;
	}

	public int getMask()
	{
		return collidesMask;
	}
}
