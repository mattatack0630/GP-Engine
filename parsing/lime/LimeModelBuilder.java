package parsing.lime;

import animation.Bone;
import models.RenderMaterial;
import parsing.utils.ParsingUtils;
import utils.VaoLoader;
import utils.VaoObject;
import utils.math.linear.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 11/30/2016.
 */
public class LimeModelBuilder
{
	private LimeData limeData;

	public LimeModelBuilder(LimeData data)
	{
		this.limeData = data;
	}

	public void finalizeData()
	{
		limeData.calculateTangents();

		buildBoneHierarchy(); // Set the parent of each bone after every bone in list. so bones in lime file can be in any order.
		orderBonesDepthFirst(); // Order the bone list into a parent first hierarchy
		convertSkinSidToIndices(); // Convert the skinBoneIndex vec4s from bone sids, to their indices in the list
		buildBones(); // finalize bone world mats, and inverse mats
		limeData.fillInBlanks(); // Check for none filled data, fill
	}

	public VaoObject buildStaticVao()
	{
		finalizeData();
		float[] verts = ParsingUtils.vec3ToFloatArray(limeData.getVertices());
		float[] tex = ParsingUtils.vec2ToFloatArray(limeData.getTexture());
		float[] norm = ParsingUtils.vec3ToFloatArray(limeData.getNormal());
		float[] tang = ParsingUtils.vec3ToFloatArray(limeData.getTangent());
		int[] index = ParsingUtils.vec3ToIntArray(limeData.getFaces());

		return VaoLoader.loadModel(verts, tex, norm, tang, index);
	}

	public VaoObject buildAnimatedVao()
	{
		finalizeData();

		float[] verts = ParsingUtils.vec3ToFloatArray(limeData.getVertices());
		float[] tex = ParsingUtils.vec2ToFloatArray(limeData.getTexture());
		float[] norm = ParsingUtils.vec3ToFloatArray(limeData.getNormal());
		float[] tang = ParsingUtils.vec3ToFloatArray(limeData.getTangent());
		float[] boneIndex = ParsingUtils.vec4ToFloatArray(limeData.getBoneSkinIndex());
		float[] boneWeight = ParsingUtils.vec4ToFloatArray(limeData.getBoneSkinWeight());
		int[] index = ParsingUtils.vec3ToIntArray(limeData.getFaces());

		return VaoLoader.loadModel(verts, tex, norm, tang, boneIndex, boneWeight, index);
	}

	public RenderMaterial buildMaterial()
	{
		RenderMaterial material = RenderMaterial.FLAT_SURFACE;
		float[] matData = limeData.getMaterialData();

		if (matData != null)
			material = new RenderMaterial(matData[0], matData[1], matData[2], matData[3], matData[4]);

		return material;
	}

	private void buildBones()
	{
		List<Bone> bones = limeData.getBones();
		for (Bone b : bones)
			b.buildPoses();
	}

	public void buildBoneHierarchy()
	{
		List<Bone> bones = limeData.getBones();
		for (Bone b : bones)
			if (b.getParentSid() != -1)
				b.setParent(limeData.getBoneFromSid(b.getParentSid()));
	}

	public void orderBonesDepthFirst()
	{
		List<Bone> oldBones = limeData.getBones();
		List<Bone> newBones = new ArrayList<>();

		for (Bone b : oldBones)
			if (b.getParent() == null) // if this is a root bone
				newBones.addAll(b.getBranching());

		limeData.setBones(newBones);
	}

	private void convertSkinSidToIndices()
	{
		List<Vector4f> boneSkinIndices = limeData.getBoneSkinIndex();
		for (Vector4f v : boneSkinIndices)
		{
			v.setX(limeData.getBoneIndexFromSid((int) v.x()));
			v.setY(limeData.getBoneIndexFromSid((int) v.y()));
			v.setZ(limeData.getBoneIndexFromSid((int) v.z()));
			v.setW(limeData.getBoneIndexFromSid((int) v.w()));
		}
	}
}