package resources;

import models.AnimatedModel;
import models.RenderMaterial;
import models.StaticModel;
import parsing.lime.LimeData;
import parsing.lime.LimeModelBuilder;
import parsing.lime.LimeParser;
import rendering.VaoObject;

/**
 * Created by mjmcc on 11/22/2016.
 */
public class AnimatedModelResource extends Resource
{
	private LimeData limeData;
	private LimeModelBuilder modelBuilder;

	public AnimatedModel model;
	public TextureResource textureMapRes;
	public TextureResource normalMapRes;
	public TextureResource infoMapRes;

	public AnimatedModelResource(String name, String location)
	{
		super(name, location);
	}

	@Override
	public void preloadOnDaemon()
	{
		// Parse the lime file
		limeData = LimeParser.parseLimeFile(location);
		modelBuilder = new LimeModelBuilder(limeData);
	}

	@Override
	public void load(ResourceManager resManager)
	{
		// Build an static vaoObject VAO
		VaoObject animVao = modelBuilder.buildAnimatedVao();

		// Get Material
		RenderMaterial material = modelBuilder.buildMaterial();

		textureMapRes = resManager.directLoadResource(new TextureResource(limeData.textureMapFile.getName(),
				limeData.textureMapFile.getPath()));
		normalMapRes = resManager.directLoadResource(new TextureResource(limeData.normalMapFile.getName(),
				limeData.normalMapFile.getPath()));
		infoMapRes = resManager.directLoadResource(new TextureResource(limeData.specMapFile.getName(),
				limeData.specMapFile.getPath()));

		// Build textured vaoObject
		StaticModel texturedModel = new StaticModel(animVao, material, limeData.getBoundingBox(),
				textureMapRes, normalMapRes, infoMapRes);

		// Build anim vaoObject
		model = new AnimatedModel(texturedModel, limeData.bones, limeData.animationsList);
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

	public AnimatedModel getAnimatedModel()
	{
		return model;
	}
}
