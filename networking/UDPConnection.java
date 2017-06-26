package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 5/23/2017.
 */
public class UDPConnection extends NetworkConnection
{
	private DatagramSocket localSocket;

	public UDPConnection(InetAddress localIP, int localPort)
	{
		this.localIP = localIP;
		this.localPort = localPort;
		initListenThread();
	}

	public UDPConnection(int packetSize, InetAddress localIP, int localPort)
	{
		super(packetSize);
		this.localIP = localIP;
		this.localPort = localPort;
		initListenThread();
	}

	@Override
	public void cleanup()
	{
		disconnect();
		destroyListenThread(); // wait for listen thread to join
	}

	@Override
	public void connect(InetAddress destAdress, int destPort)
	{
		try
		{
			this.localSocket = new DatagramSocket(localPort); // set local(from) socket
		} catch (SocketException e)
		{
			e.printStackTrace();
		}

		this.destIP = destAdress;
		this.destPort = destPort;

		connected = true;
	}

	@Override
	public void disconnect()
	{
		connected = false;
		localSocket.close();
	}

	@Override
	public boolean checkReceive()
	{
		return receivedPacket;
	}

	@Override
	protected synchronized void send(ByteBuffer buffer)
	{
		try
		{
			DatagramPacket sendPacket = new DatagramPacket(buffer.array(), 0, buffer.limit(), destIP, destPort);
			localSocket.send(sendPacket);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected synchronized void receive(ByteBuffer buffer)
	{
		try
		{
			DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.limit());
			localSocket.receive(receivePacket); // blocking!
			buffer.position(receivePacket.getLength()); // used to flip buffer properly
			buffer.limit(buffer.position()); // sets limit to the actual amount of data read
			receivedPacket = true;
		} catch (IOException e)
		{
			// If Socket was closed, then all good here
			if (!localSocket.isClosed())
			{
				e.printStackTrace();
			}
		}
	}
}
