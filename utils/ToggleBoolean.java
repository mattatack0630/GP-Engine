package utils;

/**
 * Created by mjmcc on 10/19/2016.
 */
public class ToggleBoolean
{
	boolean value;
	boolean lastValueT;
	boolean lastValueF;

	public ToggleBoolean(boolean value)
	{
		this.value = value;
		this.lastValueT = value;
		this.lastValueF = value;
	}

	public void toggle()
	{
		value = !value;
	}

	public boolean isTrue()
	{
		return value;
	}

	public boolean isFalse()
	{
		return !value;
	}

	public boolean turnedTrue()
	{
		boolean currValue = (!lastValueT && value);
		lastValueT = value;
		return currValue;
	}

	public boolean turnedFalse()
	{
		boolean currValue = (lastValueF && !value);
		lastValueF = value;
		return currValue;
	}
}
