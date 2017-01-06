package resources;

import utils.VaoLoader;
import utils.VaoObject;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager
{
	public static final String DEF_INFO_LOC = "defualt_info";
	public static final String DEF_TEX_LOC = "defualt_texture";
	public static final String DEF_NORM_LOC = "defualt_normal";

	private static float[] quadPositions = new float[]{-.5f, .5f, 0, -.5f, -.5f, 0, .5f, .5f, 0, .5f, -.5f, 0};
	private static float[] screenPositions = new float[]{-1f, 1f, 0, -1f, -1f, 0, 1f, 1f, 0, 1f, -1f, 0};

	private static VaoObject GuiQuad = VaoLoader.loadModel(3, quadPositions);
	private static VaoObject screenQuad = VaoLoader.loadModel(3, screenPositions);

	public static Map<String, Resource> resourceNameMap = new HashMap<>();

	public static void init()
	{
		loadInitResources();
	}

	public static void loadInitResources()
	{
		//Load defaults before everything
		loadResource(new StaticModelResource("defualtModel", "defualt_model"));
		loadResource(new TextureResource("defualtTexture", "diffuse_maps/defualt_texture"));
		loadResource(new TextureResource("defualtNone", "info_maps/defualt_info"));

		// Load Textures
		loadResource(new TextureResource("chrome_texture", "diffuse_maps/chrome"));
		loadResource(new TextureResource("barrel_texture", "diffuse_maps/barrel"));
		loadResource(new TextureResource("smoke", "particle_sheets/smoke"));

		// Load cube textures
		loadResource(new CubeTextureResource("sky", "sky/sky"));

		// Load Anim models
		loadResource(new AnimatedModelResource("barrel", "barrel"));
		loadResource(new AnimatedModelResource("blockBoy", "blockboy"));
		loadResource(new AnimatedModelResource("runModel", "character_run"));

		// Load Static models
		loadResource(new StaticModelResource("boneModel", "boneModel"));
		loadResource(new StaticModelResource("isoSphereModel", "isosphere"));
		loadResource(new StaticModelResource("planeModel", "planeModel"));
		loadResource(new StaticModelResource("barrelS", "barrel"));
		loadResource(new StaticModelResource("statue", "statue"));

		// Load fonts
		loadResource(new FontResource("Arial", "arial"));
		loadResource(new FontResource("Segoe", "Segoe"));
		loadResource(new FontResource("VinerHand", "VinerHand"));
	}

	public static Resource loadResource(Resource r)
	{
		// Check if loaded
		for (Resource resource : resourceNameMap.values())
			if (resource.equals(r))
				return resource;

		// If new resource
		r.load();
		r.setId();
		resourceNameMap.put(r.getName(), r);

		return r;
	}

	public static int dynamicLoadResource()
	{
		return 0;
	}

	public static int unloadResources()
	{
		return 0;
	}

	public static AnimatedModelResource getAnimatedModel(String modelName)
	{
		Resource model = findResource(modelName);
		if (model instanceof AnimatedModelResource)
			return (AnimatedModelResource) model;

		System.err.println("Type error :" + modelName);

		return null;
	}

	public static StaticModelResource getStaticModel(String modelName)
	{
		Resource model = findResource(modelName);
		if (model instanceof StaticModelResource)
			return (StaticModelResource) model;

		System.err.println("Type error :" + modelName);

		return null;
	}

	public static FontResource getFont(String fontName)
	{
		Resource font = findResource(fontName);
		if (font instanceof FontResource)
			return (FontResource) font;

		System.err.println("Type error :" + fontName);

		return null;
	}

	public static TextureResource getTextureResource(String texName)
	{
		Resource font = findResource(texName);
		if (font instanceof TextureResource)
			return (TextureResource) font;

		System.err.println("Type error :" + texName);

		return null;
	}

	public static CubeTextureResource getCubeTexture(String texName)
	{
		Resource cubeTexture = findResource(texName);
		if (cubeTexture instanceof CubeTextureResource)
			return (CubeTextureResource) cubeTexture;

		System.err.println("Type error :" + texName);

		return null;

	}

	public static <E extends Resource> E getResource(String res)
	{
		Resource resource = findResource(res);
		E eResource = null;

		if (resource != null)
		{
			try
			{
				eResource = (E) resource;
			} catch (ClassCastException e)
			{
				System.err.println("The resource you requested is not of the correct type");
				e.printStackTrace();
			}
		}

		return eResource;
	}

	public static Resource findResource(String res)
	{
		if (resourceNameMap.containsKey(res))
			return resourceNameMap.get(res);

		System.err.println("No resource of the name: " + res);
		return null;
	}

	public static void cleanUp()
	{
		for (Resource r : resourceNameMap.values())
			r.cleanUp();
	}

	public static VaoObject getGuiQuad()
	{
		return GuiQuad;
	}

	public static VaoObject getScreenQuad()
	{
		return screenQuad;
	}

}
