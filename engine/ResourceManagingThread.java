package engine;

/**
 * Created by mjmcc on 12/22/2016.
 * <p>
 * This thread will be used to load resources dynamically
 * while the game is running.
 * <p>
 * - Loading models
 * - Streaming Sounds
 */
public class ResourceManagingThread implements Runnable
{


	public static void init()
	{

	}

	@Override
	public void run()
	{
		while (Engine.isRunning())
		{

		}
	}
}
