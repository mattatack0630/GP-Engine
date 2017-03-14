package utils;

public class VaoLoader
{
	/** Raw Model (Obj Model) Loading Methods**/

	/**Basic Vao loading method*/
	public static VaoObject loadModel(int dataSize, float[] array)
	{
		VaoObject vaoObject = new VaoObject();
		vaoObject.bind();

		vaoObject.addAttribute(0, array, dataSize);

		vaoObject.vertexCount = array.length / 3;

		vaoObject.unbind();
		return vaoObject;
	}

	/**2D Model method*/
	public static VaoObject loadModel(float[] positions, float[] textureCoords)
	{
		VaoObject vaoObject = new VaoObject();
		vaoObject.bind();

		vaoObject.addAttribute(VaoObject.POSITIONS, positions, 2);
		vaoObject.addAttribute(VaoObject.TEXTURE_COORDS, textureCoords, 2);

		vaoObject.unbind();
		return vaoObject;
	}

	/**3D Model Method*/
	public static VaoObject loadModel(float[] positions, float[] textureCoords, float[] normals, float[] tangents,
									  int[] indicies)
	{
		VaoObject vaoObject = new VaoObject();

		vaoObject.bind();
		vaoObject.setIndexArray(indicies);
		vaoObject.addAttribute(VaoObject.POSITIONS, positions, 3);
		vaoObject.addAttribute(VaoObject.TEXTURE_COORDS, textureCoords, 2);
		vaoObject.addAttribute(VaoObject.NORMALS, normals, 3);
		vaoObject.addAttribute(VaoObject.TANGENTS, tangents, 3);
		vaoObject.setVertexCount(indicies.length);
		vaoObject.unbind();

		return vaoObject;
	}

	/**3D Bone-Animated Model Method*/
	public static VaoObject loadModel(float[] positions, float[] textureCoords, float[] normals, float[] tangents,
									  float[] boneIndices, float[] boneWeights, int[] indicies)
	{
		VaoObject vaoObject = new VaoObject();
		vaoObject.bind();

		vaoObject.setIndexArray(indicies);
		vaoObject.addAttribute(VaoObject.POSITIONS, positions, 3);
		vaoObject.addAttribute(VaoObject.TEXTURE_COORDS, textureCoords, 2);
		vaoObject.addAttribute(VaoObject.NORMALS, normals, 3);
		vaoObject.addAttribute(VaoObject.TANGENTS, tangents, 3);
		vaoObject.addAttribute(VaoObject.BONE_INDEX, boneIndices, 4);
		vaoObject.addAttribute(VaoObject.BONE_WEIGHT, boneWeights, 4);

		vaoObject.unbind();
		return vaoObject;
	}
}