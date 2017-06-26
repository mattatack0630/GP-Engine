package audio;

import org.lwjgl.openal.AL10;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mjmcc on 1/2/2017.
 *
 * @author mjmcc
 *         <p>
 *         The AudioResourcePool class is used to manage pregenerated sources and buffers.
 */
public class AudioResourcePool
{
	// max number of source and buffers to generate
	private static final int SOURCES = 25;
	private static final int BUFFERS = 40;

	// Lists used to keep track of sources
	public static List<Integer> sources = new LinkedList<>();
	private static List<Integer> usedSources = new LinkedList<>();

	// Lists used keep track of buffers
	private static List<Integer> buffers = new LinkedList<>();
	private static List<Integer> usedBuffers = new LinkedList<>();

	/**
	 * Initialize an amount of sources and buffers.
	 * This method generates the amount specified by the {@link #SOURCES}
	 * and {@link #BUFFERS} constants
	 */
	public static void initResources()
	{
		// Possibly dynamic
		for (int i = 0; i < BUFFERS; i++)
			buffers.add(AL10.alGenBuffers());
		for (int i = 0; i < SOURCES; i++)
			sources.add(AL10.alGenSources());
	}

	/**
	 * Retrieve a buffer from the unused buffers list.
	 * If all pre-generated buffers are used up, this method will throw an
	 * IndexOutOfBounds Exception.
	 *
	 * @return the integer id of the next available buffer
	 * @throws IndexOutOfBoundsException if the buffers are used up
	 */
	public static int retrieveBuffer()
	{
		if (buffers.isEmpty())
			System.err.println("NOT ENOUGH BUFFERS");

		usedBuffers.add(buffers.get(0));
		return buffers.remove(0);
	}

	/**
	 * Retrieve a source from the unused sources list.
	 * If all pre-generated sources are used up, this method will throw an
	 * IndexOutOfBounds Exception.
	 *
	 * @return the integer id of the next available source
	 * @throws IndexOutOfBoundsException if the sources are used up
	 */
	public static int retrieveSource()
	{
		if (sources.isEmpty())
			System.err.println("NOT ENOUGH SOURCES");

		usedSources.add(sources.get(0));
		return sources.remove(0);
	}

	/**
	 * Release a buffer back to the audio resource pool.
	 * Call this method when a buffer is finished being used.
	 *
	 * @param buffer the buffer id to reintroduce to the resource pool
	 */
	public static void releaseBuffer(Integer buffer)
	{
		if (usedBuffers.contains(buffer))
		{
			usedBuffers.remove(buffer);
			buffers.add(buffer);
		}
	}

	/**
	 * Release a source back to the audio resource pool.
	 * Call this method when a source is finished being used.
	 *
	 * @param source the buffer id to reintroduce to the resource pool
	 */
	public static void releaseSource(Integer source)
	{
		if (usedSources.contains(source))
		{
			AL10.alSourcei(source, AL10.AL_BUFFER, AL10.AL_NONE);
			usedSources.remove(source);
			sources.add(source);
		}
	}

	/**
	 * Clean up the buffers and sources at the end of the game
	 * Call this method before stopping openAL to delete all the pools sources
	 * and buffers.
	 */
	public static void cleanUp()
	{
		for (int b : buffers)
			AL10.alDeleteBuffers(b);
		for (int b : usedBuffers)
			AL10.alDeleteBuffers(b);
		for (int s : sources)
			AL10.alDeleteSources(s);
		for (int s : usedSources)
			AL10.alDeleteSources(s);
	}
}
