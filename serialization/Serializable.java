package serialization;

/**
 * Created by mjmcc on 12/26/2016.
 */
public interface Serializable
{
	void serialize(SerialObject o);

	void deserialize(SerialObject o);
}
