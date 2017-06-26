package utils;

import org.lwjgl.opengl.GL15;
import rendering.VaoObject;

public class VaoLoader
{
	/** Raw Model (Obj Model) Loading Methods**/

	/**Basic Vao loading method*/
	public static VaoObject loadModel(int dataSize, float[] array, int vertCount)
	{
		VaoObject vaoObject = new VaoObject();
		vaoObject.bind();

		vaoObject.addAttribute(0, array, dataSize, GL15.GL_STATIC_DRAW);

		vaoObject.setVertexCount(vertCount);

		vaoObject.unbind();
		return vaoObject;
	}

	/**2D Model method*/
	public static VaoObject loadModel(float[] positions, float[] textureCoords)
	{
		VaoObject vaoObject = new VaoObject();
		vaoObject.bind();

		vaoObject.addAttribute(VaoObject.POSITIONS, positions, 2, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.TEXTURE_COORDS, textureCoords, 2, GL15.GL_STATIC_DRAW);
		vaoObject.setVertexCount(positions.length / 2);

		vaoObject.unbind();
		return vaoObject;
	}

	/**3D Model Method*/
	public static VaoObject loadModel(float[] positions, float[] textureCoords, float[] normals, float[] tangents,
									  int[] indicies)
	{
		VaoObject vaoObject = new VaoObject();

		vaoObject.bind();
		vaoObject.setIndexArray(indicies, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.POSITIONS, positions, 3, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.TEXTURE_COORDS, textureCoords, 2, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.NORMALS, normals, 3, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.TANGENTS, tangents, 3, GL15.GL_STATIC_DRAW);
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

		vaoObject.setIndexArray(indicies, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.POSITIONS, positions, 3, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.TEXTURE_COORDS, textureCoords, 2, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.NORMALS, normals, 3, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.TANGENTS, tangents, 3, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.BONE_INDEX, boneIndices, 4, GL15.GL_STATIC_DRAW);
		vaoObject.addAttribute(VaoObject.BONE_WEIGHT, boneWeights, 4, GL15.GL_STATIC_DRAW);

		vaoObject.unbind();
		return vaoObject;
	}
}