package resources;

import audio.DirectSound;
import utils.math.Maths;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class SoundResource extends Resource
{
	private static final String SOUND_FOLDER = "res/audio/";
	private static final String SOUND_EXT = ".wav";

	private DirectSound sound;

	public SoundResource(String name, String location)
	{
		super(name, SOUND_FOLDER + location + SOUND_EXT);
	}

	@Override
	public void load()
	{
		sound = DirectSound.fromWavFile(location);
	}

	@Override
	public void setId()
	{
		id = Maths.uniqueInteger();
	}

	@Override
	public void cleanUp()
	{
		sound.cleanSound();
		sound.getData().clear();
	}

	public DirectSound getSound()
	{
		return sound;
	}
}
