package gui.XML;

import gui.Component.*;
import gui.Component.Panel.HPanel;
import gui.Component.Panel.Panel;
import gui.Component.Panel.VPanel;
import gui.Text.TextAttributes;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 3/14/2017.
 */
public class ComponentDictionary
{
	public static Component getComponent(String s)
	{
		Component c = null;

		switch (s.toLowerCase())
		{
			case "panel":
				c = new Panel(new Vector2f());
				break;

			case "hpanel":
				c = new HPanel();
				break;

			case "vpanel":
				c = new VPanel();
				break;

			case "animatedtexture":
				c = new AnimatedTextureComponent();
				break;

			case "button":
				c = new ButtonComponent(new Vector2f());
				break;

			case "checkbox":
				c = new CheckBoxComponent();
				break;

			case "label":
				c = new LabelComponent("", "Arial", new TextAttributes());
				break;

			case "radio":
				c = new RadioComponent();
				break;

			case "slider":
				c = new SliderComponent(new Vector2f());
				break;

			case "textinput":
				c = new TextInputComponent();
				break;

			case "texture":
				//c = new TextureComponent();
				break;

			default:
				System.out.println("Error : Could not generate component from DOM-Element");
				break;
		}

		return c;
	}
}
