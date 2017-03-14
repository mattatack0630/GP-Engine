package networking;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 12/30/2016.
 * <p>
 * The UDPPacket is a representation of a packet sent over UDP.
 * Any packet larger than MAX_PACKET_SIZE is split up and sent in smaller chunks.
 */
public class UDPPacket
{
	// Packet types constants
	public static final byte UNKNOWN = 0x0;
	public static final byte CONNECT = 0x1;
	public static final byte DISCONNECT = 0x2;
	public static final byte DATABASE_SEND = 0x3;

	// Packet type
	private short packetId;
	private byte type;

	// From server data
	private int fromPort;
	private InetAddress fromAddress;

	// Body data from the packet
	private ByteBuffer packetBody;

	// Full Packet (Used to construct packets from multiple chunks)
	private ByteBuffer fullPacket;
	private int chunksReceived;
	private boolean isComplete;

	public UDPPacket(short packetId)
	{
		this.packetId = packetId;
		this.isComplete = false;
		this.chunksReceived = 0;
	}

	public void setType(byte type)
	{
		this.type = type;
	}

	public void appendChunk(ByteBuffer buffer, InetAddress address, int port)
	{
		if (chunksReceived == 0)
		{
			int packetSize = buffer.getInt();
			fullPacket = ByteBuffer.allocate(packetSize);
			buffer.position(0);
		}

		if (fullPacket.position() + buffer.limit() <= fullPacket.limit())
			fullPacket.put(buffer);

		if (fullPacket.position() == fullPacket.limit())
		{
			deconstruct();
			isComplete = true;
		}

		chunksReceived++;
	}

	/**
	 * Sets the packets body. This contains the packets data, excluding data like version
	 * and packet type which are included in the packet header
	 */
	public void setPacketBody(ByteBuffer data)
	{
		packetBody = data;
	}

	/**
	 * Construct a packet from the full setElements of bytes
	 */
	public void deconstruct()
	{

	}

	public ByteBuffer construct()
	{
		ByteBuffer buffer = ByteBuffer.allocate(calculateLength());

		// Set how many bytes the entire packet is (1 integer)
		buffer.putInt(buffer.limit());

		// Set packet Id
		buffer.putShort((short) (hashCode() / 1000));

		// Set packet type (1 byte)
		buffer.put(type);

		// Set packet body length (1 integer)
		buffer.putInt(packetBody.limit());

		// Set packet body ([n] bytes)
		packetBody.position(0);
		buffer.put(packetBody);

		return buffer;
	}

	private int calculateLength()
	{
		return (Integer.BYTES) + // packet length
				(Short.BYTES) + // packet id
				(Byte.BYTES) + // packet type
				(Integer.BYTES) + // body length
				(packetBody.limit()); // packet body
	}

	public boolean isComplete()
	{
		return isComplete;
	}

	public byte getType()
	{
		return type;
	}

	public InetAddress getFromAddress()
	{
		return fromAddress;
	}

	public int getFromPort()
	{
		return fromPort;
	}
}
