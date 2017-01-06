package audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by ThinMatrix
 * <p>
 * A class that is used to stream from a wav file.
 */
public class AudioStreamData
{
	final int alFormat;
	final int sampleRate;
	final int totalBytes;
	final int bytesPerFrame;
	private float duration;
	final AudioFormat format;
	final String streamFilePath;

	private final int chunkSize;
	private AudioInputStream audioStream;

	private final ByteBuffer buffer;
	private final byte[] data;

	private int totalBytesRead = 0;
	private boolean isClosed = false;

	private AudioStreamData(String filePath, AudioInputStream stream, int chunkSize)
	{
		this.audioStream = stream;
		this.chunkSize = chunkSize;
		this.format = stream.getFormat();
		this.alFormat = getOpenAlFormat(format.getChannels(), format.getSampleSizeInBits());
		this.buffer = BufferUtils.createByteBuffer(chunkSize);
		this.data = new byte[chunkSize];
		this.sampleRate = (int) format.getSampleRate();
		this.bytesPerFrame = format.getFrameSize();
		this.totalBytes = (int) (stream.getFrameLength() * bytesPerFrame);
		this.streamFilePath = filePath;
		this.duration = (float) totalBytes / (sampleRate * format.getChannels() * format.getSampleSizeInBits() / 8.0f);
	}

	/**
	 * TODO Find a better solution to this
	 * Restarts the audioStream at the beginning of the file
	 */
	public void restart()
	{
		try
		{
			InputStream bufferedInput = new BufferedInputStream(new FileInputStream(streamFilePath));
			audioStream = AudioSystem.getAudioInputStream(bufferedInput);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		totalBytesRead = 0;
		isClosed = false;
	}

	/**
	 * Loads the next chunk of data from the .wav file into a ByteBuffer. The
	 * amount of bytes that it attempts to load is determined by the
	 * {@code chunkSize} argument when the
	 * openWavStream()} method was called to create this stream. The actual
	 * number of bytes loaded may be less depending on how close to the end of
	 * the stream it is, or if the {@code chunkSize} doesn't represent an
	 * integer number of audio frames.
	 *
	 * @return The loaded byte buffer.
	 */
	public ByteBuffer loadNextData()
	{
		try
		{
			int bytesRead = audioStream.read(data, 0, chunkSize);
			totalBytesRead += bytesRead;

			buffer.clear();
			if (bytesRead != -1)
				buffer.put(data, 0, bytesRead);

			buffer.flip();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Couldn't read more bytes from audio stream!");
			System.exit(-1);
		}
		return buffer;
	}

	/**
	 * @return {@code true} if the stream has read all the audio data and
	 * reached the end of the data.
	 * TODO not always accurate
	 */
	public boolean hasEnded()
	{
		return totalBytesRead >= totalBytes || isClosed;
	}

	/**
	 * @return The total number of bytes in the audio data.
	 */
	public int getTotalBytes()
	{
		return totalBytes;
	}


	public AudioFormat getFormat()
	{
		return format;
	}

	public int getBytesRead()
	{
		return totalBytesRead;
	}

	public int getSampleRate()
	{
		return sampleRate;
	}

	/**
	 * Closes the stream.
	 */
	public void close()
	{
		try
		{
			audioStream.close();
			isClosed = true;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static AudioStreamData openWavStream(String filePath, int chunkSize)
	{
		AudioInputStream audioStream = null;

		try
		{
			InputStream bufferedInput = new BufferedInputStream(new FileInputStream(filePath));
			audioStream = AudioSystem.getAudioInputStream(bufferedInput);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		AudioStreamData wavStream = new AudioStreamData(filePath, audioStream, chunkSize);
		return wavStream;
	}

	/**
	 * Determines the OpenAL ID of the sound data format.
	 *
	 * @param channels      - number of channels in the audio data.
	 * @param bitsPerSample - number of bits per sample (either 8 or 16).
	 * @return The OpenAL format ID of the sound data.
	 */
	private static int getOpenAlFormat(int channels, int bitsPerSample)
	{
		if (channels == 1)
		{
			return bitsPerSample == 8 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_MONO16;
		} else
		{
			return bitsPerSample == 8 ? AL10.AL_FORMAT_STEREO8 : AL10.AL_FORMAT_STEREO16;
		}
	}

	public float getDuration()
	{
		return duration;
	}
}