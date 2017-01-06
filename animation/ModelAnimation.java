package animation;

import utils.math.linear.matrix.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mjmcc on 10/29/2016.
 */
public class ModelAnimation
{
	private HashMap<Bone, ArrayList<Keyframe>> keyframes;
	private String animationName;

	public ModelAnimation(String name)
	{
		this.keyframes = new HashMap<>();
		this.animationName = name;
	}

	public void addKeyframe(Keyframe keyframe)
	{
		if (!keyframes.containsKey(keyframe.affectedBone))
			keyframes.put(keyframe.affectedBone, new ArrayList<>());

		keyframes.get(keyframe.affectedBone).add(keyframe);
	}

	// Use binary search
	public Matrix4f getBoneMatrix(Bone b, float time)
	{
		ArrayList<Keyframe> boneFrames = keyframes.containsKey(b) ? keyframes.get(b) : new ArrayList<>();

		float smallestDistC = Float.MAX_VALUE;
		float smallestDistF = Float.MAX_VALUE;
		Keyframe floorFrame = null;
		Keyframe ceilFrame = null;

		for (Keyframe k : boneFrames)
		{
			if (Math.abs(time - k.timeOn) < smallestDistF && time >= k.timeOn)
			{
				smallestDistF = Math.abs(time - k.timeOn);
				floorFrame = k;
			}
			if (Math.abs(time - k.timeOn) < smallestDistC && time <= k.timeOn)
			{
				smallestDistC = Math.abs(time - k.timeOn);
				ceilFrame = k;
			}
		}

		Matrix4f interpolatedMat = new Matrix4f(b.getInitialPose().matrix);
		if (floorFrame == null && ceilFrame != null)
			Matrix4f.mult(interpolatedMat, ceilFrame.transform.matrix, interpolatedMat);
		if (floorFrame != null && ceilFrame == null)
			Matrix4f.mult(interpolatedMat, floorFrame.transform.matrix, interpolatedMat);
		if (floorFrame != null && ceilFrame != null)
			Matrix4f.mult(interpolatedMat, Keyframe.interpolateFrames(floorFrame, ceilFrame, time), interpolatedMat);

		return interpolatedMat;
	}

	public String getName()
	{
		return animationName;
	}

	@Override
	public String toString()
	{
		String s = " Name : " + animationName;
		return s;
	}
}
