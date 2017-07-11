package gui_m4;

import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthew on 7/8/17.
 */
public class TextureAtlas
{
    private int texture;
    private Map<String, Vector4f> coordinateMap;

    public TextureAtlas()
    {
        this.texture = -1;
        this.coordinateMap = new HashMap<>();
    }

    public void setTextureId(int id)
    {
        
        this.texture = id;
    }

    public void addCoordinate(String name, Vector4f coord)
    {

        coordinateMap.put(name, coord);
    }

    public Vector4f getCoordinate(String name)
    {

        return coordinateMap.get(name);
    }

    public int getTextureId()
    {

        return texture;
    }

    public GuiTexture generateTextureObject(String name)
    {
        GuiTexture textureObj = new GuiTexture(texture, new Vector2f(), new Vector2f(1));

        Vector4f coords = coordinateMap.get(name);
        if (coords != null)
            textureObj.setTextureCoords(coords);

        return textureObj;
    }

    public Map<String,Vector4f> getCoordinateMap()
    {
        return coordinateMap;
    }
}
