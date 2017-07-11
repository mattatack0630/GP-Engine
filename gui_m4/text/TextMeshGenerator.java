package gui_m4.text;

import org.lwjgl.opengl.GL15;
import utils.VaoLoader;
import rendering.VaoObject;
import utils.math.Maths;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * The TextMeshGenerator Class
 * <p>
 * This class generates the mesh that is used to render a text
 */
public class TextMeshGenerator
{
	public static final VaoObject NEW_VAO = null;

	/**
	 * Used for static initialization
	 * Called after the VaoLoader class
	 */
	public static void init()
	{
	}

	/**
	 * Generate a mesh for each character in the text
	 *
	 * @param text the text to be drawn
	 * @param font the font to render the text in
	 * @param VAO  the vao to load this mesh to, use NEW_VAO if this is a new text object
	 * @return the new Mesh of this text
	 */
	public static VaoObject generateTextVao(String text, TextAttributes attribs, Font font, VaoObject VAO)
	{
		List<Float> positions = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		Vector2f max = new Vector2f(-1000000, -100000);
		Vector2f min = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);

		// Split text into wrapped lines, so that they are getSplit per word instead of per character
		String[] lines = splitAtLineBreaks(text, attribs.getMaxLineLength());
		float[] positionsArray;
		float[] textureCoordsArray;
		int cursorX = 0;
		int cursorY = 0;

		// for each line
		for (int i = 0; i < lines.length; i++)
		{
			String line = lines[i];

			// for each character
			for (int j = 0; j < line.length(); j++)
			{
				char c = line.charAt(j);
				CharacterData data = font.getData(c);

				if (data == null)
					continue;

				float[] verticies = generateQuad(data, font, cursorX, cursorY);
				float[] uvCoords = generateTextureCoords(data, font);

				for (int k = 0; k < verticies.length; k++)
				{
					positions.add(verticies[k]);
					textureCoords.add(uvCoords[k]);
				}

				//Advance the cursor x and y
				cursorX += data.advanceX - font.getPadding();
			}

			// Reset cursor to new line after each line
			cursorY += attribs.getLineSpacing();
			cursorX = 0;
		}

		positionsArray = new float[positions.size()];
		textureCoordsArray = new float[textureCoords.size()];

		for (int i = 0; i < positions.size(); i++)
		{
			positionsArray[i] = positions.get(i);
			textureCoordsArray[i] = textureCoords.get(i);
		}

		for (int i = 0; i < positionsArray.length; i += 2)
		{
			Vector2f pos = new Vector2f(positionsArray[i + 0], positionsArray[i + 1]);

			min.setX((pos.x() < min.x()) ? pos.x() : min.x());
			min.setY((pos.y() < min.y()) ? pos.y() : min.y());
			max.setX((pos.x() > max.x()) ? pos.x() : max.x());
			max.setY((pos.y() > max.y()) ? pos.y() : max.y());
		}

		VaoObject vaoObj = VAO;
		if (VAO == null)
		{
			vaoObj = VaoLoader.loadModel(positionsArray, textureCoordsArray);
			vaoObj.vertexCount = positionsArray.length / 2;
		} else
		{
			vaoObj.update(positionsArray, VaoObject.POSITIONS, GL15.GL_STREAM_DRAW);
			vaoObj.update(textureCoordsArray, VaoObject.TEXTURE_COORDS, GL15.GL_STREAM_DRAW);
			vaoObj.vertexCount = positionsArray.length / 2;
		}

		vaoObj.maxPoint = new Vector3f(max.x(), Math.abs(Math.abs(max.y()) - Math.abs(min.y())), 0);
		vaoObj.minPoint = new Vector3f(0, 0, 0);
		return vaoObj;
	}

	/**
	 * Generate the textureCoords based on the font character data
	 *
	 * @param data the font sheets data for this character
	 * @param font the font to use
	 * @return the float array of texture coordinates
	 */
	private static float[] generateTextureCoords(CharacterData data, Font font)
	{
		float[] result = new float[12];
		float uvX = (data.coordX) / (float) (font.getWidth());
		float uvY = (data.coordY) / (float) (font.getHeight());
		float uvW = (data.width) / (float) (font.getWidth());
		float uvH = (data.height) / (float) (font.getHeight());

		//v1
		result[0] = uvX;
		result[1] = uvY;
		//v2
		result[2] = uvX;
		result[3] = uvY + uvH;
		//v4
		result[4] = uvX + uvW;
		result[5] = uvY + uvH;
		//v1
		result[10] = uvX;
		result[11] = uvY;
		//v4
		result[6] = uvX + uvW;
		result[7] = uvY + uvH;
		//v3
		result[8] = uvX + uvW;
		result[9] = uvY;

		return result;
	}

	/**
	 * Generate the quad for a character
	 *
	 * @param data    the data for this character
	 * @param font    the font to use
	 * @param cursorX the x axis of the cursor to offset the quad
	 * @return the positions array of the quad
	 */
	private static float[] generateQuad(CharacterData data, Font font, int cursorX, int cursorY)
	{
		float[] result = new float[12];
		Vector2f glCoords = Maths.glLengthFromPixle(new Vector2f(data.width, data.height));
		Vector2f glOffset = Maths.glLengthFromPixle(new Vector2f(cursorX + data.offX, -data.offY - cursorY));
		//v1
		result[0] = glOffset.x();//+.1f; italics
		result[1] = glOffset.y();
		//v2
		result[2] = glOffset.x();
		result[3] = -glCoords.y() + glOffset.y();
		//v4
		result[4] = glCoords.x() + glOffset.x();
		result[5] = -glCoords.y() + glOffset.y();
		//v3
		result[8] = glCoords.x() + glOffset.x();//+.1f; italics
		result[9] = glOffset.y();
		//v1
		result[10] = glOffset.x();//+.1f; italics
		result[11] = glOffset.y();
		//v4
		result[6] = glCoords.x() + glOffset.x();
		result[7] = -glCoords.y() + glOffset.y();

		return result;
	}

	private static String[] splitAtLineBreaks(String text, float maxLineLength)
	{
		List<String> lines = new ArrayList<String>();
		String[] words = text.split(" ");

		String currentLine = "";
		for (int i = 0; i < words.length; i++)
		{
			if (currentLine.length() + words[i].length() <= maxLineLength)
			{
				currentLine += words[i] + " ";
			} else
			{
				if (currentLine.length() > 0)
					lines.add(currentLine);
				currentLine = words[i] + " ";
			}

			if (i == words.length - 1)
				lines.add(currentLine);
		}

		String[] stringArray = new String[lines.size()];
		for (int i = 0; i < lines.size(); i++)
			stringArray[i] = lines.get(i);

		return stringArray;
	}
}
