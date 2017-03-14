package engine;

import rendering.DisplayManager;

/**
 * Created by mjmcc on 12/22/2016.
 * <p>
 * This thread will be used to load resources dynamically
 * while the game is running.
 * <p>
 * - Loading models
 * - Streaming Sounds
 */
public class ResourceManagingThread extends Thread
{
	private static final long SLEEP_TIME = 50;
	private static Thread resourceThread;

	public synchronized static void startThread()
	{

		resourceThread = new Thread(() -> {
			DisplayManager.shareGlContextOnThread();

			while (Engine.checkRunning())
			{
				update();
				try
				{
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}, "ResourceThread");

		resourceThread.start();
	}

	public synchronized static void stopThread()
	{
		try
		{
			resourceThread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void update()
	{
	}
}
