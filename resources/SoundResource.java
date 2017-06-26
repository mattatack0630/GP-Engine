package resources;

import audio.DirectSound;
import utils.math.Maths;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class SoundResource extends Resource
{
	private DirectSound sound;

	public SoundResource(String name, String location)
	{
		super(name, location);
	}

	@Override
	public void preloadOnDaemon()
	{
		sound = DirectSound.fromWavFile(location);
	}

	@Override
	public void load(ResourceManager resManager)
	{
	}

	@Override
	public void unload()
	{
		sound.cleanSound();
		sound.getData().clear();
	}

	@Override
	public void setId()
	{
		id = Maths.uniqueInteger();
	}

	public DirectSound getSound()
	{
		return sound;
	}
}
