package resources;

import models.RenderMaterial;
import models.StaticModel;
import parsing.lime.LimeData;
import parsing.lime.LimeModelBuilder;
import parsing.lime.LimeParser;
import utils.VaoObject;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class StaticModelResource extends Resource
{
	public static final String DIFFUSE_FOLDER = "diffuse_maps/";
	public static final String NORMAL_FOLDER = "normal_maps/";
	public static final String INFO_FOLDER = "info_maps/";

	public static final String MODELS_FOLDER = "res/models/static_models/";
	public static final String MODEL_EXT = ".lime";

	public StaticModel model;
	public TextureResource textureMapRes;
	public TextureResource normalMapRes;
	public TextureResource infoMapRes;

	public StaticModelResource(String name, String location)
	{
		super(name, MODELS_FOLDER + location + MODEL_EXT);
	}

	@Override
	public void load(ResourceManager resManager)
	{
		// Parse the lime file
		LimeData data = LimeParser.parseLimeFile(location);
		LimeModelBuilder modelBuilder = new LimeModelBuilder(data);

		// Build an static vaoObject VAO
		VaoObject staticVao = modelBuilder.buildStaticVao();

		// Get Material
		RenderMaterial material = modelBuilder.buildMaterial();

		// Change naming later so they can be found in map easier
		textureMapRes = resManager.loadResource(new TextureResource(data.textureMapFile.getName(),
				DIFFUSE_FOLDER + data.textureMapFile.getPath()));
		normalMapRes = resManager.loadResource(new TextureResource(data.normalMapFile.getName(),
				NORMAL_FOLDER + data.normalMapFile.getPath()));
		infoMapRes = resManager.loadResource(new TextureResource(data.specMapFile.getName(),
				INFO_FOLDER + data.specMapFile.getPath()));

		// Build textured vaoObject
		model = new StaticModel(staticVao, material, data.getBoundingBox(), textureMapRes, normalMapRes, infoMapRes);
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

	public StaticModel getModel()
	{
		return model;
	}
}
