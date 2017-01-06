package input;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyHandler {
	private static final int REKEY_TIME = 10;
	
	public static List<KeyUsable> keyUsers = new ArrayList<KeyUsable>();
	public static HashMap<Integer, Integer> DownKeys = new HashMap<>();
	public static HashMap<Integer, Character> DownChars = new HashMap<>();
	
	public static void update(){
		Keyboard.poll();
		
		if(Keyboard.next()){
			int eventKey = Keyboard.getEventKey();
			char eventChar = Keyboard.getEventCharacter();
			
			if(Keyboard.getEventKeyState()){
				for (KeyUsable user : keyUsers)
					user.onKeyPress(eventKey, eventChar);

				DownKeys.put(eventKey, 0);
				DownChars.put(eventKey, eventChar);
			}else{
				for (KeyUsable user : keyUsers)
					if (DownChars.containsKey(eventKey))
						user.onKeyRelease(eventKey, DownChars.get(eventKey));
				DownKeys.remove(eventKey);
				DownChars.remove(eventKey);
			}
				
		}
			
		if(!DownChars.isEmpty()){
			for(KeyUsable user: keyUsers){
				for (int i : DownKeys.keySet())
				{
					char c = DownChars.get(i);
					user.onKeyHold(i, c);
					if (DownKeys.get(i) % REKEY_TIME == 0 && DownKeys.get(i) > 30)
						user.onKeyRepress(i, c);
				}
			}
		}

		for (int i : DownKeys.keySet())
			DownKeys.put(i, DownKeys.get(i) + 1);
	}
	
	public static void addKeyUser(KeyUsable user){
		keyUsers.add(user);
	}

}
