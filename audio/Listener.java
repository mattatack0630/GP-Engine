package audio;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 4/11/2016.
 *
 * The listener is used to represent openAL's listener parameters
 */
public class Listener
{
	// The Listeners spacial parameters
	private Vector3f position;
	private Vector3f velocity;

	public Listener(Vector3f position, Vector3f velocity)
	{
		this.position = position;
		this.velocity = velocity;
	}

	/**
	 * Get the position of the listener
	 * <p>
	 * This is a record of the openAL's listener position param
	 * but is not guaranteed to be the same value.
	 */
	public Vector3f getPosition()
	{
		return position;
	}

	/**
	 * Get the velocity of the listener
	 * <p>
	 * This is a record of the openAL's listener velocity param
	 * but is not guaranteed to be the same value.
	 */
	public Vector3f getVelocity()
	{
		return velocity;
	}

	/**
	 * Set the listener position
	 * <p>
	 * This value is passed to the {@link AudioManager} which then sets
	 * the listener parameters
	 */
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	/**
	 * Set the listener velocity
	 *
	 * This value is passed to the {@link AudioManager} which then sets
	 * the listener parameters
	 * */
	public void setVelocity(Vector3f velocity)
	{
		this.velocity = velocity;
	}
}
