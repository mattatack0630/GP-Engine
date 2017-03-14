package resources;

import models.SpriteModel;
import models.SpriteSheet;
import org.lwjgl.opengl.GL11;
import parsing.lisf.LisfData;
import parsing.lisf.LisfParser;
import parsing.lisf.LisfSpriteBuilder;
import utils.math.Maths;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 1/11/2017.
 */
public class SpriteSheetResource extends Resource
{
	private static final String SPRITE_SHEET_DATA_LOC = "res/models/sprite_sheets/";
	private static final String SPRITE_SHEET_DATA_EXT = ".lisf";

	private LisfData data;
	private SpriteSheet spriteSheet;
	private Map<String, SpriteModel> spriteModels;

	public SpriteSheetResource(String name, String location)
	{
		super(name, SPRITE_SHEET_DATA_LOC + location + SPRITE_SHEET_DATA_EXT);
		spriteModels = new HashMap<>();
	}

	@Override
	public void load(ResourceManager resManager)
	{
		data = LisfParser.parseLISF(location);

		TextureResource spriteTexture = new TextureResource(name + "_texture", data.texturePath);
		TextureResource spriteNormal = new TextureResource(name + "_normal", data.normalPath);
		resManager.loadResource(spriteTexture);
		resManager.loadResource(spriteNormal);

		spriteSheet = new SpriteSheet(spriteTexture, spriteNormal, data.rowColumn);
		spriteSheet.setMagFilter(data.textureSmoothing ? GL11.GL_LINEAR : GL11.GL_NEAREST);

		LisfSpriteBuilder spriteBuilder = new LisfSpriteBuilder(data);
		spriteBuilder.buildSpriteAnimations(spriteSheet);
		spriteBuilder.buildSpriteModels(spriteSheet);
		spriteModels = spriteBuilder.getModels();
	}

	@Override
	public void setId()
	{
		id = Maths.uniqueInteger();
	}

	@Override
	public void cleanUp()
	{
		// Textures will clean themselves up
	}

	public SpriteModel getModel(String modelName)
	{
		return spriteModels.get(modelName);
	}

	public SpriteSheet getSpriteSheet()
	{
		return spriteSheet;
	}
}
