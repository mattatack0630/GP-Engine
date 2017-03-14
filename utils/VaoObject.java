package utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import utils.math.linear.vector.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 8/23/2016.
 * The VaoObject class is a wrapper-ish class that is used to
 * access and modify shader vaoObject data in one convenient object
 */
public class VaoObject
{
	public static final int ELEMENT_INDEX = -2;
	public static final int POSITIONS = 0;
	public static final int TEXTURE_COORDS = 1;
	public static final int NORMALS = 2;
	public static final int TANGENTS = 3;
	public static final int BONE_INDEX = 4;
	public static final int BONE_WEIGHT = 5;

	public Vector3f maxPoint;
	public Vector3f minPoint;

	public int vaoId;
	public int vertexCount;
	private int attribCount;

	private Map<Integer, Integer> vboMap = new HashMap<>();

	public VaoObject()
	{
		vaoId = GL30.glGenVertexArrays();

		attribCount = 0;

		this.maxPoint = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
		this.minPoint = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
	}

	public void bind()
	{
		GL30.glBindVertexArray(vaoId);
		for (int i = 0; i < attribCount; i++)
			GL20.glEnableVertexAttribArray(i);
	}

	public void unbind()
	{
		for (int i = 0; i < attribCount; i++)
			GL20.glDisableVertexAttribArray(i);
		GL30.glBindVertexArray(0);
	}

	/**
	 * Adding a child vbo to this VaoObject
	 *
	 * Make Sure to call bind() before and unbind() after to properly add a vbo!
	 * @param vboData     the data to store in this vbo
	 * @param attribIndex the index in which to store this vbo, within the parent vao
	 */
	public void addAttribute(int attribIndex, float[] vboData, int dataSize)
	{
		int vboID = GL15.glGenBuffers();
		vboMap.put(attribIndex, vboID);
		attribCount++;

		//TODO fix this
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vboData.length);
		buffer.put(vboData);
		buffer.flip();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribIndex, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public void addInstanceAttribute(int attribIndex, float[] vboData, int dataSize)
	{
		int vboID = GL15.glGenBuffers();
		vboMap.put(attribIndex, vboID);
		attribCount++;

		FloatBuffer buffer = BufferUtils.createFloatBuffer(vboData.length);
		buffer.put(vboData);
		buffer.flip();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribIndex, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL33.glVertexAttribDivisor(attribIndex, 1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Set the element index data, aka the data that tells opengl what order to make shapes from
	 *
	 * @param indexArray the index array to setElements the order to
	 */
	public void setIndexArray(int[] indexArray)
	{
		int vboId = GL15.glGenBuffers();
		vboMap.put(ELEMENT_INDEX, vboId);
		vertexCount = indexArray.length;

		IntBuffer buffer = BufferUtils.createIntBuffer(indexArray.length);
		buffer.put(indexArray);
		buffer.flip();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	public void updateIndexArray(int[] indexArray)
	{
		int vboId = vboMap.get(ELEMENT_INDEX);
		vertexCount = indexArray.length;

		IntBuffer buffer = BufferUtils.createIntBuffer(indexArray.length);
		buffer.put(indexArray);
		buffer.flip();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	/**
	 * Update the data inside one of the child vbos
	 *
	 * The binding and unbinding methods are called within this method
	 * @param newData  the new data to be updated to
	 * @param attribId what index to store this data
	 */
	public void update(float[] newData, int attribId)
	{
		int vboId = vboMap.get(attribId);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(newData.length);
		buffer.put(newData);
		buffer.flip();

		GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}

	// TODO finish instance rendering
	public void updateInstanceAttrib(float[] newData, int attribId)
	{
		int vboId = vboMap.get(attribId);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(newData.length);
		buffer.put(newData);
		buffer.flip();

		GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
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
