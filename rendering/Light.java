package rendering;

import utils.math.linear.vector.Vector3f;

/**
 * Light Class
 *
 * The light class is used by the renderers to simulate light
 * */
public class Light
{
	//No attenuation used to non-attenuating sources like the sun
	public static final Vector3f NO_ATTENUATION = new Vector3f(1, 0, 0);

	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation; // The lights travel distance

	public Light(Vector3f position, Color color, Vector3f attenuation)
	{
		this.position = position;
		this.color = new Vector3f(color.getR(), color.getG(), color.getB());
		this.attenuation = attenuation;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = new Vector3f(position);
	}

	public Vector3f getColor()
	{
		return color;
	}

	public void setColor(Vector3f color)
	{
		this.color = color;
	}

	public Vector3f getAttenuation()
	{
		return attenuation;
	}

	public void setAttenuation(Vector3f attenuation)
	{
		this.attenuation = attenuation;
	}
}
