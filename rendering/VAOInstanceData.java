package rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 3/15/2017.
 */
public class VAOInstanceData
{
	private int vboId;
	private FloatBuffer instanceBuffer;
	private List<VAOInstancePartition> partitions;
	private int maxInstances;
	private int chunkSize;

	public VAOInstanceData(int chunkSize, int maxInstances)
	{
		this.vboId = GL15.glGenBuffers();
		this.chunkSize = chunkSize;
		this.maxInstances = maxInstances;
		this.instanceBuffer = BufferUtils.createFloatBuffer(chunkSize * maxInstances);
		this.partitions = new ArrayList<>();
	}

	public void loadFloats(VAOInstancePartition partition, Float[] floats)
	{
		for (int i = 0; i < floats.length; i++)
		{
			float value = floats[i];
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.put(startIndex, value);
		}
	}

	public void loadVector2fs(VAOInstancePartition partition, Vector2f[] vecs)
	{
		for (int i = 0; i < vecs.length; i++)
		{
			Vector2f value = vecs[i];
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.put(startIndex + 0, value.x());
			instanceBuffer.put(startIndex + 1, value.y());
		}
	}

	public void loadVector3fs(VAOInstancePartition partition, Vector3f[] vecs)
	{
		for (int i = 0; i < vecs.length; i++)
		{
			Vector3f value = vecs[i];
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.put(startIndex + 0, value.x());
			instanceBuffer.put(startIndex + 1, value.y());
			instanceBuffer.put(startIndex + 2, value.z());
		}
	}

	public void loadVector4fs(VAOInstancePartition partition, Vector4f[] vecs)
	{
		for (int i = 0; i < vecs.length; i++)
		{
			Vector4f value = vecs[i];
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.put(startIndex + 0, value.x());
			instanceBuffer.put(startIndex + 1, value.y());
			instanceBuffer.put(startIndex + 2, value.z());
			instanceBuffer.put(startIndex + 3, value.w());
		}
	}

	public void loadMatrix4fs(VAOInstancePartition partition, Matrix4f[] mats)
	{
		for (int i = 0; i < mats.length; i++)
		{
			Matrix4f value = mats[i];
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.position(startIndex);
			Matrix4f.store(value, instanceBuffer);
		}
	}

	public void loadFloats(VAOInstancePartition partition, List<Float> floats)
	{
		for (int i = 0; i < floats.size(); i++)
		{
			float value = floats.get(i);
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.put(startIndex, value);
		}
	}

	public void loadVector2fs(VAOInstancePartition partition, List<Vector2f> vecs)
	{
		for (int i = 0; i < vecs.size(); i++)
		{
			Vector2f value = vecs.get(i);
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.put(startIndex + 0, value.x());
			instanceBuffer.put(startIndex + 1, value.y());
		}
	}

	public void loadVector3fs(VAOInstancePartition partition, List<Vector3f> vecs)
	{
		for (int i = 0; i < vecs.size(); i++)
		{
			Vector3f value = vecs.get(i);
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.put(startIndex + 0, value.x());
			instanceBuffer.put(startIndex + 1, value.y());
			instanceBuffer.put(startIndex + 2, value.z());
		}
	}

	public void loadVector4fs(VAOInstancePartition partition, List<Vector4f> vecs)
	{
		for (int i = 0; i < vecs.size(); i++)
		{
			Vector4f value = vecs.get(i);
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.put(startIndex + 0, value.x());
			instanceBuffer.put(startIndex + 1, value.y());
			instanceBuffer.put(startIndex + 2, value.z());
			instanceBuffer.put(startIndex + 3, value.w());
		}
	}

	public void loadMatrix4fs(VAOInstancePartition partition, List<Matrix4f> mats)
	{
		for (int i = 0; i < mats.size(); i++)
		{
			Matrix4f value = mats.get(i);
			int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
			instanceBuffer.position(startIndex);
			Matrix4f.store(value, instanceBuffer);
		}
	}

	public void loadFloat(VAOInstancePartition partition, Float f, int i)
	{
		int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
		instanceBuffer.put(startIndex + 0, f);
	}

	public void loadVector2f(VAOInstancePartition partition, Vector2f vec, int i)
	{
		int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
		instanceBuffer.put(startIndex + 0, vec.x());
		instanceBuffer.put(startIndex + 1, vec.y());
	}

	public void loadVector3f(VAOInstancePartition partition, Vector3f vec, int i)
	{
		int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
		instanceBuffer.put(startIndex + 0, vec.x());
		instanceBuffer.put(startIndex + 1, vec.y());
		instanceBuffer.put(startIndex + 2, vec.z());
	}

	public void loadVector4f(VAOInstancePartition partition, Vector4f vec, int i)
	{
		int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
		instanceBuffer.put(startIndex + 0, vec.x());
		instanceBuffer.put(startIndex + 1, vec.y());
		instanceBuffer.put(startIndex + 2, vec.z());
		instanceBuffer.put(startIndex + 3, vec.w());
	}

	public void loadMatrix4f(VAOInstancePartition partition, Matrix4f mat, int i)
	{
		int startIndex = (chunkSize * i) + partition.getVboChunkOffset();
		instanceBuffer.position(startIndex);
		Matrix4f.store(mat, instanceBuffer);
	}

	public List<VAOInstancePartition> getPartitions()
	{
		return partitions;
	}

	public void setPartitions(List<VAOInstancePartition> partitions)
	{
		this.partitions = partitions;
	}

	public void setPartitions(VAOInstancePartition... partitions)
	{
		for (VAOInstancePartition partition : partitions)
		{
			this.partitions.add(partition);
		}
	}

	public int getVboId()
	{
		return vboId;
	}

	public FloatBuffer getInstanceBuffer()
	{
		return instanceBuffer;
	}

	public int getMaxInstances()
	{
		return maxInstances;
	}

	public int getChunkSize()
	{
		return chunkSize;
	}
}
