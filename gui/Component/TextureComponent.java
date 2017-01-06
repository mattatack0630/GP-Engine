package gui.Component;

import gui.Component.Content.TextureContent;
import resources.ResourceManager;
import resources.TextureResource;
import utils.math.linear.vector.Vector2f;

/**
 * Created by mjmcc on 10/8/2016.
 */
public class TextureComponent extends Component
{
	TextureContent textureContent;

	public TextureComponent(String textureLocation, Vector2f size)
	{
		super(new Vector2f());

		int id = ResourceManager.loadResource(new TextureResource(textureLocation, textureLocation)).getId();
		textureContent = new TextureContent(this, new Vector2f(), size, id);
		setContent(textureContent);
	}

	public void setImageSize(Vector2f size)
	{
		textureContent.updateDimension(absolutePos, size);
		calculateSize(size);
		hasUpdatedDimensions = true;
	}
}
