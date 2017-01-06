package serialization;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 12/26/2016.
 */
public class SerialFile
{
	// Res directory for serial data
	private static final String SERIAL_PATH = "res/serial/";

	// Header data
	private static final String GPDB_HEADER = "GPBD";
	private static final short STATIC_VERSION = 01;
	private static final byte FLAG_COUNT = 10;

	private short version;
	private String fileName;
	private boolean[] headerFlags;

	public SerialFile(String path)
	{
		this.fileName = path;
		this.headerFlags = new boolean[FLAG_COUNT];
	}

	public void saveToFile(SerialDatabase database)
	{
		int length = database.calcLength();
		ByteBuffer buffer = ByteBuffer.allocate(length);

		database.serialize(buffer);

		ByteFileUtil.writeToFile(fileName, buffer.array());
	}

	public SerialDatabase buildFromFile(String databaseName)
	{
		byte[] bytes = ByteFileUtil.readFromFile(fileName);
		ByteBuffer buffer = ByteBuffer.wrap(bytes);

		SerialDatabase db = new SerialDatabase("");
		db.deserialize(buffer);
		return db;
	}

	public void setVersion(int version)
	{
		this.version = (short) version;
	}
}
