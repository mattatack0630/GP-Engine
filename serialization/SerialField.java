package serialization;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 12/24/2016.
 */
public class SerialField extends SerialContainer
{
	// Primitive Types
	private static final byte BYTE = 0;
	private static final byte SHORT = 1;
	private static final byte CHAR = 2;
	private static final byte INTEGER = 3;
	private static final byte LONG = 4;
	private static final byte FLOAT = 5;
	private static final byte DOUBLE = 6;
	private static final byte BOOLEAN = 7;

	// Data represented as a bytes
	private byte[] data;
	// Data type
	private byte dataType;

	private SerialField(String name, byte type, byte[] data)
	{
		this.name = name;
		this.data = data;
		this.dataType = type;
		this.type = SerialContainer.FIELD;
	}

	@Override
	public int calcLength()
	{
		return (1) +    // Type
				(Short.BYTES) +    // Name char count
				(name.length() * Character.BYTES) + // Name chars
				(1) +    // Data type
				(Short.BYTES) +    // Data length count
				(data.length);    // Data bytes
	}

	@Override
	public void serialize(ByteBuffer buffer)
	{
		// Write Serial Type
		buffer.put(type);

		// Write name Length
		buffer.putShort((short) name.length());

		// Write name data
		for (int i = 0; i < name.length(); i++)
			buffer.putChar(name.charAt(i));

		// Write Data type
		buffer.put(dataType);

		// Write Data length
		buffer.putShort((short) data.length);

		// Write Data
		buffer.put(data);
	}

	@Override
	public void deserialize(ByteBuffer buffer)
	{
		byte type = buffer.get();
		if (type == this.type)
		{
			short nameLength = buffer.getShort();

			String name = "";
			for (int i = 0; i < nameLength; i++)
				name += buffer.getChar();

			byte dataType = buffer.get();

			short dataLength = buffer.getShort();

			byte[] data = new byte[dataLength];

			for (int i = 0; i < dataLength; i++)
				data[i] = buffer.get();

			this.name = name;
			this.data = data;
			this.dataType = dataType;
		}
	}

	// debugging
	public String testString(int tc)
	{
		String s = new String();

		for (int i = 0; i < tc; i++)
			s += "\t";
		s += "Field: " + name + " - ";
		for (int i = 0; i < data.length; i++)
			s += " 0x" + String.format("%02x", data[i]);
		s += "\n";

		return s;
	}

	/**
	 * Getters and Setters
	 */
	public String getName()
	{
		return name;
	}

	public byte[] getBytes()
	{
		return data;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setValue(byte[] data)
	{
		this.data = data;
	}

	/**
	 * Field getters
	 */
	public byte asByte()
	{
		return data[0];
	}

	public short asShort()
	{
		return ByteBuffer.wrap(data).getShort();
	}

	public char asChar()
	{
		return ByteBuffer.wrap(data).getChar();
	}

	public int asInteger()
	{
		return ByteBuffer.wrap(data).getInt();
	}

	public long asLong()
	{
		return ByteBuffer.wrap(data).getLong();
	}

	public float asFloat()
	{
		return ByteBuffer.wrap(data).getFloat();
	}

	public double asDouble()
	{
		return ByteBuffer.wrap(data).getDouble();
	}

	public boolean asBoolean()
	{
		return data[0] != 0;
	}

	/**
	 * Generator Methods
	 */
	public static SerialField Byte(String name, byte i)
	{
		byte[] data = new byte[1];
		data[0] = i;
		return new SerialField(name, BYTE, data);
	}

	public static SerialField Short(String name, short i)
	{
		byte[] data = new byte[2];

		data[0] = (byte) (i >> 8);
		data[1] = (byte) (i >> 0);

		return new SerialField(name, SHORT, data);
	}

	public static SerialField Char(String name, char i)
	{
		byte[] data = new byte[2];

		data[0] = (byte) (i >> 8);
		data[1] = (byte) (i >> 0);

		return new SerialField(name, CHAR, data);
	}

	public static SerialField Integer(String name, int i)
	{
		byte[] data = new byte[4];

		for (int j = 0; j < data.length; j++)
			data[data.length - 1 - j] = (byte) (i >> (8 * j));

		return new SerialField(name, INTEGER, data);
	}

	public static SerialField Long(String name, long i)
	{
		byte[] data = new byte[8];

		for (int j = 0; j < data.length; j++)
			data[data.length - 1 - j] = (byte) (i >> (8 * j));

		return new SerialField(name, LONG, data);
	}

	public static SerialField Float(String name, float i)
	{
		return new SerialField(name, FLOAT, ByteBuffer.allocate(4).putFloat(i).array());
	}

	public static SerialField Double(String name, double i)
	{
		return new SerialField(name, DOUBLE, ByteBuffer.allocate(4).putDouble(i).array());
	}

	public static SerialField Boolean(String name, boolean i)
	{
		byte[] data = new byte[1];

		data[0] = (byte) (i ? 1 : 0);

		return new SerialField(name, BOOLEAN, data);
	}

	public static SerialField Unknown()
	{
		return new SerialField("UNKNOWN", (byte) -1, null);
	}
}
