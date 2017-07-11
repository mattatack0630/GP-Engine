package gui_m4.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FontLoader Class
 *
 * Loads the font sheet texture and the character data files
 * Uses the VaoLoader
 */
public class FontLoader
{
	private static final String FILE_HEADER = "common ";
	private static final String CHAR_HEADER = "char ";

	// Static variables change per Font
	//Set int the loadFontData method
	public static int sheetWidth, sheetHeight; // Fix later, this is stupid

	/**
	 * For static initialization
	 * Call before the ResourceManager
	 */
	public static void init()
	{

	}

	/**
	 * Make sure not to use any extensions
	 * Looks in the "/res" location
	 *
	 * @param fileName the location of the font texture and data files
	 * @return the Font object that contains this fonts data
	 */
	/*public static Font loadFont(String fileName, String name)
	{
		return new Font(name, VaoLoader.loadTexture(fileName), loadFontData(fileName), sheetWidth, sheetHeight, 8, 10);
	}*/

	/**
	 * Reads the .fnt file to get the font data
	 *
	 * @param fileName the file location of the .fnt file
	 * @return the CharatcerData array
	 */
	public static List<CharacterData> loadFontData(String fileName)
	{
		List<CharacterData> characters = new ArrayList<>();

		try
		{
			File dataFile = new File(fileName);
			FileReader fileReader = new FileReader(dataFile);
			BufferedReader reader = new BufferedReader(fileReader);

			while (true)
			{
				String line = reader.readLine();
				if (line == null)
					break;

				// Read the main file data (Height and Width of sheet)
				if (line.startsWith(FILE_HEADER))
				{
					String[] FileData = line.split(" ");
					String sheetWidthString = FileData[3].split("=")[1];
					String sheetHeightString = FileData[4].split("=")[1];
					sheetWidth = Integer.parseInt(sheetWidthString);
					sheetHeight = Integer.parseInt(sheetHeightString);
				}

				// Read the data for each character and add to the list
				if (line.startsWith(CHAR_HEADER))
				{
					String[] data = line.split(" ");
					int[] numberData = new int[9];

					int realIndex = 0; // Used to keep track of valid data indices
					for (int i = 0; i < data.length; i++)
					{
						if (data[i].length() <= 0 || data[i].startsWith("c"))
							continue;
						String[] data2 = data[i].split("=");
						numberData[realIndex] = Integer.parseInt(data2[1]);
						realIndex++;
					}

					characters.add(new CharacterData(numberData[0], numberData[1], numberData[2],
							numberData[3], numberData[4], numberData[5], numberData[6], numberData[7]));
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return characters;
	}

}
