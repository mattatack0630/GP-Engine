package audio;

import engine.Engine;
import org.lwjgl.openal.AL10;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 1/2/2017.
 * <p>
 * The source class is used to represent a openAL source,
 * which can be used to play sounds and streams.
 *
 * @author mjmcc
 */
public class Source
{
	// Integer constant to represent no source id has been given
	private static final int NULL_SOURCE = -1;

	// id of the openAl source
	private int sourceId;

	// Spacial parameters
	private Vector3f position;
	private Vector3f velocity;

	// Source parameters
	private float gain;
	private float pitch;
	private float refDist;
	private float rollOff;
	private float radius;
	private boolean isRelative;

	// play timeout
	float lastPlayed;

	public Source(Vector3f position, Vector3f velocity, float gain, float pitch, float refDist,
				  float rollOff, boolean isRelative)
	{
		this.sourceId = NULL_SOURCE;

		this.velocity = velocity;
		this.position = position;

		this.gain = gain;
		this.pitch = pitch;
		this.refDist = refDist;
		this.rollOff = rollOff;
		this.isRelative = isRelative;

		this.lastPlayed = Engine.getTime();
	}

	/**
	 * Update all the spacial parameters in the openAL source at once
	 */
	public void updateSpacialParams()
	{
		if (hasSourceId())
		{
			AL10.alSource3f(sourceId, AL10.AL_POSITION, position.x(), position.y(), position.z());
			AL10.alSource3f(sourceId, AL10.AL_VELOCITY, velocity.x(), velocity.y(), velocity.z());
		}
	}

	/**
	 * Update all the source parameters in the openAL source at once
	 */
	public void updateSourceParams()
	{
		if (hasSourceId())
		{
			AL10.alSourcef(sourceId, AL10.AL_GAIN, gain);
			AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
			AL10.alSourcef(sourceId, AL10.AL_ROLLOFF_FACTOR, rollOff);
			AL10.alSourcef(sourceId, AL10.AL_REFERENCE_DISTANCE, refDist);
			//AL10.alSourcei(sourceId, AL10.AL_SOURCE_RELATIVE, isRelative ? AL10.AL_TRUE : AL10.AL_FALSE);
		}
	}

	/**
	 * Gets a source id from the AudioResourcePool and syncs its
	 * parameters this objects parameters. If this source Object already has a source id
	 * then this method doesn't do anything.
	 *
	 * @return the sourceId that was retrieved (or {@link #NULL_SOURCE} if it failed)
	 */
	public int retrieveSourceId()
	{
		if (!hasSourceId())
		{
			sourceId = AudioResourcePool.retrieveSource();
			updateSourceParams();
			updateSpacialParams();
		}

		return sourceId;
	}

	/**
	 * Return this objects source id back to the AudioResourcePool.
	 * If this source object has no source id, then this method doesnt
	 * return anything to the pool.
	 */
	public void releaseSourceId()
	{
		if (hasSourceId())
			AudioResourcePool.releaseSource(sourceId);
		sourceId = NULL_SOURCE;
	}

	/**
	 * Get the last time this source was played or paused
	 * TODO make this not update all the time
	 */
	public float lastPlayed()
	{
		int state = AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE);
		boolean playing = (state == AL10.AL_PLAYING || state == AL10.AL_PAUSED);
		float now = Engine.getTime();

		lastPlayed = playing ? now : lastPlayed;

		return (now - lastPlayed);
	}

	/***** Getters and Setters *****/

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public void setVelocity(Vector3f velocity)
	{
		this.velocity = velocity;
	}

	public void setRefDist(float refDist)
	{
		this.refDist = refDist;
	}

	public void setRollOff(float rollOff)
	{
		this.rollOff = rollOff;
	}

	public void setRadius(float radius)
	{
		this.radius = radius;
	}

	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}

	public void setGain(float gain)
	{
		this.gain = gain;
	}

	public float getGain()
	{
		return gain;
	}

	public boolean hasSourceId()
	{
		return sourceId != NULL_SOURCE;
	}

	public int getSourceId()
	{
		return sourceId;
	}

	/**** Static generators *****/

	/**
	 * Generate an ambient source to play more long range sounds
	 *
	 * @param radius the radius in which this source can be heard
	 */
	public static Source generateAmbientSource(Vector3f pos, float radius)
	{
		return new Source(pos, new Vector3f(), 1, 1, radius, 12, false);
	}

	/**
	 * Generate a point source to play sounds from a point in space
	 *
	 * @param radius the radius in which this source can be heard
	 */
	public static Source generatePointSource(Vector3f pos, float radius)
	{
		return new Source(pos, new Vector3f(), 1, 1, radius, 5, false);
	}

	/**
	 * Generate a music source.
	 * When this source type is generated, the source will play at the
	 * given gain value no matter where the listener is relative
	 * to the source.
	 *
	 * @param gain the gain or volume to set the source to
	 * @return the newly generated source object
	 */
	public static Source generateMusicSource(float gain)
	{
		return new Source(new Vector3f(), new Vector3f(), gain, 1, 1, 0, true);
	}

}
