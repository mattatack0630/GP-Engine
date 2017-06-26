package rendering;

/**
 * Created by mjmcc on 3/15/2017.
 */
public class VAOInstancePartition
{
	private int vaoAttribIndex;
	private int vboChunkOffset;
	private int dataSize;

	public VAOInstancePartition(int vaoAttribIndex, int vboChunkOffset, int dataSize)
	{
		this.vaoAttribIndex = vaoAttribIndex;
		this.vboChunkOffset = vboChunkOffset;
		this.dataSize = dataSize;
	}

	public int getVaoAttribIndex()
	{
		return vaoAttribIndex;
	}

	public int getVboChunkOffset()
	{
		return vboChunkOffset;
	}

	public int getDataSize()
	{
		return dataSize;
	}
}
