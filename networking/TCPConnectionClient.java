package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 5/23/2017.
 */
public class TCPConnectionClient extends NetworkConnection
{
	private Socket clientSocket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	public TCPConnectionClient(InetAddress localAdress, int localPort, InetAddress destAddress, int destPort)
	{
		this.localIP = localAdress;
		this.localPort = localPort;
		this.destIP = destAddress;
		this.destPort = destPort;
		initListenThread();
	}

	public TCPConnectionClient(int packetSize, InetAddress localAdress, int localPort, InetAddress destAddress, int destPort)
	{
		super(packetSize);
		this.localIP = localAdress;
		this.localPort = localPort;
		this.destIP = destAddress;
		this.destPort = destPort;
		initListenThread();
	}


	@Override
	public void connect(InetAddress destAdress, int destPort)
	{
		try
		{
			clientSocket = new Socket(destAdress, destPort, localIP, localPort);
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
			connected = true;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect()
	{
		try
		{
			clientSocket.close();
			connected = false;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void cleanup()
	{
		disconnect();
		destroyListenThread();
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
			outputStream.write(buffer.array(), 0, buffer.limit());
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
			int read = inputStream.read(buffer.array()); // blocking!
			buffer.position(read); // used to flip buffer properly
			buffer.limit(buffer.position()); // sets limit to the actual amount of data read
			receivedPacket = true;
		} catch (IOException e)
		{
			// If Socket was closed, then all good here
			if (!clientSocket.isClosed())
			{
				e.printStackTrace();
			}
		}
	}
}
