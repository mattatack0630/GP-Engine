package enitities.systems;

import engine.Engine;
import enitities.Entity;
import enitities.components.PickingComponent;
import enitities.components.RenderComponent;
import rendering.renderers.MasterRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjmcc on 12/22/2016.
 */
public class PickingSystem extends EntitySystem
{
	private Map<Entity, PickingComponent> pickingComponents;
	private Map<Entity, RenderComponent> renderComponents;

	public PickingSystem()
	{
		pickingComponents = new HashMap<>();
		renderComponents = new HashMap<>();
	}

	@Override
	public void tick()
	{
		for (Entity e : pickingComponents.keySet())
		{
			PickingComponent pickingComponent = pickingComponents.get(e);
			RenderComponent renderComponent = renderComponents.get(e);

			pickingComponent.setPicked(false);
			Engine.getPickingManager().processPickingMesh(pickingComponent, renderComponent.getRenderData());
		}
	}

	@Override
	public void render(MasterRenderer renderer)
	{

	}

	@Override
	public void setupNeededComponents()
	{
		super.addNeededComponent(PickingComponent.class, pickingComponents);
		super.addNeededComponent(RenderComponent.class, renderComponents);
	}
}
