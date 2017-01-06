package resources;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class SoundResource extends Resource
{
	private static final String SOUND_FOLDER = "res/audio/";
	private static final String SOUND_EXT = ".wav";

	private int soundLocId;

	public SoundResource(String name, String location)
	{
		super(name, SOUND_FOLDER + location + SOUND_EXT);
	}

	@Override
	public void load()
	{

	}

	@Override
	public void setId()
	{
		id = soundLocId;
	}

	@Override
	public void cleanUp()
	{

	}
}
