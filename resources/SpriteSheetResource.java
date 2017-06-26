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
	private LisfData data;
	private SpriteSheet spriteSheet;
	private Map<String, SpriteModel> spriteModels;

	public SpriteSheetResource(String name, String location)
	{
		super(name, location);
		spriteModels = new HashMap<>();
	}

	@Override
	public void preloadOnDaemon()
	{
		data = LisfParser.parseLISF(location);
	}

	@Override
	public void load(ResourceManager resManager)
	{

		TextureResource spriteTexture = new TextureResource(name + "_texture", data.texturePath);
		TextureResource spriteNormal = new TextureResource(name + "_normal", data.normalPath);
		resManager.directLoadResource(spriteTexture);
		resManager.directLoadResource(spriteNormal);

		spriteSheet = new SpriteSheet(spriteTexture, spriteNormal, data.rowColumn);
		spriteSheet.setMagFilter(data.textureSmoothing ? GL11.GL_LINEAR : GL11.GL_NEAREST);

		LisfSpriteBuilder spriteBuilder = new LisfSpriteBuilder(data);
		spriteBuilder.buildSpriteAnimations(spriteSheet);
		spriteBuilder.buildSpriteModels(spriteSheet);
		spriteModels = spriteBuilder.getModels();
	}

	@Override
	public void unload()
	{
		// Textures will clean themselves up
	}

	@Override
	public void setId()
	{
		id = Maths.uniqueInteger();
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
