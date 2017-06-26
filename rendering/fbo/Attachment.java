package rendering.fbo;

import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 12/9/2016.
 */
public abstract class Attachment
{
	protected Vector2f dimensions;
	protected int id;

	public Attachment(Vector2f dimensions)
	{
		this.dimensions = new Vector2f(dimensions);
		//this.id = generate();
	}

	public abstract int generate();

	public abstract void attachTo(FboObject fbo);

	public abstract void cleanUp();

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
