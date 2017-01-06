package animation;

import utils.math.Transform;
import utils.math.linear.matrix.Matrix4f;

import java.util.ArrayList;

/**
 * Created by mjmcc on 10/29/2016.
 */
public class Bone
{
	private int sid;
	private int parentSid;
	private Bone parent;
	private ArrayList<Bone> children;

	private Transform initialPose;
	private Matrix4f worldPose;
	private Matrix4f inverseWorldPose;

	/**
	 * Constructors
	 */
	public Bone(int sid, int pSid, Transform initialPose)
	{
		this.sid = sid;
		this.parentSid = pSid;
		this.children = new ArrayList<>();
		this.initialPose = initialPose;

		if (this.parent != null)
			this.parent.addChild(this);
	}

	public Bone(int sid, int parent, Matrix4f initialPose)
	{
		this(sid, parent, new Transform(initialPose));
	}

	/**
	 * Hierarchy methods
	 **/
	public void setParent(Bone b)
	{
		parent = b;
		parent.addChild(this);
	}

	/**
	 * Get the decedent bones branching from this bone in the
	 * Armature
	 *
	 * @return an ArrayList of decedent bones
	 */
	public ArrayList<Bone> getBranching()
	{
		ArrayList<Bone> branching = new ArrayList<>();

		branching.add(this);
		for (Bone b : children)
			branching.addAll(b.getBranching());

		return branching;
	}

	/**
	 * Get the parent bone of this bone
	 * @return this bone's parent
	 * */
	public Bone getParent()
	{
		return parent;
	}

	/**
	 * Add a child bone to this bone
	 * @param c the child bone to add
	 * */
	public void addChild(Bone c)
	{
		children.add(c);
	}

	/**
	 * Pose methods
	 **/
	public int getSid()
	{
		return sid;
	}

	public int getParentSid()
	{
		return parentSid;
	}

	public Matrix4f getWorldPose()
	{
		return worldPose;
	}

	public Transform getInitialPose()
	{
		return initialPose;
	}

	public Matrix4f getInverseWorldPose()
	{
		return inverseWorldPose;
	}

	public void buildPoses()
	{
		worldPose = new Matrix4f(initialPose.matrix);

		if (parent != null)
		{
			parent.buildPoses();
			Matrix4f.mult(parent.getWorldPose(), worldPose, worldPose);
		}

		inverseWorldPose = new Matrix4f(worldPose).invert();
	}

	/**
	 * Overridden Object Methods
	 */
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Bone))
			return false;

		Bone b = (Bone) o;
		return b.sid == this.sid;
	}

	@Override
	public String toString()
	{
		String b = "";
		b += "Bone @" + sid + " " + " : Parent @" + (parent == null ? -1 : parent.getSid());
		return b;
	}
}
