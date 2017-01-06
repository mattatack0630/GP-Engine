package serialization;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/24/2016.
 */
public class SerialDatabase extends SerialContainer
{
	private List<SerialObject> objects;

	public SerialDatabase(String name)
	{
		this.name = name;
		this.type = SerialContainer.DATABASE;
		this.objects = new ArrayList<>();
	}

	public void addObject(SerialObject object)
	{
		this.objects.add(object);
	}

	@Override
	public int calcLength()
	{
		int c = (Byte.BYTES) +    // type
				(Short.BYTES) +    // letterCount
				(name.length() * Character.BYTES) + // name
				(Short.BYTES);    // ObjectCount

		for (SerialObject object : objects)
			c += object.calcLength();

		return c;
	}

	@Override
	public void serialize(ByteBuffer buffer)
	{
		// Write type
		buffer.put(type);

		// Write nameLength
		buffer.putShort((short) name.length());

		// Write name
		for (int i = 0; i < name.length(); i++)
			buffer.putChar(name.charAt(i));

		// Write Object Count
		buffer.putShort((short) objects.size());

		// Write Objects
		for (SerialObject object : objects)
			object.serialize(buffer);
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

			short objectCount = buffer.getShort();

			for (int i = 0; i < objectCount; i++)
			{
				SerialObject o = new SerialObject("");
				o.deserialize(buffer);
				objects.add(o);
			}

			this.name = name;
		}
	}

	// debugging
	public String testString(int tc)
	{
		String s = new String();

		s += "DataBase: " + name + "\n";
		s += "Object Count: " + objects.size() + "\n";
		for (SerialObject o : objects)
			s += o.testString(tc + 1);

		return s;
	}

	public SerialObject getObject(String name)
	{
		for (SerialObject obj : objects)
			if (obj.getName().equals(name))
				return obj;
		return null;
	}
}
