package audio;

import org.lwjgl.openal.AL10;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 1/2/2017.
 * <p>
 * The audio Streamer is used to manage audio streams.
 */
public class AudioStreamer
{
	// The size of each audio chunk in the stream
	public static final int CHUNK_SIZE = 48000;
	private static final int MAX_REFILL = 2;

	// Map to hold the pairs of Streams and their sources
	public static Map<StreamSound, Source> streams = new HashMap<>();

	/**
	 * Add a stream to the streaming list
	 *
	 * @param sound  the sound to stream
	 * @param source the source to emmit the stream from
	 */
	public static void addStream(StreamSound sound, Source source)
	{
		streams.put(sound, source);

		AudioStreamData streamData = sound.getStreamData();

		// Fill up initial buffers
		for (int i = 0; i < sound.getBuffersSize(); i++)
		{
			// Get next write buffer
			int wb = sound.getNextBuffer();
			// Write data into buffer
			AL10.alBufferData(wb, streamData.alFormat, streamData.loadNextData(), streamData.sampleRate);
			// Queue into source
			AL10.alSourceQueueBuffers(source.getSourceId(), wb);
		}
	}

	/**
	 * Remove stream the streaming list
	 *
	 * @param sound the stream to remove
	 */
	public static void removeStream(StreamSound sound)
	{
		streams.remove(sound);
	}

	/**
	 * Update the streams
	 * This method is used to refill the streams buffers when they
	 * begin to empty. As well, the method checks for dead streams and
	 * stops them or restarts them depending on the {@link StreamSound#isLooping} flag.
	 */
	public static void updateStreams()
	{
		for (StreamSound streamSound : streams.keySet())
		{
			Source source = streams.get(streamSound);


			// Get processed buffers
			int buffersProcessed = AL10.alGetSourcei(source.getSourceId(), AL10.AL_BUFFERS_PROCESSED);
			int refillBuffers = Math.min(buffersProcessed, MAX_REFILL);
			AL10.alSourceUnqueueBuffers(source.getSourceId());

			// Get Stream Data
			AudioStreamData streamData = streamSound.getStreamData();

			for (int i = 0; i < refillBuffers; i++)
			{
				// Get next write buffer
				int wb = streamSound.getNextBuffer();
				// Write data into buffer
				AL10.alBufferData(wb, streamData.alFormat, streamData.loadNextData(), streamData.sampleRate);
				// Queue into source
				AL10.alSourceQueueBuffers(source.getSourceId(), wb);
			}

			// check stream dead
			if (streamData.hasEnded() && source.lastPlayed() > 0.0f)
			{
				if (streamSound.isLooping())
					streamData.restart();
				else
					AudioManager.stop(streamSound, source);
			}
		}
	}
}
