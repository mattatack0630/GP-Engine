package input;

public interface KeyUsable {
	void onKeyPress(int i, char c);

	void onKeyRelease(int i, char c);

	void onKeyHold(int i, char c);

	void onKeyRepress(int i, char character);
}
