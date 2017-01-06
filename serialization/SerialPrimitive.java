package serialization;

/**
 * Created by mjmcc on 12/26/2016.
 */
public class SerialPrimitive
{
	// Primitive types
	private static final byte BYTE = 0;
	private static final byte SHORT = 1;
	private static final byte CHAR = 2;
	private static final byte INTEGER = 3;
	private static final byte LONG = 4;
	private static final byte FLOAT = 5;
	private static final byte DOUBLE = 6;
	private static final byte BOOLEAN = 7;

	// Data represented as bytes
	private byte[] data;
	// Data type
	private byte dataType;
}
