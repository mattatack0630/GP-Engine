package audio;

import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 1/5/2017.
 */
public class DirectSound extends Sound
{
	// integer to represent no buffer
	private static final int NULL_BUFFER = -1;

	// audio file data
	private ByteBuffer audioData;
	private int sampleRate;
	private int alFormat;

	// audio playback data
	private boolean isLooping;
	private int bufferId;

	private DirectSound(ByteBuffer audioData, int sampleRate, int alFormat)
	{
		this.bufferId = NULL_BUFFER;
		this.alFormat = alFormat;
		this.audioData = audioData;
		this.sampleRate = sampleRate;
	}

	/**
	 * Get a buffer from the AudioResourcePool
	 */
	public void retrieveBuffer()
	{
		if (bufferId == NULL_BUFFER)
			bufferId = AudioResourcePool.retrieveBuffer();
	}

	/**
	 * Return this sound's buffer back to the AudioResourcePool
	 */
	public void releaseBuffer()
	{
		if (bufferId != NULL_BUFFER)
			AudioResourcePool.releaseBuffer(bufferId);
		bufferId = NULL_BUFFER;
	}

	/**
	 * renew this sound for another play.
	 * Mainly this method gets a new bufferId from the AudioResourcePool
	 */
	@Override
	public void renewSound()
	{
		retrieveBuffer();
	}

	/**
	 * Clean up the sound object
	 * Mainly this method releases its bufferId back to the AudioResourcePool
	 */
	@Override
	public void cleanSound()
	{
		releaseBuffer();
	}

	/***
	 * Getters and Setters
	 ***/

	public void setLooping(boolean looping)
	{
		this.isLooping = looping;
	}

	public int getSampleRate()
	{
		return sampleRate;
	}

	public ByteBuffer getData()
	{
		return audioData;
	}

	public boolean isLooping()
	{
		return isLooping;
	}

	public int getBufferId()
	{
		return bufferId;
	}

	public int getAlFormat()
	{
		return alFormat;
	}

	/**
	 * Generate a Sound from a wav file
	 */
	public static DirectSound fromWavFile(String fileName)
	{
		WaveData waveData = null;

		try
		{
			waveData = WaveData.create(new BufferedInputStream(new FileInputStream(fileName)));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return new DirectSound(waveData.data, waveData.samplerate, waveData.format);
	}
}
