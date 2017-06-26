package gui;

import gui.transition.Transition;
import gui.components.Component;
import rendering.renderers.MasterRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mjmcc on 3/14/2017.
 */
public class GuiManager
{
	private Map<GuiScene, List<Transition>> sceneTransitions;
	private List<Component> sceneComponents;

	public GuiManager()
	{
		sceneTransitions = new HashMap<>();
		sceneComponents = new ArrayList<>();
	}

	/**
	 * Tick each GuiScene in the managers list.
	 * This method also goes through each scenes transitions and applies them if
	 * they are not finished.
	 * */
	public void update()
	{
		for (GuiScene scene : sceneTransitions.keySet())
		{
			List<Transition> transitions = sceneTransitions.get(scene);

			for (int i = transitions.size() - 1; i >= 0; i--)
			{
				Transition t = transitions.get(i);

				t.applyTransition(scene);

				if (t.isFinished())
					transitions.remove(t);
			}

			//scene.tick();
		}

		for (Component component : sceneComponents)
			component.tick();
		// check disputing clicks/ hovers in component scenes, resolve to the top component
	}

	/**
	 * Render each Component in the managers list.
	 * Render in order of each components renderLevel
	 * */
	public void render(MasterRenderer renderer)
	{
		for (Component component : sceneComponents)
			component.render(renderer);
	}

	/**
	 * Add a GuiScene to the managers list.
	 * This method will add both a scene to the transitions map
	 * and also order the scenes child components based on render level
	 * */
	public void show(GuiScene scene)
	{
		if(!sceneTransitions.containsKey(scene))
		{
			List<Component> components = scene.getAllChildren();

			sceneComponents.addAll(components);
			//for (Component component : components)
			//	Sorter.dynamicSort(sceneComponents, component);

			sceneTransitions.put(scene, new ArrayList<>());
		}
	}

	/**
	 * Remove a GuiScene from the manager.
	 * This method also removes each child component of the scene
	 * from the managers list.
	 * */
	public void hide(GuiScene scene)
	{
		List<Component> components = scene.getAllChildren();

		for (Component component : components)
			sceneComponents.remove(component);

		sceneTransitions.remove(scene);
	}

	/**
	 * Apply a transition to a GuiScene, this method also adds the scene to
	 * the managers list if it isn't already.
	 * */
	public void applyTransition(Transition transition, GuiScene scene)
	{
		List<Transition> transitions = sceneTransitions.containsKey(transition) ? sceneTransitions.get(scene) : new ArrayList<>();
		transitions.add(transition);
		sceneTransitions.put(scene, transitions);
	}
}
