package audio;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import utils.math.linear.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

/**
 * The AudioManager Class
 * Created by mjmcc on 4/11/2016.
 * <p>
 * This class is used to manage audio playback in the game.
 */
public class AudioManager
{
	private static final float SOURCE_TIME_OUT = 5.0f;

	private static Map<Source, Sound> playingSources;

	/**
	 * Init audio Manager
	 * Called by the Engine class
	 */
	public static void init()
	{
		playingSources = new HashMap<>();

		// openAL prep
		createOpenAL();
		AL10.alDistanceModel(AL10.AL_INVERSE_DISTANCE_CLAMPED);
	}

	/**
	 * Generates the openAL instance used for playing in game audio.
	 */
	private static void createOpenAL()
	{
		try
		{
			AL.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Check for unused sources and sounds and return their resources back to
	 * the resource pool. This method find the state of a source (playing or not)
	 * and handles cleaning up the source if it is not in used.
	 * Called in the Engine update method.
	 */
	public static void cleanUnusedResources()
	{
		for (Source source : playingSources.keySet())
		{
			float lastPlayed = source.lastPlayed();
			boolean playing = lastPlayed < SOURCE_TIME_OUT;

			if (!playing)
			{
				source.releaseSourceId();
				playingSources.get(source).cleanSound();
				playingSources.remove(source);
			}
		}
	}

	/**
	 * Play a sound.
	 * This method plays a sound directly, while the entire
	 * audio byte array is held in memory. It is best to only call this method on
	 * smaller audio chunks (less than {@link AudioStreamer#CHUNK_SIZE}) that can be pre-loaded before playing.
	 *
	 * @param sound  the sound to be played directly
	 * @param source the source to emmit this sound from
	 */
	public static void play(DirectSound sound, Source source)
	{
		playingSources.put(source, sound);
		source.retrieveSourceId();
		sound.renewSound();

		AL10.alBufferData(sound.getBufferId(), sound.getAlFormat(), sound.getData(), sound.getSampleRate());
		AL10.alSourcei(source.getSourceId(), AL10.AL_BUFFER, sound.getBufferId());
		AL10.alSourcePlay(source.getSourceId());

		if (sound.isLooping())
			AL10.alSourcei(source.getSourceId(), AL10.AL_LOOPING, sound.isLooping() ?
					AL10.AL_TRUE : AL10.AL_FALSE);
	}

	/**
	 * This method plays in a audio stream. It uses the {@link AudioStreamer} to
	 * handle stream management after its initial call to the alPlay method. It is best
	 * to use this method when playing larger audio files (greater than {@link AudioStreamer#CHUNK_SIZE})
	 *
	 * @param sound  the sound to be streamed
	 * @param source the source to emmit this stream from
	 */
	public static void play(StreamSound sound, Source source)
	{
		playingSources.put(source, sound);
		source.retrieveSourceId();
		sound.renewSound();

		AudioStreamer.addStream(sound, source);
		AL10.alSourcePlay(source.getSourceId());
	}

	/**
	 * This method stops the given source using openAL's stopSource method.
	 *
	 * @param sound  the sound to be stopped
	 * @param source the source to be stopped
	 */
	public static void stop(DirectSound sound, Source source)
	{
		AL10.alSourceStop(source.getSourceId());

		playingSources.remove(sound);

		source.releaseSourceId();
		sound.cleanSound();
	}

	/**
	 * Stop an audio stream
	 * This method stops an audio stream by calling openAl's stopSource method.
	 * In addition, this method calls on the {@link AudioStreamer} remove method
	 * to remove and clean the audio stream.
	 *
	 * @param sound  the sound stream to be stopped and cleaned
	 * @param source the source to be stopped
	 */
	public static void stop(StreamSound sound, Source source)
	{
		AL10.alSourceStop(source.getSourceId());
		AL10.alSourceUnqueueBuffers(source.getSourceId());

		AudioStreamer.removeStream(sound);
		playingSources.remove(source);

		source.releaseSourceId();
		sound.cleanSound();
	}

	/**
	 * Pause a stream.
	 */
	public static void pause(StreamSound sound, Source source)
	{
		AL10.alSourcePause(source.getSourceId());
		AudioStreamer.removeStream(sound);
	}

	/**
	 * Pause a DirectSound.
	 */
	public static void pause(DirectSound sound, Source source)
	{
		AL10.alSourcePause(source.getSourceId());
	}

	/**
	 * Resume a Stream back to where it was paused.
	 * */
	public static void resume(StreamSound sound, Source source)
	{
		AL10.alSourcePlay(source.getSourceId());
		AudioStreamer.addStream(sound, source);
	}

	/**
	 * Resume a DirectSound back to where it was paused.
	 */
	public static void resume(DirectSound sound, Source source)
	{
		AL10.alSourcePlay(source.getSourceId());
	}

	/**
	 * Set openAL's listener params.
	 * This method uses a listener object to set openAL's listener parameters.
	 *
	 * @param listener the Listener object, whose data should be copied to openAL
	 */
	public static void setListener(Listener listener)
	{
		Vector3f position = listener.getPosition();
		Vector3f velocity = listener.getVelocity();

		AL10.alListener3f(AL10.AL_POSITION, position.x(), position.y(), position.z());
		AL10.alListener3f(AL10.AL_VELOCITY, velocity.x(), velocity.y(), velocity.z());
	}

	/**
	 * Clean up all buffers and sources, also destroys openAL instance.
	 */
	public static void cleanUp()
	{
		AL.destroy();
	}
}