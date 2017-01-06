package resources;

import utils.VaoLoader;
import utils.VaoObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by mjmcc 1/6/2017
 * <p>
 * The ResourceManager is a static util class that handles
 * loading and unloading resources.
 */

public class ResourceManager
{
	// Maximum amount of resources that can be loaded/unloaded per update
	private static final int MAX_LOADS_PER_CYCLE = 3;

	// Map containing loaded resources and their names as keys
	private static Map<String, Resource> resourceNameMap;

	// Queues to hold resources that are queued for loading/unloading
	private static List<Resource> loadQueue;
	private static List<Resource> unloadQueue;

	/**
	 * Initialize the resource manager
	 */
	public static void init()
	{
		resourceNameMap = new HashMap<>();
		loadQueue = new ArrayList<>();
		unloadQueue = new ArrayList<>();
	}

	/**
	 * Load a single resource.
	 * This method will block until the resource is completely loaded.
	 * Once the resource is loaded it is added to the {@link #resourceNameMap}
	 * and can be accessed by calling the generic {@link #getResource(String)}
	 * method using the resource name as a parameter.
	 */
	private static Resource loadResource(Resource r)
	{
		// Check if the same as another resource
		for (Resource resource : resourceNameMap.values())
			if (resource.equals(r) && resource.isLoaded())
				return resource;

		// If new resource
		if (!r.isLoaded())
		{
			r.load();
			r.setId();
			r.setLoaded(true);
			resourceNameMap.put(r.getName(), r);
		}

		return r;
	}

	/**
	 * Unload a single resource.
	 * This method will block until the resource is completely unloaded.
	 * Once the resource is unloaded it is removed from the {@link #resourceNameMap}
	 */
	private static Resource unloadResource(Resource r)
	{
		if (r.isLoaded())
		{
			r.cleanUp();
			r.setLoaded(false);
			resourceNameMap.remove(r.getName());
		}

		return r;
	}

	/**
	 * Load a package of resources.
	 * This method will add a resource package's resources to the
	 * managers {@link #loadQueue} and can be loaded on a background thread
	 * using the {@link #runBackgroundManagement()} method. You can find
	 * this packages load status using the packages isLoaded variable.
	 *
	 * @param resourcePackage the package to be loaded
	 * @param block           whether or not this method should block until the package is done loading
	 */
	public static void loadResourcePackage(ResourcePackage resourcePackage, boolean block)
	{
		loadQueue.addAll(resourcePackage.getResources());

		while (!resourcePackage.isLoaded() && block)
		{
			try
			{
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Unload a package of resources.
	 * This method will add a resource package's resources to the
	 * managers {@link #unloadQueue} and can be unloaded on a background thread
	 * using the {@link #runBackgroundManagement()} method. You can find
	 * this packages load status using the packages isLoaded variable.
	 *
	 * @param resourcePackage the package to be unloaded
	 * @param block           whether or not this method should block until the package is done unloading
	 */
	public static void unloadResourcePackage(ResourcePackage resourcePackage, boolean block)
	{
		unloadQueue.addAll(resourcePackage.getResources());

		while (resourcePackage.isLoaded() && block)
		{
			try
			{
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method runs the background management of resource.
	 * It is meant to be called on a separate thread, so as to not block the current one.
	 * Mainly this method is used to load and unload resources from the queues in the background.
	 */
	public static void runBackgroundManagement()
	{
		int toLoad = Math.min(loadQueue.size(), MAX_LOADS_PER_CYCLE);
		int toUnload = Math.min(unloadQueue.size(), MAX_LOADS_PER_CYCLE);

		for (int i = 0; i < toLoad; i++)
		{
			Resource resource = loadQueue.remove(0);
			loadResource(resource);
		}

		for (int i = 0; i < toUnload; i++)
		{
			Resource resource = unloadQueue.remove(0);
			unloadResource(resource);
		}
	}

	/**
	 * Get a generic resource by name. This method will generically cast to
	 * the specified type parameter. It searches the {@link #resourceNameMap} and
	 * returns the resource stored at the given name, or {@code null} if their is no
	 * such resource.
	 *
	 * @param res the name of the resource to find in the map
	 * @return the resource with that name, or {@code null} if it doesn't exist
	 */
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
				return null;
			}
		}

		return eResource;
	}

	/**
	 * Find a resource in the {@link #resourceNameMap}, when given its name.
	 *
	 * @param res the resources name
	 * @return the resource stored at that name, or {@code null} if it does not exist
	 */
	private static Resource findResource(String res)
	{
		if (resourceNameMap.containsKey(res))
			return resourceNameMap.get(res);

		System.err.println("No resource of the name: " + res);
		return null;
	}

	/**
	 * Clean up and unload the resources still stored in the {@link #resourceNameMap}.
	 */
	public static void cleanUp()
	{
		for (Resource r : resourceNameMap.values())
			r.cleanUp();
	}


	public static void loadTestResources()
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

		// Load audio
		loadResource(new SoundResource("knock", "door_converted"));
	}

	/*TODO remove these*/
	private static float[] quadPositions = new float[]{-.5f, .5f, 0, -.5f, -.5f, 0, .5f, .5f, 0, .5f, -.5f, 0};
	private static float[] screenPositions = new float[]{-1f, 1f, 0, -1f, -1f, 0, 1f, 1f, 0, 1f, -1f, 0};

	private static VaoObject GuiQuad = VaoLoader.loadModel(3, quadPositions);
	private static VaoObject screenQuad = VaoLoader.loadModel(3, screenPositions);

	public static VaoObject getGuiQuad()
	{
		return GuiQuad;
	}

	public static VaoObject getScreenQuad()
	{
		return screenQuad;
	}
}
