package serialization;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/24/2016.
 */
public class SerialObject extends SerialContainer
{
	private List<SerialObject> objects;
	private List<SerialField> fields;
	private List<SerialArray> arrays;


	public SerialObject(String name)
	{
		this.name = name;
		this.type = SerialContainer.OBJECT;
		this.fields = new ArrayList<>();
		this.arrays = new ArrayList<>();
		this.objects = new ArrayList<>();
	}

	public void addField(SerialField field)
	{
		this.fields.add(field);
	}

	public void addArray(SerialArray array)
	{
		this.arrays.add(array);
	}

	public void addObject(SerialObject obj)
	{
		this.objects.add(obj);
	}

	public SerialObject getObject(String name)
	{
		for (SerialObject o : objects)
			if (o.getName().equals(name))
				return o;
		return null;
	}

	public SerialField getField(String name)
	{
		for (SerialField f : fields)
			if (f.getName().equals(name))
				return f;
		return null;
	}

	public SerialArray getArray(String name)
	{
		for (SerialArray a : arrays)
			if (a.getName().compareTo(name) == 0)
				return a;
		return null;
	}

	@Override
	public int calcLength()
	{
		int c = (1) +    // Type
				(Short.BYTES) +    // NameLetterCount
				(name.length() * Character.BYTES) +    // NameData
				(Short.BYTES) +    // FieldCount
				(Short.BYTES) +    // ArrayCount
				(Short.BYTES);     // ObjCount

		for (SerialField field : fields)
			c += field.calcLength();

		for (SerialArray array : arrays)
			c += array.calcLength();

		for (SerialObject obj : objects)
			c += obj.calcLength();

		return c;
	}

	@Override
	public void serialize(ByteBuffer buffer)
	{
		// Write Type
		buffer.put(type);

		//Write nameLength
		buffer.putShort((short) name.length());

		//Write name
		for (int i = 0; i < name.length(); i++)
			buffer.putChar(name.charAt(i));

		//Write fieldSize
		buffer.putShort((short) fields.size());

		// Write Fields
		for (SerialField field : fields)
			field.serialize(buffer);

		//Write fieldSize
		buffer.putShort((short) arrays.size());

		// Write Fields
		for (SerialArray array : arrays)
			array.serialize(buffer);

		//Write fieldSize
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

			short fieldCount = buffer.getShort();

			for (int i = 0; i < fieldCount; i++)
			{
				SerialField o = SerialField.Unknown();
				o.deserialize(buffer);
				fields.add(o);
			}

			short arrayCount = buffer.getShort();
			for (int i = 0; i < arrayCount; i++)
			{
				SerialArray o = SerialArray.Unknown();
				o.deserialize(buffer);
				arrays.add(o);
			}

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

		for (int i = 0; i < tc; i++)
			s += "\t";
		s += "Object: " + name + "\n";

		for (int i = 0; i < tc + 1; i++)
			s += "\t";
		s += "Field Count: " + fields.size() + "\n";
		for (SerialField f : fields)
			s += f.testString(tc + 2);

		for (int i = 0; i < tc + 1; i++)
			s += "\t";
		s += "Array Count: " + arrays.size() + "\n";
		for (SerialArray f : arrays)
			s += f.testString(tc + 2);

		for (int i = 0; i < tc + 1; i++)
			s += "\t";
		s += "Object Count: " + objects.size() + "\n";
		for (SerialObject f : objects)
			s += f.testString(tc + 2);

		return s;
	}

	public String getName()
	{
		return name;
	}
}
