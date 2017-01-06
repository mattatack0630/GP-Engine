package rendering.fbo;

/**
 * Created by mjmcc on 12/9/2016.
 */
public abstract class Attachment
{
	protected int id;

	public Attachment(int id)
	{
		this.id = id;
	}

	public abstract void cleanUp();

	public int getId()
	{
		return id;
	}
}
