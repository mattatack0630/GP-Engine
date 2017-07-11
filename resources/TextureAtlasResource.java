package resources;

import gui_m4.TextureAtlas;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import utils.math.linear.vector.Vector4f;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Map;

/**
 * Created by matthew on 7/8/17.
 */
public class TextureAtlasResource extends Resource
{
    private static final String SUB_TEX_Q_NAME = "SubTexture";
    private static final String SUB_TEX_NAME = "name";
    private static final String SUB_TEXT_WIDTH = "width";
    private static final String SUB_TEXT_HEIGHT = "height";
    private static final String SUB_TEXT_X = "x";
    private static final String SUB_TEXT_Y = "y";

    private TextureAtlas atlas;
    private String dataFileLoc;
    private String imageLoc;
    private boolean isPixels;

    public TextureAtlasResource(String name, String imageLoc, String dataFileLoc)
    {
        super(name, imageLoc);
        this.imageLoc = imageLoc;
        this.dataFileLoc = dataFileLoc;
        this.atlas = new TextureAtlas();
        this.isPixels = true;
    }

    @Override
    public void preloadOnDaemon()
    {
        try
        {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(new File(dataFileLoc), xmlHandler);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void load(ResourceManager resManager)
    {
        TextureResource texture = new TextureResource(name + "_texture", imageLoc);
        resManager.directLoadResource(texture);
        atlas.setTextureId(texture.getId());

        // Use textureSize to set coords
        Map<String, Vector4f> coordMap = atlas.getCoordinateMap();
        float h = isPixels ? texture.texData.getHeight() : 1.0f;
        float w = isPixels ? texture.texData.getWidth() : 1.0f;

        for (String name : coordMap.keySet())
        {
            Vector4f coord = coordMap.get(name);
            Vector4f newCoord = new Vector4f();
            newCoord.setX(coord.x() / w);
            newCoord.setY(coord.y() / h);
            newCoord.setZ((coord.x() + coord.z()) / w);
            newCoord.setW((coord.y() + coord.w()) / h);
            coordMap.put(name, newCoord);
        }
    }

    @Override
    public void unload()
    {
        // Textures will clean themselves up
    }

    @Override
    public void setId()
    {

        id = atlas.getTextureId();
    }

    public TextureAtlas getAtlas()
    {

        return atlas;
    }

    public void setPixelMode(boolean p)
    {

        this.isPixels = p;
    }

    private DefaultHandler xmlHandler = new DefaultHandler()
    {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            Map<String, Vector4f> atlasMap = atlas.getCoordinateMap();
            if (qName.equals(SUB_TEX_Q_NAME))
            {
                String name = attributes.getValue(SUB_TEX_NAME);
                float x = Float.parseFloat(attributes.getValue(SUB_TEXT_X));
                float y = Float.parseFloat(attributes.getValue(SUB_TEXT_Y));
                float w = Float.parseFloat(attributes.getValue(SUB_TEXT_WIDTH));
                float h = Float.parseFloat(attributes.getValue(SUB_TEXT_HEIGHT));
                Vector4f coords = new Vector4f(x, y, w, h);
                atlasMap.put(name, coords);
            }
        }
    };
}
