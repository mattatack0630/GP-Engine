package gui.Text;

/**
 * Character data class
 *
 * Hold the character information for generation
 * */
public class CharacterData
{
	public int characterASCII; // Ascii number
	public int coordX, coordY; // X and Y coordinates on the font sheet
	public int width, height;  // Width and height of the character of the sheet
	public int offX, offY; 	// Screen offset to the virtual cursor
	public int advanceX;    // X cursor movement after this character is generated

	public CharacterData(int characterASCII, int coordX, int coordY, int width, int height, int offX, int offY, int advanceX)
	{
		this.characterASCII = characterASCII;
		this.coordX = coordX;
		this.coordY = coordY;
		this.width = width;
		this.height = height;
		this.offX = offX;
		this.offY = offY;
		this.advanceX = advanceX;
	}
}
