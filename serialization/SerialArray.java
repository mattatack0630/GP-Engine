package serialization;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 12/24/2016.
 */
public class SerialArray extends SerialContainer
{
	// Primitive Types
	private static final byte UNKNOWN = -1;
	private static final byte BYTE = 0;
	private static final byte SHORT = 1;
	private static final byte CHAR = 2;
	private static final byte INTEGER = 3;
	private static final byte LONG = 4;
	private static final byte FLOAT = 5;
	private static final byte DOUBLE = 6;
	private static final byte BOOLEAN = 7;

	private int dataLength;
	private int arrayLength;
	private byte dataType;
	private byte[][] data;

	private SerialArray(String name, byte dataType, int dataLength, int arrayLength, byte[][] data)
	{
		this.name = name;
		this.type = SerialContainer.ARRAY;
		this.dataType = dataType;
		this.data = data;
		this.arrayLength = arrayLength;
		this.dataLength = dataLength;
	}

	@Override
	public int calcLength()
	{
		return (Byte.BYTES) + // Type
				(Byte.BYTES) + // Data Type
				(Short.BYTES) + // Name length
				(Integer.BYTES) + // Array length
				(Character.BYTES * name.length()) + // Name characters
				(Short.BYTES * arrayLength) + // Data length for each in array
				(dataLength * arrayLength); // Data for each in array
	}

	@Override
	public void serialize(ByteBuffer buffer)
	{
		// Write type
		buffer.put(type);

		// Write data type
		buffer.put(dataType);

		// Write Array length
		buffer.putInt(arrayLength);

		// Write name length
		buffer.putShort((short) name.length());

		// Write name chars
		for (int i = 0; i < name.length(); i++)
			buffer.putChar(name.charAt(i));

		// For each in array
		for (int i = 0; i < arrayLength; i++)
		{
			// Write data length
			buffer.putShort((short) data[i].length);
			// Write data
			for (int j = 0; j < dataLength; j++)
				buffer.put(data[i][j]);
		}
	}

	@Override
	public void deserialize(ByteBuffer buffer)
	{
		byte type = buffer.get();
		if (type == this.type)
		{
			byte dataType = buffer.get();
			int arrayLength = buffer.getInt();

			short nameLength = buffer.getShort();
			String name = new String();
			for (int i = 0; i < nameLength; i++)
				name += buffer.getChar();

			byte[][] data = new byte[arrayLength][0];

			for (int i = 0; i < arrayLength; i++)
			{
				short dataLength = buffer.getShort();
				data[i] = new byte[dataLength];

				for (int j = 0; j < dataLength; j++)
					data[i][j] = buffer.get();
			}

			this.name = name;
			this.arrayLength = data.length;
			this.dataLength = data[0].length;
			this.data = data;
			this.dataType = dataType;
		}
	}

	public String testString(int tc)
	{
		String s = new String();

		for (int i = 0; i < tc; i++)
			s += "\t";
		s += "Array: " + name + "\n";

		for (int i = 0; i < arrayLength; i++)
		{
			for (int t = 0; t < tc; t++)
				s += "\t";
			for (int j = 0; j < dataLength; j++)
				s += " 0x" + String.format("%02x", data[i][j]);
			s += "\n";
		}

		return s;
	}

	public String getName()
	{
		return name;
	}

	/**
	 * GetAs methods
	 */
	public byte[] asByte()
	{
		byte[] d = new byte[data.length];

		for (int i = 0; i < d.length; i++)
			d[i] = data[i][0];

		return d;
	}

	public short[] asShort()
	{
		short[] d = new short[data.length];

		for (int i = 0; i < d.length; i++)
			d[i] = ByteBuffer.wrap(data[i]).getShort();

		return d;
	}

	public char[] asChar()
	{
		char[] d = new char[data.length];

		for (int i = 0; i < d.length; i++)
			d[i] = ByteBuffer.wrap(data[i]).getChar();

		return d;
	}

	public int[] asInteger()
	{
		int[] d = new int[data.length];

		for (int i = 0; i < d.length; i++)
			d[i] = ByteBuffer.wrap(data[i]).getInt();

		return d;
	}

	public long[] asLong()
	{
		long[] d = new long[data.length];

		for (int i = 0; i < d.length; i++)
			d[i] = ByteBuffer.wrap(data[i]).getLong();

		return d;
	}

