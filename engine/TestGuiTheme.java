package engine;

import gui_m4.TextureAtlas;
import resources.TextureAtlasResource;
import resources.TextureResource;
import utils.math.linear.vector.Vector4f;

/**
 * Created by matthew on 7/6/17.
 */
public class TestGuiTheme
{
    private static final String themeImageFile = "res/textures/gui_textures/greySheet.png";
    private static final String themeDataFile = "res/textures/gui_textures/greySheet.xml";
    public static int THEME_TEXTURE_ID = -1;
    public static TextureAtlas ATLAS;
    public static Vector4f BUTTON_STATIC_COORDS;
    public static Vector4f BUTTON_HOVER_COORDS;
    public static Vector4f BUTTON_PRESS_COORDS;
    public static Vector4f CHECK_COORDS;
    public static Vector4f ARROW_UP_COORDS;
    public static Vector4f ARROW_DOWN_COORDS;
    public static Vector4f ARROW_LEFT_COORDS;
    public static Vector4f ARROW_RIGHT_COORDS;
    public static Vector4f H_SLIDER_COORDS;
    public static Vector4f V_SLIDER_COORDS;
    public static Vector4f CIRCLE_COORDS;

    public static void init()
    {
        TextureAtlasResource resource = new TextureAtlasResource("defaultTheme", themeImageFile, themeDataFile);
        Engine.getResourceManager().directLoadResource(resource);

        ATLAS = resource.getAtlas();
        THEME_TEXTURE_ID = ATLAS.getTextureId();

        BUTTON_STATIC_COORDS = ATLAS.getCoordinate("grey_button03.png");
        BUTTON_PRESS_COORDS = ATLAS.getCoordinate("grey_button14.png");
        BUTTON_HOVER_COORDS = ATLAS.getCoordinate("grey_button14.png");
        CHECK_COORDS = ATLAS.getCoordinate("grey_boxCheckmark.png");
        ARROW_UP_COORDS = ATLAS.getCoordinate("grey_sliderUp.png");
        ARROW_DOWN_COORDS = ATLAS.getCoordinate("grey_sliderDown.png");
        ARROW_LEFT_COORDS = ATLAS.getCoordinate("grey_sliderLeft.png");
        ARROW_RIGHT_COORDS = ATLAS.getCoordinate("grey_sliderRight.png");
        H_SLIDER_COORDS = ATLAS.getCoordinate("grey_sliderHorizontal.png");
        V_SLIDER_COORDS = ATLAS.getCoordinate("grey_sliderVertical.png");
        CIRCLE_COORDS = ATLAS.getCoordinate("grey_circle.png");
    }
}
