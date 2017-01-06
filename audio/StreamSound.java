package audio;

import javax.sound.sampled.AudioFormat;

/**
 * Created by mjmcc on 1/2/2017.
 * <p>
 * The sound class is used to hold information about
 * larger audio streams, it is best to use this class
 * for audio file greater than {@link AudioStreamer#CHUNK_SIZE}
 */
public class StreamSound extends Sound
{
	// The amount of buffers to supply each stream with
	private static final int BUFFERS_AMOUNT = 3;
	private static final int NULL_BUFFER = -1;

	// Stream data used to load audio data in chunks
	public AudioStreamData streamData;
	private boolean isLooping;

	// Buffer array used to store chunks of audio data
	private boolean hasBuffers;
	private int[] buffers;
	private int bufferOn;

	public StreamSound(AudioStreamData streamData)
	{
		this.streamData = streamData;

		this.buffers = new int[BUFFERS_AMOUNT];
		this.hasBuffers = false;
		this.bufferOn = 0;

		this.isLooping = false;
	}

	/**
	 * Get amount buffers equal to the {@link #BUFFERS_AMOUNT} constant
	 * if this sound does not already have buffers and set {@code hasBuffers} variable to true.
	 * */
	public void retrieveBuffers()
	{
		if (!hasBuffers)
			for (int i = 0; i < buffers.length; i++)
				buffers[i] = AudioResourcePool.retrieveBuffer();

		hasBuffers = true;
	}

	/**
	 * Return all of this sounds buffers (if it has any) back to the AudioResourcePool.
	 * This method also sets the {@code hasBuffers} variable to false and sets each buffer
	 * in the buffer array to {@link #NULL_BUFFER}
	 */
	public void releaseBuffers()
	{
		if (hasBuffers)
		{
			for (int i = 0; i < buffers.length; i++)
			{
				AudioResourcePool.releaseBuffer(buffers[i]);
				buffers[i] = NULL_BUFFER;
			}
		}

		hasBuffers = false;
	}

	/**
	 * Clean up the audio stream
	 * Mainly this method releases its buffers back to the
	 * AudioResourcePool and closes the stream.
	 */
	@Override
	public void cleanSound()
	{
		releaseBuffers();
		streamData.close();
	}

	/**
	 * Renew this sound to play again
	 * Mainly this method retrieves buffers (if it has none) and restarts the stream
	 * if has ended.
	 */
	@Override
	public void renewSound()
	{
		retrieveBuffers();
		if (streamData.hasEnded())
			streamData.restart();
	}

	/**
	 * Calculate the amount of seconds left in this StreamSound
	 *
	 * @return the amount in seconds left until this StreamSound ends
	 */
	public float calcSecondsLeft()
	{
		float totalTime = streamData.getDuration();
		AudioFormat format = streamData.getFormat();
		int bytesRead = streamData.getBytesRead();
		int sampleRate = streamData.getSampleRate();
		float div = (sampleRate * format.getChannels() * format.getSampleSizeInBits() / 8);
		float timeAt = bytesRead / div;

		return (totalTime - timeAt);
	}

	/**
	 * Get the next buffer in the buffers array
	 * <p>
	 * This method gets the next write buffer for this stream
	 * and increments the bufferOn variable
	 *
	 * @return the next writable buffer's id
	 */
	public int getNextBuffer()
	{
		return buffers[(bufferOn++) % buffers.length];
	}

	/****** Getters and Setters ******/

	public void setLooping(boolean looping)
	{
		isLooping = looping;
	}

	public float getDuration()
	{
		return streamData.getDuration();
	}

	public AudioStreamData getStreamData()
	{
		return streamData;
	}

	public int getBuffersSize()
	{
		return buffers.length;
	}

	public boolean isLooping()
	{
		return isLooping;
	}

	/**** Static generator methods ****/

	/**
	 * Load a streamSound from a WAV file
	 *
	 * @return the created StreamSound object
	 * @throws java.io.FileNotFoundException when the file does not exist
	 */
	public static StreamSound fromWavFile(String fileName)
	{
		AudioStreamData streamData = AudioStreamData.openWavStream(fileName, AudioStreamer.CHUNK_SIZE);
		return new StreamSound(streamData);
	}

	public static StreamSound fromMP4File(String fileName)
	{
		return null;
	}

	public static StreamSound fromOGGFile(String fileName)
	{
		return null;
	}

}
