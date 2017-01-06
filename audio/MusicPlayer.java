package audio;

import utils.math.Maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 1/4/2017.
 */
public class MusicPlayer
{
	private List<String> trackQueue;

	private StreamSound currentTrack;
	private Source currentSource;
	private float fadeLength;

	public MusicPlayer()
	{
		this.currentSource = Source.generateMusicSource(1);
		this.trackQueue = new ArrayList<>();
		this.fadeLength = 1f;
	}

	public void addTrackToEnd(String trackPath)
	{
		trackQueue.add(trackPath);
	}

	public void addTrackToBeginning(String trackPath)
	{
		trackQueue.add(0, trackPath);
	}

	public void addTrackAtIndex(String trackPath, int i)
	{
		trackQueue.add(i, trackPath);
	}

	public void updatePlayer()
	{
		if (currentTrack == null && !trackQueue.isEmpty())
		{
			currentTrack = StreamSound.fromWavFile(trackQueue.remove(0));
			AudioManager.play(currentTrack, currentSource);
		}

		if (currentTrack != null)
		{
			float duration = currentTrack.getDuration();
			float secondsLeft = currentTrack.calcSecondsLeft();

			// Testing fade in and out
			//float s = fadeLength / duration;
			//float d = (Maths.map(secondsLeft, duration, 0, 0, 1.0f) + 0.5f) % 1.0f;
			//float df = Maths.map(d, 0.5f - s, 0.5f + s, 0, 1);
			////if (d > 0.5f - s && d < 0.5f + s)
			//	fade(currentSource, df);

			// When the current source ends playing
			if (Maths.round(secondsLeft, 4) <= 0.0 && currentSource.lastPlayed() > 0.0)
			{
				AudioManager.stop(currentTrack, currentSource);
				currentTrack = null;
			}
		}
	}

	public void setFadeMode(int mode)
	{

	}

	public void setFadeLength(float seconds)
	{
		this.fadeLength = seconds;
	}

	public void fade(Source source0, float d)
	{
		float f = (float) ((Math.cos(2 * Math.PI * d) + 1) / 2.0f);
		source0.setGain(f);
	}
}