	public float[] asFloat()
	{
		float[] d = new float[data.length];

		for (int i = 0; i < d.length; i++)
			d[i] = ByteBuffer.wrap(data[i]).getFloat();

		return d;
	}

	public double[] asDouble()
	{
		double[] d = new double[data.length];

		for (int i = 0; i < d.length; i++)
			d[i] = ByteBuffer.wrap(data[i]).getDouble();

		return d;
	}

	public boolean[] asBoolean()
	{
		boolean[] d = new boolean[data.length];

		for (int i = 0; i < d.length; i++)
			d[i] = data[i][0] != 0;

		return d;
	}

	public String asString()
	{
		String d = new String();

		for (int i = 0; i < data.length; i++)
			d += ByteBuffer.wrap(data[i]).getChar();

		return d;
	}

	/**
	 * Generator Methods
	 */
	public static SerialArray Byte(String name, byte[] bytes)
	{
		byte[][] data = new byte[bytes.length][Byte.BYTES];

		for (int i = 0; i < bytes.length; i++)
			for (int j = 0; j < 1; j++)
				data[i][1 - 1 - j] = (byte) (i >> (8 * j));

		return new SerialArray(name, BYTE, Byte.BYTES, bytes.length, data);
	}

	public static SerialArray Short(String name, short[] shorts)
	{
		byte[][] data = new byte[shorts.length][Short.BYTES];

		for (int i = 0; i < shorts.length; i++)
			data[i] = ByteBuffer.allocate(Short.BYTES).putShort(shorts[i]).array();

		return new SerialArray(name, SHORT, Short.BYTES, shorts.length, data);
	}

	public static SerialArray Char(String name, char[] chars)
	{
		byte[][] data = new byte[chars.length][Character.BYTES];

		for (int i = 0; i < chars.length; i++)
			data[i] = ByteBuffer.allocate(Character.BYTES).putChar(chars[i]).array();

		return new SerialArray(name, CHAR, Character.BYTES, chars.length, data);
	}

	public static SerialArray String(String name, String string)
	{
		byte[][] data = new byte[string.length()][Character.BYTES];

		for (int i = 0; i < string.length(); i++)
			data[i] = ByteBuffer.allocate(Character.BYTES).putChar(string.charAt(i)).array();

		return new SerialArray(name, CHAR, Character.BYTES, string.length(), data);
	}

	public static SerialArray Integer(String name, int[] ints)
	{
		byte[][] data = new byte[ints.length][Integer.BYTES];

		for (int i = 0; i < ints.length; i++)
			data[i] = ByteBuffer.allocate(Integer.BYTES).putInt(ints[i]).array();

		return new SerialArray(name, INTEGER, Integer.BYTES, ints.length, data);
	}

	public static SerialArray Long(String name, long[] longs)
	{
		byte[][] data = new byte[longs.length][Long.BYTES];

		for (int i = 0; i < longs.length; i++)
			data[i] = ByteBuffer.allocate(Long.BYTES).putLong(longs[i]).array();

		return new SerialArray(name, LONG, Long.BYTES, longs.length, data);
	}

	public static SerialArray Float(String name, float[] floats)
	{
		byte[][] data = new byte[floats.length][Float.BYTES];

		for (int i = 0; i < floats.length; i++)
			data[i] = ByteBuffer.allocate(4).putFloat(i).array();

		return new SerialArray(name, FLOAT, Float.BYTES, floats.length, data);
	}

	public static SerialArray Double(String name, double[] doubles)
	{
		byte[][] data = new byte[doubles.length][Double.BYTES];

		for (int i = 0; i < doubles.length; i++)
			data[i] = ByteBuffer.allocate(8).putDouble(i).array();

		return new SerialArray(name, DOUBLE, Double.BYTES, doubles.length, data);
	}

	public static SerialArray Boolean(String name, boolean[] booleans)
	{
		byte[][] data = new byte[booleans.length][1];

		for (int i = 0; i < booleans.length; i++)
			data[i][0] = (byte) (booleans[i] ? 1 : 0);

		return new SerialArray(name, BOOLEAN, 1, booleans.length, data);
	}

	public static SerialArray Unknown()
	{
		return new SerialArray("UNKNOWN", UNKNOWN, 0, 0, new byte[0][0]);
	}

}
