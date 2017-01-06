package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/29/2016.
 */
public class UDPCommunication
{
	private static final int MAX_PACKET_SIZE = 1024;

	private DatagramSocket localSocket;

	private ByteBuffer sendBytes;
	private ByteBuffer receiveBytes;

	private volatile boolean hasReceived;
	private volatile boolean socketOpen;

	private Map<Short, UDPPacket> constructingPackets;
	private Map<Short, UDPPacket> completedPackets;

	public UDPCommunication(int clientPort)
	{
		this.sendBytes = ByteBuffer.allocate(MAX_PACKET_SIZE);
		this.receiveBytes = ByteBuffer.allocate(MAX_PACKET_SIZE);
		this.socketOpen = true;
		this.hasReceived = false;
		this.completedPackets = new HashMap<>();
		this.constructingPackets = new HashMap<>();

		try
		{
			this.localSocket = new DatagramSocket(clientPort);
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}

	public void send(ByteBuffer bytes, InetAddress destAddress, int destPort)
	{
		int dataWritten = 0;

		while (dataWritten < bytes.limit())
		{
			int toWrite = Math.min(bytes.limit() - dataWritten, MAX_PACKET_SIZE);

			sendBytes.clear();
			sendBytes.put(bytes.array(), dataWritten, toWrite);

			try
			{
				DatagramPacket sendPacket = new DatagramPacket(sendBytes.array(), toWrite, destAddress, destPort);
				localSocket.send(sendPacket);
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			dataWritten += toWrite;
		}
	}

	public void closeSocket()
	{
		localSocket.close();
		socketOpen = false;
	}

	public void listenForReceived()
	{
		try
		{
			if (socketOpen)
			{
				DatagramPacket receivePacket = new DatagramPacket(receiveBytes.array(), receiveBytes.limit());
				localSocket.receive(receivePacket);
				doInitialProcessing(receivePacket);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void doInitialProcessing(DatagramPacket info)
	{
		ByteBuffer buffer = ByteBuffer.wrap(info.getData());

		short packetId = buffer.getShort(4); // Skipping packet length

		UDPPacket packet;

		if (constructingPackets.containsKey(packetId))
		{
			packet = constructingPackets.get(packetId);
			packet.appendChunk(buffer, info.getAddress(), info.getPort());
		} else
		{
			packet = new UDPPacket(packetId);
			packet.appendChunk(buffer, info.getAddress(), info.getPort());
		}

		if (packet.isComplete())
		{
			constructingPackets.remove(packet);
			completedPackets.put(packetId, packet);
		}
	}

	public synchronized UDPPacket getReceivePacket()
	{
		UDPPacket packet = completedPackets.remove(0);
		return packet;
	}

	public synchronized boolean hasReceived()
	{
		return hasReceived;
	}
}
