package enitities.systems;

import audio.DirectSound;
import audio.Source;
import engine.Engine;
import enitities.Entity;
import enitities.components.SoundEffectComponent;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 1/5/2017.
 */
public class SoundEffectEmissionSystem extends EntitySystem
{
	private Map<Entity, SoundEffectComponent> component;

	public SoundEffectEmissionSystem()
	{
		component = new HashMap<>();
	}

	@Override
	public void tick()
	{
		for (Entity e : component.keySet())
		{
			SoundEffectComponent soundEffectComponent = component.get(e);
			DirectSound sound = soundEffectComponent.getShouldPlay();
			Source source = soundEffectComponent.getSound();

			source.setPosition(e.getPosition());
			source.updateSpacialParams();

			if (sound != null)
			{
				Engine.getAudioManager().play(sound, source);
				soundEffectComponent.emitSound(null);
			}
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{
	}

	@Override
	public void setupNeededComponents()
	{
		super.addNeededComponent(SoundEffectComponent.class, component);
	}
}
