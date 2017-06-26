package networking;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 5/23/2017.
 */
public abstract class NetworkConnection
{
	private static final int DEF_PACKET_SIZE = 1024; // make larger later

	protected InetAddress destIP;
	protected int destPort;
	protected InetAddress localIP;
	protected int localPort;

	protected boolean connected;
	protected boolean receivedPacket;
	protected boolean beginReceiving;

	protected Thread receiveListener;
	protected ByteBuffer workingBuffer; // use for sending and receiving, maybe change

	public NetworkConnection()
	{
		this.workingBuffer = ByteBuffer.allocate(DEF_PACKET_SIZE);
	}

	public NetworkConnection(int maxPacketSize)
	{
		this.workingBuffer = ByteBuffer.allocate(maxPacketSize);
	}

	public abstract void connect(InetAddress destAdress, int destPort);

	public abstract void disconnect();

	public abstract void cleanup();

	public abstract boolean checkReceive();

	protected abstract void send(ByteBuffer buffer);

	protected abstract void receive(ByteBuffer buffer);

	protected void initListenThread()
	{
		receiveListener = new Thread(() -> {

			while (connected)
			{
				if (beginReceiving)
				{
					receive(workingBuffer);
					beginReceiving = false;
				}

				try
				{
					receiveListener.sleep(100);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

		}, "Network Response Listening Thread");

		receiveListener.start();
	}

	protected void destroyListenThread()
	{
		try
		{
			receiveListener.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void beginReceiving()
	{
		beginReceiving = true;
		receivedPacket = false;
		clearWorkingBuffer();
	}

	public void beginSending()
	{
		send(workingBuffer);
		clearWorkingBuffer();
	}

	private void clearWorkingBuffer(){

		workingBuffer.flip();
		workingBuffer.limit(workingBuffer.capacity());
	}

	public void setLocalPort(int localPort)
	{
		this.localPort = localPort;
	}

	public void setLocalIP(InetAddress localIP)
	{
		this.localIP = localIP;
	}

	public boolean isConnected()
	{
		return connected;
	}

	public int getLocalPort()
	{
		return localPort;
	}

	public InetAddress getLocalIP()
	{
		return localIP;
	}

	public int getDestPort()
	{
		return destPort;
	}

	public InetAddress getDestIP()
	{
		return destIP;
	}

	public ByteBuffer getDataBuffer()
	{
		return workingBuffer;
	}
}
