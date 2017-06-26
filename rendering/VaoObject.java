package rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import utils.math.linear.vector.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 8/23/2016.
 * The VaoObject class is a wrapper-ish class that is used to
 * access and modify shader vaoObject data in one convenient object
 */
public class VaoObject
{
	private static final int MAX_VERTICES = 100000;
	public static final int ELEMENT_INDEX = -2;
	public static final int POSITIONS = 0;
	public static final int TEXTURE_COORDS = 1;
	public static final int NORMALS = 2;
	public static final int TANGENTS = 3;
	public static final int BONE_INDEX = 4;
	public static final int BONE_WEIGHT = 5;

	public static FloatBuffer attributeBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 4);
	public static IntBuffer indexBuffer = BufferUtils.createIntBuffer(MAX_VERTICES * 1);
	private static int boundVao = 0;

	public Vector3f maxPoint; // remove?
	public Vector3f minPoint; // remove?

	public int vaoId;
	public int vertexCount;
	private List<Integer> usedAttributes;

	private Map<Integer, Integer> vboMap = new HashMap<>();

	public VaoObject()
	{
		this.vaoId = GL30.glGenVertexArrays();
		this.usedAttributes = new ArrayList<>();
		this.maxPoint = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
		this.minPoint = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
	}

	public void bind()
	{
		if (boundVao != vaoId)
		{
			GL30.glBindVertexArray(vaoId);
			for (Integer i : usedAttributes)
				GL20.glEnableVertexAttribArray(i);
			boundVao = vaoId;
		}
	}

	public void unbind()
	{
		if (boundVao == vaoId)
		{
			for (Integer i : usedAttributes)
				GL20.glDisableVertexAttribArray(i);
			GL30.glBindVertexArray(0);
			boundVao = 0;
		}
	}

	/**
	 * Adding a child vbo to this VaoObject
	 * <p>
	 * Make Sure to call bind() before and unbind() after to properly add a vbo!
	 *
	 * @param vboData     the data to store in this vbo
	 * @param attribIndex the index in which to store this vbo, within the parent vao
	 */
	public void addAttribute(int attribIndex, float[] vboData, int dataSize, int bufferUsage)
	{
		int vboID = GL15.glGenBuffers();
		vboMap.put(attribIndex, vboID);
		usedAttributes.add(attribIndex);

		attributeBuffer.limit(vboData.length);
		attributeBuffer.put(vboData);
		attributeBuffer.flip();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, attributeBuffer, bufferUsage);
		GL20.glVertexAttribPointer(attribIndex, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Set instance attribute
	 */
	public void setInstanceAttribute(VAOInstanceData instanceData)
	{
		FloatBuffer instBuffer = instanceData.getInstanceBuffer();
		instBuffer.position(instBuffer.capacity());
		instBuffer.flip();

		GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, instanceData.getVboId());
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, instBuffer.capacity() * 4, GL15.GL_STREAM_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, instBuffer);

		List<VAOInstancePartition> partitions = instanceData.getPartitions();

		for (int i = 0; i < partitions.size(); i++)
		{
			VAOInstancePartition parti = partitions.get(i);
			int dataSize = parti.getDataSize();
			int chunkOff = parti.getVboChunkOffset();
			int attribIndex = parti.getVaoAttribIndex();

			for (int j = 0; j < dataSize; j += 4)
			{
				int clippedDataSize = Math.min(dataSize - j, 4);

				GL20.glVertexAttribPointer(attribIndex + (j / 4), clippedDataSize, GL11.GL_FLOAT, false,
						instanceData.getChunkSize() * 4, (chunkOff + (j)) * 4);

				GL33.glVertexAttribDivisor(attribIndex + (j / 4), 1);

				usedAttributes.add(attribIndex + (j / 4));
			}
		}

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}

	/**
	 * Make sure to call this method before binding/ rendering, as
	 * it binds and unbinds this vao while executing.
	 */
	public void updateInstanceAttribute(VAOInstanceData instanceData)
	{
		FloatBuffer instBuffer = instanceData.getInstanceBuffer();
		instBuffer.position(instBuffer.capacity());
		instBuffer.flip();

		if (boundVao != vaoId) GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, instanceData.getVboId());
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, instBuffer.capacity() * Float.BYTES, GL15.GL_STREAM_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, instBuffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		if (boundVao != vaoId) GL30.glBindVertexArray(0);
	}

	/**
	 * Set the element index data, aka the data that tells opengl what order to make shapes from
	 *
	 * @param indexArray the index array to setElements the order to
	 */
	public void setIndexArray(int[] indexArray, int bufferUsage)
	{
		int vboId = GL15.glGenBuffers();
		vboMap.put(ELEMENT_INDEX, vboId);
		vertexCount = indexArray.length;

		indexBuffer.limit(indexArray.length);
		indexBuffer.put(indexArray);
		indexBuffer.flip();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, bufferUsage);
	}

	/**
	 * Update the index array of this VAO
	 */
	public void updateIndexArray(int[] indexArray, int bufferUsage)
	{
		int vboId = vboMap.get(ELEMENT_INDEX);
		vertexCount = indexArray.length;

		indexBuffer.limit(indexArray.length);
		indexBuffer.put(indexArray);
		indexBuffer.flip();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, bufferUsage);
	}

	/**
	 * Update the data inside one of the child vbos
	 * <p>
	 * The binding and unbinding methods are called within this method
	 *
	 * @param newData  the new data to be updated to
	 * @param attribId what index to store this data
	 */
	public void update(float[] newData, int attribId, int bufferUsage)
	{
		int vboId = vboMap.get(attribId);

		attributeBuffer.limit(newData.length);
		attributeBuffer.put(newData);
		attributeBuffer.flip();

		if (boundVao != vaoId) GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, attributeBuffer, bufferUsage);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		if (boundVao != vaoId) GL30.glBindVertexArray(0);
	}

	/**
	 * Clean up/ Delete the vbos and vaos
	 */
	public void clean()
	{
		GL30.glDeleteVertexArrays(vaoId);

		for (int vbo : vboMap.values())
			GL15.glDeleteBuffers(vbo);
	}

	public int getId()
	{
		return vaoId;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}

	public void setVertexCount(int vertexCount)
	{
		this.vertexCount = vertexCount;
	}
}
