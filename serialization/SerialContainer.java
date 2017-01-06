package serialization;

import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 12/25/2016.
 */
public abstract class SerialContainer
{
	public static final byte FIELD = 1;
	public static final byte ARRAY = 2;
	public static final byte OBJECT = 3;
	public static final byte DATABASE = 4;

	protected String name;
	protected byte type;

	public abstract int calcLength();

	public abstract void serialize(ByteBuffer buffer);

	public abstract void deserialize(ByteBuffer buffer);
}
