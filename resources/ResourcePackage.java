package resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 1/6/2017.
 * <p>
 * The ResourcePackage is used to store
 * a set of resource together, so that they can be easily
 * loaded and unloaded by the ResourceManager.
 */
public class ResourcePackage
{
	// List containing all of the resources in this package
	private List<Resource> resources;

	public ResourcePackage()
	{
		resources = new ArrayList<>();
	}

	public ResourcePackage(int i)
	{
		resources = new ArrayList<>(i);
	}

	public ResourcePackage(List<Resource> startResources)
	{
		resources = new ArrayList<>(startResources);
	}

	/**
	 * Add a resource to this package's list
	 *
	 * @param resource the resource to be appended
	 */
	public void addResource(Resource resource)
	{
		resources.add(resource);
	}

	/**
	 * Get this package's resource list
	 *
	 * @return a reference to this packages resource list
	 */
	public List<Resource> getResources()
	{
		return resources;
	}

	/**
	 * Check if the package is loaded. Right now, this method simply checks
	 * if the last resource in this package is loaded, this will work if the package
	 * is loaded only using the ResourceManager
	 *
	 * @return whether or not this package is loaded
	 */
	public boolean isLoaded()
	{
		Resource resource = resources.isEmpty() ? null : resources.get(resources.size() - 1);
		return resource != null && resource.isLoaded();
	}
}
