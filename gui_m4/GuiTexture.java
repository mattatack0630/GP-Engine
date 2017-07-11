package gui_m4;


import gui_m4.GuiRenderable;
import rendering.Color;
import rendering.renderers.GuiRenderer;
import shaders.GuiShader;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector4f;

public class GuiTexture extends GuiRenderable
{
	public static final int NO_TEXTURE = -1;
	public static final int RENDER_AT_END = -1;

	private int texture;

	private Vector2f size;
	private Vector2f position;
	private Vector4f textureCoords;

	private Color color;
	private float opacity;
	private float rotation;

	public GuiTexture(int texture, Vector2f position, Vector2f size)
	{
		super(GuiShader.TEXTURE_TYPE);
		this.texture = texture;
		this.color = new Color(0, 0, 0, 0);
		this.position = new Vector2f(position);
		this.size = new Vector2f(size);
		this.opacity = 1;
		this.renderLevel = RENDER_AT_END;
		this.textureCoords = new Vector4f(GuiRenderer.DEF_TEXTURE_COORDS);
	}

	public GuiTexture(Color color, Vector2f position, Vector2f size)
	{
		super(GuiShader.NO_TEXTURE_TYPE);
		this.texture = NO_TEXTURE;
		this.color = new Color(color);
		this.position = new Vector2f(position);
		this.size = new Vector2f(size);
		this.opacity = 1;
		this.renderLevel = RENDER_AT_END;
		this.textureCoords = new Vector4f(GuiRenderer.DEF_TEXTURE_COORDS);
	}

	public Color getColor()
	{
		return color;
	}

	public Vector2f getSize()
	{
		return size;
	}

	public int getTexture()
	{
		return texture;
	}

	public float getOpacity()
	{
		return opacity;
	}

	public float getRotation()
	{
		return rotation;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public float getRenderLevel()
	{
		return renderLevel;
	}

	public void setRenderLevel(float renderLevel)
	{
		this.renderLevel = renderLevel;
	}

	public void setPosition(Vector2f position)
	{
		this.position.set(position.x(), position.y());
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public void setTexture(int texture)
	{
		this.texture = texture;
		this.type = (texture == GuiTexture.NO_TEXTURE) ? GuiShader.NO_TEXTURE_TYPE : GuiShader.TEXTURE_TYPE;
	}

	public void setSize(Vector2f size)
	{
		this.size.set(size.x(), size.y());
	}

	public void setOpacity(float o)
	{
		this.opacity = o;
	}

	public void setColor(Color color)
	{
		this.color.setA(color.getA());
		this.color.setR(color.getR());
		this.color.setG(color.getG());
		this.color.setB(color.getB());
	}

	public Vector4f getTextureCoords()
	{
		return textureCoords;
	}

	public void setTextureCoords(Vector4f tc)
	{
		this.textureCoords = tc;
	}

	public void setFromSource(GuiTexture texture){

		this.texture = texture.getTexture();

		this.size.set(texture.getSize());
		this.position.set(texture.getPosition());
		this.textureCoords.set(texture.getTextureCoords());

		this.color.set(texture.getColor());
		this.opacity = texture.getOpacity();
		this.rotation = texture.getRotation();

		this.type = texture.getType();
		this.renderLevel = texture.getRenderLevel();
		this.clippingBounds = texture.getClippingBounds();
	}

	//public static GuiTexture fromSingleFile(){}
	//public static GuiTexture fromColor(){}
}
