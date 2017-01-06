package networking;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 12/29/2016.
 */
public class UDPServer
{
	private Map<InetAddress, clientDS> connectedClients;
	private List<UDPPacket> completedPackets;

	private UDPCommunication serverCom;
	private int serverPort;

	private volatile boolean running;
	private Thread listenThread;

	UDPServer(int port)
	{
		this.connectedClients = new HashMap<>();
		this.completedPackets = new ArrayList<>();

		this.serverPort = port;
		this.serverCom = new UDPCommunication(port);

		this.running = true;
		startListenThread();
	}

	private synchronized void startListenThread()
	{
		this.listenThread = new Thread(() -> {
			running = true;
			while (running)
			{
				serverCom.listenForReceived();
				UDPPacket packet = serverCom.getReceivePacket();
				completedPackets.add(packet);
				checkForSpecialPacket(packet);
			}
		});

		this.listenThread.start();
	}

	private void checkForSpecialPacket(UDPPacket packet)
	{
		if (packet.getType() == UDPPacket.CONNECT)
		{
			connectedClients.put(packet.getFromAddress(), new clientDS(packet.getFromAddress(), packet.getFromPort()));
		}

		if (packet.getType() == UDPPacket.DISCONNECT)
		{
			connectedClients.remove(packet.getFromAddress());
		}
	}

	public void send(ByteBuffer buffer, clientDS client)
	{
		serverCom.send(buffer, client.address, client.port);
	}

	public void close()
	{
		running = false;
		serverCom.closeSocket();
	}

	// Client data structure
	private class clientDS
	{
		public int port;
		public InetAddress address;
		public boolean connected = false;

		public clientDS(InetAddress address, int port)
		{
			this.address = address;
			this.port = port;
		}
	}
}
