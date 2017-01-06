package resources;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 11/25/2016.
 */
public class TextureData
{
	private ByteBuffer byteBuffer;
	private float height;
	private float width;
	private int id;

	public TextureData(float width, float height, int id)
	{
		this(width, height, id, null);
	}

	public TextureData(float width, float height, int id, ByteBuffer byteBuffer)
	{
		this.byteBuffer = byteBuffer;
		this.height = height;
		this.width = width;
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public float getHeight()
	{
		return height;
	}

	public float getWidth()
	{
		return width;
	}

	public ByteBuffer getByteBuffer()
	{
		return byteBuffer;
	}
}
