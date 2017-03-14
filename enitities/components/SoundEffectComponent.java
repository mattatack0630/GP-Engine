package enitities.components;

import audio.DirectSound;
import audio.Source;
import com.sun.istack.internal.Nullable;
import enitities.Entity;

/**
 * Created by mjmcc on 1/5/2017.
 */
public class SoundEffectComponent extends EntityComponent
{
	private Source source;
	private DirectSound shouldPlay;

	public SoundEffectComponent(Entity parent, Source source)
	{
		super(parent);
		this.source = source;
	}

	public Source getSound()
	{
		return source;
	}

	public void emitSound(@Nullable DirectSound shouldPlay)
	{
		this.shouldPlay = shouldPlay;
	}

	public DirectSound getShouldPlay()
	{
		return shouldPlay;
	}
}
