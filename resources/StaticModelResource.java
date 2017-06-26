package resources;

import models.RenderMaterial;
import models.StaticModel;
import parsing.lime.LimeData;
import parsing.lime.LimeModelBuilder;
import parsing.lime.LimeParser;
import rendering.VaoObject;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class StaticModelResource extends Resource
{
	private LimeData limeData;
	private LimeModelBuilder modelBuilder;

	public StaticModel model;
	public TextureResource textureMapRes;
	public TextureResource normalMapRes;
	public TextureResource infoMapRes;

	public StaticModelResource(String name, String location)
	{
		super(name, location);
	}

	@Override
	public void preloadOnDaemon()
	{
		//Parse the lime file
		limeData = LimeParser.parseLimeFile(location);
		modelBuilder = new LimeModelBuilder(limeData);
	}

	@Override
	public void load(ResourceManager resManager)
	{
		// Build an static vaoObject VAO
		VaoObject staticVao = modelBuilder.buildStaticVao();

		// Get Material
		RenderMaterial material = modelBuilder.buildMaterial();

		// Change naming later so they can be found in map easier
		textureMapRes = resManager.directLoadResource(new TextureResource(limeData.textureMapFile.getName(),
				limeData.textureMapFile.getPath()));
		normalMapRes = resManager.directLoadResource(new TextureResource(limeData.normalMapFile.getName(),
				limeData.normalMapFile.getPath()));
		infoMapRes = resManager.directLoadResource(new TextureResource(limeData.specMapFile.getName(),
				limeData.specMapFile.getPath()));

		// Build textured vaoObject
		model = new StaticModel(staticVao, material, limeData.getBoundingBox(), textureMapRes, normalMapRes, infoMapRes);
	}

	@Override
	public void unload()
	{
		// textures will be cleaned in the Res manager by themselves
		model.getVaoObject().clean();
	}

	@Override
	public void setId()
	{
		id = model.getVaoObject().getId();
	}


	public StaticModel getModel()
	{
		return model;
	}
}
