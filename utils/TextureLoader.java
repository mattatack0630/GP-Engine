package utils;

import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.opengl.Texture;
import resources.TextureData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mjmcc on 11/25/2016.
 */
public class TextureLoader
{
    public static TextureData decodeTextureFile(String fileName)
    {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        try
        {
            FileInputStream in = new FileInputStream(fileName);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.RGBA);
            buffer.flip();
            in.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Tried to load texture " + fileName + ", didn't work");
            System.exit(-1);
        }
        return new TextureData(width, height, -1, buffer);
    }

    public static int loadTexture(String textureFile)
    {
        Texture texture = null;
        try
        {
            texture = org.newdawn.slick.opengl.TextureLoader.getTexture("PNG", new FileInputStream("res/" + textureFile + ".png"));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        int textureID = texture.getTextureID();
        return textureID;
    }
}
