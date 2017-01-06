package utils;

/**
 * Created by mjmcc on 10/21/2016.
 */
public class Timer
{
	private float elapsed;
	private long lastTime;
	private boolean isRunning;

	public Timer()
	{
		lastTime = System.currentTimeMillis();
		elapsed = 0;
	}

	public void start()
	{
		calculateTime();
		isRunning = true;
	}

	public void pause()
	{
		calculateTime();
		isRunning = false;
	}

	public void reset()
	{
		elapsed = 0;
	}

	public boolean isTimeAt(float time)
	{
		return (getTime() > time);
	}

	public float getTime()
	{
		calculateTime();
		return elapsed;
	}

	public void calculateTime()
	{
		if (isRunning)
			elapsed += (System.currentTimeMillis() - lastTime) / 1000.0f;
		lastTime = System.currentTimeMillis();
	}
}
