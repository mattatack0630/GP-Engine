package serialization;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/24/2016.
 */
public class SerialTester
{

	public static void serialTest()
	{
		SerialDatabase database = new SerialDatabase("TestDatabase");

		//////////////////////////////////////////////////////////////////
		SerialObject obj1 = new SerialObject("ObjectTest0");
		obj1.addField(SerialField.Float("pos.x", 1.2f));
		obj1.addField(SerialField.Float("pos.y", 1.7f));
		database.addObject(obj1);

		SerialObject obj2 = new SerialObject("ObjectTest1");
		obj2.addField(SerialField.Float("pos.x", 2.2f));
		obj2.addField(SerialField.Float("pos.y", 10.3f));
		database.addObject(obj2);
		/////////////////////////////////////////////////////////////////

		SerialFile file = new SerialFile("res/serial/testFile.gpdb");
		file.saveToFile(database);
	}

	public static void deserialTest()
	{
		SerialFile file = new SerialFile("res/serial/testFile.gpdb");
		SerialDatabase db = file.buildFromFile("TestDatabase");

		SerialObject obj1 = db.getObject("ObjectTest0");
		Vector3f pos0 = new Vector3f();
		pos0.setX(obj1.getField("pos.x").asFloat());
		pos0.setY(obj1.getField("pos.y").asFloat());

		SerialObject obj2 = db.getObject("ObjectTest1");
		Vector3f pos1 = new Vector3f();
		pos1.setX(obj2.getField("pos.x").asFloat());
		pos1.setY(obj2.getField("pos.y").asFloat());

		System.out.println(pos0);
		System.out.println(pos1);
	}

	public static void test()
	{
		serialTest();
		deserialTest();
	}
}
