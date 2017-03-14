package animation;

import utils.math.InterpType;
import utils.math.Interpolation;
import utils.math.Transform;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Quaternion;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 10/29/2016.
 *
 * The Keyframe class holds information about a keyframe in a skeletal animation
 */
public class Keyframe
{
	// The time this keyframe is setElements on
	public float timeOn;
	// The bone affected by this keyframe
	public Bone affectedBone;
	// The Transform associated with this keyframe
	public Transform transform;

	public Keyframe(float timeOn, Quaternion rotation, Vector3f position, Vector3f scale, Bone affectedBone)
	{
		this.timeOn = timeOn;
		this.affectedBone = affectedBone;
		this.transform = new Transform(position, rotation, scale);
	}

	/**
	 * Interpolate between two keyframes
	 */
	public static Matrix4f interpolateFrames(Keyframe floorFrame, Keyframe ceilFrame, float t)
	{
		if (floorFrame.equals(ceilFrame))
			return floorFrame.transform.matrix;

		Transform cTransform = ceilFrame.transform;
		Transform fTransform = floorFrame.transform;

		float time = (t - floorFrame.timeOn) / (ceilFrame.timeOn - floorFrame.timeOn);

		// if problem, look up slerp quat
		Quaternion rot = Interpolation.interpolateVector(InterpType.LINEAR, (Quaternion) fTransform.rotation,
				(Quaternion) cTransform.rotation, new Quaternion(), time);
		rot.normalize();

		Vector3f pos = Interpolation.interpolateVector(InterpType.LINEAR, fTransform.position, cTransform.position,
				new Vector3f(), time);

		Vector3f scale = Interpolation.interpolateVector(InterpType.LINEAR, fTransform.scale, cTransform.scale,
				new Vector3f(), time);

		Transform animTrans = new Transform(pos, rot, scale);
		return animTrans.matrix;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Keyframe)
		{
			Keyframe other = (Keyframe) o;
			return (other.timeOn == timeOn &&
					((other.transform.position.equals(transform.position) &&
							other.transform.scale.equals(transform.scale) &&
							other.transform.rotation.equals(transform.rotation)) || (other.transform.matrix.equals(transform.matrix))));
		}

		return false;
	}

	@Override
	public String toString()
	{
		String s = "";
		s += affectedBone;
		s += "Time " + timeOn;
		s += ", Pos : " + transform.position;
		s += ", Scale : " + transform.scale;
		s += ", Rot : " + transform.rotation;

		return s;
	}
}
