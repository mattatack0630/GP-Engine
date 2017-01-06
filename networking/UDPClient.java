package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 12/29/2016.
 */
public class UDPClient
{
	private UDPCommunication clientCom;

	private InetAddress serverAddress;
	private int serverPort;

	private Thread listenThread;
	private volatile boolean running;

	public UDPClient(int clientPort, String serverAddress, int serverPort)
	{
		this.clientCom = new UDPCommunication(clientPort);
		this.running = true;

		connectToServer(serverAddress, serverPort);

		startListenThread();
	}

	private synchronized void startListenThread()
	{
		this.listenThread = new Thread(() -> {
			running = true;
			while (running)
				clientCom.listenForReceived();
		});

		this.listenThread.start();
	}

	public boolean hasReceived()
	{
		return clientCom.hasReceived();
	}

	public void send(ByteBuffer buffer)
	{
		clientCom.send(buffer, serverAddress, serverPort);
	}

	public void connectToServer(String address, int port)
	{
		try
		{
			serverAddress = InetAddress.getByName(address);
			serverPort = port;
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public void close()
	{
		running = false;
		clientCom.closeSocket();
	}
}
