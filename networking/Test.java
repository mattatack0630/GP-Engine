package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by mjmcc on 1/6/2017.
 */
public class Test
{
	private static final String SEND_SUFFIX = "\n\r";
	private static final int MAX_BYTES = 1000 * 1000;

	private String name;

	private Socket clientSocket;
	private boolean connected;
	private String host;
	private int port;

	private boolean listening;
	private Thread listenThread;
	private DataInputStream listenStream;
	private byte[] recvBuffer = new byte[MAX_BYTES];
	private int currPacketSize = 0;

	private DataOutputStream sendStream;

	public Test(String name, String host, int port)
	{
		listenThread = new Thread(() -> listen());
		this.name = name;
		this.host = host;
		this.port = port;
	}

	public synchronized boolean connect()
	{
		if (connected)
			return false;

		try
		{
			clientSocket = new Socket(host, port);
			listenStream = new DataInputStream(clientSocket.getInputStream());
			sendStream = new DataOutputStream(clientSocket.getOutputStream());
			connected = true;

		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public synchronized boolean disconnect()
	{
		if (!connected)
			return false;

		try
		{
			stopListening();
			listenThread.join();
			listenStream.close();
			sendStream.flush();
			sendStream.close();
			clientSocket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	public synchronized boolean send(String message)
	{
		if (!connected)
			return false;

		if (message.length() > MAX_BYTES)
			return false;

		try
		{
			sendStream.write(message.getBytes());
			sendStream.write(SEND_SUFFIX.getBytes());
			sendStream.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public synchronized boolean send(byte[] message)
	{
		if (!connected)
			return false;

		if (message.length > MAX_BYTES)
			return false;

		try
		{
			sendStream.write(message);
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean beginListening()
	{
		if (!connected)
			return false;

		listening = true;
		listenThread.start();

		return true;
	}

	public boolean stopListening()
	{
		if (!connected)
			return false;

		listening = false;
		return true;
	}

	public boolean listen()
	{
		if (!connected)
			return false;

		try
		{
			while (listening)
			{
				if (currPacketSize > MAX_BYTES)
					continue;

				int available = listenStream.available();

				// FIXME: 8/22/2016 Possible solution to the high CPU ussage, making the thread sleep :/
				if (available > 0)
					writePacketToBuffer(listenStream, available);
				else
					Thread.sleep(10);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	public synchronized boolean pollDisconnect() throws IOException
	{
		return connected;
	}

	public synchronized void writePacketToBuffer(DataInputStream is, int available) throws IOException
	{
		int code = is.read(recvBuffer, currPacketSize, available);
		currPacketSize += available;
	}

	public boolean waitForPacketSize(int length, int timeout)
	{
		long then = System.currentTimeMillis();
		while (currPacketSize < length)
		{
			// FIXME: 8/22/2016 Find a better way to slow this loop down, seems to malfunction when nothing is here?
			System.out.print("");
			if (System.currentTimeMillis() - then > timeout)
				return false;
		}

		return true;
	}

	public boolean waitForPacketRegex(String s, int timeout)
	{
		/*USE ONLY FOR SMALLER RESPONSES!*/
		long then = System.currentTimeMillis();
		String toString = new String(recvBuffer, 0, currPacketSize);

		while (!toString.contains(s))
		{
			//Optimise to not do this later!
			toString = new String(recvBuffer, 0, currPacketSize);

			if (System.currentTimeMillis() - then > timeout)
				return false;
		}
		return true;
	}

	public int getPacketSize()
	{
		if (!connected)
			return -1;

		return currPacketSize;
	}

	public synchronized byte[] getPacket()
	{
		if (!connected)
			return null;

		currPacketSize = 0;

		return recvBuffer;
	}

	public synchronized String getPacketString()
	{
		if (!connected)
			return null;
		String s = new String(recvBuffer, 0, currPacketSize);
		currPacketSize = 0;

		return s;
	}
}
