package resources;

import models.AnimatedModel;
import models.RenderMaterial;
import models.StaticModel;
import parsing.lime.LimeData;
import parsing.lime.LimeModelBuilder;
import parsing.lime.LimeParser;
import utils.VaoObject;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class AnimatedModelResource extends Resource
{
	public static final String DIFFUSE_FOLDER = "diffuse_maps/";
	public static final String NORMAL_FOLDER = "normal_maps/";
	public static final String INFO_FOLDER = "info_maps/";

	public static final String MODELS_FOLDER = "res/models/animated_models/";
	public static final String MODEL_EXT = ".lime";

	public AnimatedModel model;
	public TextureResource textureMapRes;
	public TextureResource normalMapRes;
	public TextureResource infoMapRes;

	public AnimatedModelResource(String name, String location)
	{
		super(name, MODELS_FOLDER + location + MODEL_EXT);
	}

	@Override
	public void load()
	{
		// Parse the lime file
		LimeData data = LimeParser.parseLimeFile(location);
		LimeModelBuilder modelBuilder = new LimeModelBuilder(data);

		// Build an static vaoObject VAO
		VaoObject animVao = modelBuilder.buildAnimatedVao();

		// Get Material
		RenderMaterial material = modelBuilder.buildMaterial();

		textureMapRes = (TextureResource) ResourceManager.loadResource(new TextureResource(data.textureMapFile.getName(),
				DIFFUSE_FOLDER + data.textureMapFile.getPath()));
		normalMapRes = (TextureResource) ResourceManager.loadResource(new TextureResource(data.normalMapFile.getName(),
				NORMAL_FOLDER + data.normalMapFile.getPath()));
		infoMapRes = (TextureResource) ResourceManager.loadResource(new TextureResource(data.specMapFile.getName(),
				INFO_FOLDER + data.specMapFile.getPath()));

		// Build textured vaoObject
		StaticModel texturedModel = new StaticModel(animVao, material, data.getBoundingBox(),
				textureMapRes, normalMapRes, infoMapRes);

		// Build anim vaoObject
		model = new AnimatedModel(texturedModel, data.bones, data.animationsList);
	}

	@Override
	public void setId()
	{
		id = model.getVaoObject().getId();
	}

	@Override
	public void cleanUp()
	{
		// textures will be cleaned in the Res manager by themselves
		model.getVaoObject().clean();
	}

	public AnimatedModel getAnimatedModel()
	{
		return model;
	}
}
