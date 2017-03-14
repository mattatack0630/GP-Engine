package resources;

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
	private Map<String, Resource> resourceNameMap;

	// Queues to hold resources that are queued for loading/unloading
	private List<Resource> loadQueue;
	private List<Resource> unloadQueue;

	/**
	 * Initialize the resource manager
	 */
	public ResourceManager()
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
	public <E extends Resource> E loadResource(E r)
	{
		// Check if the same as another resource
		for (Resource resource : resourceNameMap.values())
			if (resource.equals(r) && resource.isLoaded())
				return (E) resource;

		// If new resource
		if (!r.isLoaded())
		{
			r.load(this);
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
	private Resource unloadResource(Resource r)
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
	public void loadResourcePackage(ResourcePackage resourcePackage, boolean block)
	{
		loadQueue.addAll(resourcePackage.getResources());

		while (!resourcePackage.isLoaded() && block)
			runBackgroundManagement();

		// reimplement ???
		/*while (!resourcePackage.isLoaded() && block)
		{
			try
			{
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		}*/
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
	public void unloadResourcePackage(ResourcePackage resourcePackage, boolean block)
	{
		unloadQueue.addAll(resourcePackage.getResources());

		while (resourcePackage.isLoaded() && block)
			runBackgroundManagement();

		// reimplement ???
		/*while (resourcePackage.isLoaded() && block)
		{
			try
			{
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}*/
	}

	/**
	 * This method runs the background management of resource.
	 * It is meant to be called on a separate thread, so as to not block the current one.
	 * Mainly this method is used to load and unload resources from the queues in the background.
	 */
	public void runBackgroundManagement()
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
	public <E extends Resource> E getResource(String res)
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
	private Resource findResource(String res)
	{
		if (resourceNameMap.containsKey(res))
			return resourceNameMap.get(res);

		System.err.println("No resource of the name: " + res);
		return null;
	}

	/**
	 * Clean up and unload the resources still stored in the {@link #resourceNameMap}.
	 */
	public void cleanUp()
	{
		for (Resource r : resourceNameMap.values())
			r.cleanUp();
	}
}
