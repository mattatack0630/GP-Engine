package input.picker;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import rendering.fbo.FboObject;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 12/4/2016.
 */
public class PBODownloader
{
	private static final int BYTES_PER_PIXEL = 4;
	private static final int PBO_COUNT = 3; // less causes wait time, more causes picking lag

	private int[] PBOs;
	private int bufferSize;
	private ByteBuffer buffer;
	private byte[] results;

	private int writeIndex;
	private int readIndex;
	private int component;

	private int sizeX;
	private int sizeY;

	public PBODownloader(int component, int sx, int sy)
	{
		this.bufferSize = (sx * sy) * BYTES_PER_PIXEL; // Make variable size later
		this.buffer = BufferUtils.createByteBuffer(bufferSize);
		this.results = new byte[bufferSize];
		this.sizeX = sx;
		this.sizeY = sy;

		initPBOs(PBO_COUNT);
		this.writeIndex = 0;
		this.readIndex = PBOs.length - 1;

		this.component = component;
	}

	public void initPBOs(int pboSize)
	{
		PBOs = new int[pboSize];
		for (int i = 0; i < pboSize; i++)
			PBOs[i] = generatePbo();
	}

	public int generatePbo()
	{
		int pboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, pboId);
		GL15.glBufferData(GL21.GL_PIXEL_PACK_BUFFER, bufferSize, GL15.GL_STREAM_READ);
		GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);
		return pboId;
	}

	public byte[] getResults()
	{
		return results;
	}

	public void downloadData(FboObject fbo, int x, int y)
	{
		fbo.bindFrameBuffer();
		writeToPbo(x, y);
		readFromPbo();
		moveToNextPBO();
		GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);
		fbo.unbindFrameBuffer();
	}

	private void readFromPbo()
	{
		GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, PBOs[readIndex]);
		buffer = GL15.glMapBuffer(GL21.GL_PIXEL_PACK_BUFFER, GL15.GL_READ_ONLY, buffer);
		buffer.get(results);
		GL15.glUnmapBuffer(GL21.GL_PIXEL_PACK_BUFFER);
		buffer.flip();
	}

	private void writeToPbo(int x, int y)
	{
		GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, PBOs[writeIndex]);
		GL11.glReadPixels(x, y, sizeX, sizeY, component, GL11.GL_UNSIGNED_BYTE, 0); // change 1,1
	}

	private void moveToNextPBO()
	{
		writeIndex = (writeIndex + 1) % PBOs.length;
		readIndex = (readIndex + 1) % PBOs.length;
	}

	public void cleanUp()
	{
		GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);
		for (int pbo : PBOs)
			GL15.glDeleteBuffers(pbo);
	}

}
