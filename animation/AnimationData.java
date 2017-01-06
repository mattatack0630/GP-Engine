package animation;

import utils.math.linear.matrix.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mjmcc on 10/31/2016.
 */
public class AnimationData
{
	// a map of animation with their names as keys
	private HashMap<String, ModelAnimation> animations;
	// an array list of bones, whose order is matching the order of bones in the vaoObject, parent->child
	private List<Bone> bones;

	/**
	 * Initialize a animation data with a set of animations and bones
	 */
	public AnimationData(HashMap<String, ModelAnimation> animations, List<Bone> bones)
	{
		this.animations = animations;
		this.bones = bones;
	}

	/**
	 * Get a list of matrices for each bone on this animation.
	 *
	 * @param time the time that the animation is at
	 * @return a list of matrices of the current animation frame.
	 * whose order is aligned with the inputFbo bone list
	 */
	public ArrayList<Matrix4f> getBoneAnimationMats(String animationOn, float time)
	{
		HashMap<Bone, Matrix4f> matList = new HashMap<>();
		ModelAnimation animData = animations.get(animationOn);

		// Get each bones relative animation mat
		// This will be init pose if there is no animation of that name
		for (Bone b : bones)
			matList.put(b, animData == null ? b.getInitialPose().matrix : animData.getBoneMatrix(b, time));

		// Multiply them in hierarchy, bones must be ordered parent -> child -> grandchild
		for (Bone b : bones)
		{
			if (b.getParent() != null)
			{
				Matrix4f bMat = matList.get(b);
				Matrix4f pMat = matList.get(b.getParent());
				Matrix4f mulMat = Matrix4f.mult(pMat, bMat, null);
				matList.put(b, mulMat);
			}
		}

		// Multiply by the inverse bind, for skinning to vertex
		ArrayList<Matrix4f> array = new ArrayList<>();
		for (Bone b : bones)
		{
			Matrix4f bMat = matList.get(b);
			Matrix4f mulMat = Matrix4f.mult(bMat, b.getInverseWorldPose(), null);
			array.add(mulMat);
		}

		return array;
	}
}
