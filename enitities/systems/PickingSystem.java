package enitities.systems;

import enitities.Entity;
import enitities.components.PickingComponent;
import enitities.components.RenderComponent;
import input.picker.PickingManager;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/22/2016.
 */
public class PickingSystem extends EntitySystem
{
	Map<Entity, PickingComponent> pickingComponents = new HashMap<>();
	Map<Entity, RenderComponent> renderComponents = new HashMap<>();

	@Override
	public void addEntity(Entity e)
	{
		PickingComponent pickingComponent = e.getComponent(PickingComponent.class);
		RenderComponent renderComponent = e.getComponent(RenderComponent.class);

		if (pickingComponent != null && renderComponent != null)
		{
			pickingComponents.put(e, pickingComponent);
			renderComponents.put(e, renderComponent);
		}
	}

	@Override
	public void removeEntity(Entity e)
	{
		pickingComponents.remove(e);
		renderComponents.remove(e);
	}

	@Override
	public void tick()
	{
		for (Entity e : pickingComponents.keySet())
		{
			PickingComponent pickingComponent = pickingComponents.get(e);
			RenderComponent renderComponent = renderComponents.get(e);

			pickingComponent.setPicked(false);
			PickingManager.processPickingMesh(pickingComponent, renderComponent.getRenderData());
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{

	}
}
