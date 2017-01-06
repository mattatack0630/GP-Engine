package resources;

/**
 * Created by mjmcc on 11/22/2016.
 */
public abstract class Resource
{
	protected String location;
	protected String name;
	protected int id;
	protected boolean isLoaded;

	public Resource(String name, String location)
	{
		this.name = name;
		this.location = location;
		this.isLoaded = false;
	}

	public abstract void load();

	public abstract void setId();

	public abstract void cleanUp();

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public boolean isLoaded()
	{
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded)
	{
		this.isLoaded = isLoaded;
	}

	@Override
	public boolean equals(Object o)
	{
		boolean e = false;

		if (o instanceof Resource)
		{
			Resource r = ((Resource) o);
			e = r.location.equals(this.location) && r.name == this.name;
		}
		return e;
	}
}
