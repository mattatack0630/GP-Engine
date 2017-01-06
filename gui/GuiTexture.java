package gui;


import rendering.Color;
import utils.math.linear.vector.Vector2f;

public class GuiTexture {
	public static final int NO_TEXTURE = -1;
	
	private int texture;
	private float opacity;
	private Color color;
	private Vector2f position;
	private Vector2f size;
	
	public GuiTexture(int texture, Vector2f position, Vector2f size){
		this.texture = texture;
		this.color = new Color(1,1,1,1);
		this.position = position;
		this.size = size;
		this.opacity = 1;
	}
	
	public GuiTexture(Color color, Vector2f position, Vector2f size){
		this.texture = NO_TEXTURE;
		this.color = color;
		this.position = position;
		this.size = size;
		this.opacity = 1;
	}
	
	public GuiTexture(int texture, Color color, Vector2f position, Vector2f size){
		this.texture = texture;
		this.color = color;
		this.position = position;
		this.size = size;
		this.opacity = 1;
	}

	public int getTexture() {
		return texture;
	}

	public void setTexture(int texture) {
		this.texture = texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public void setColor(Color color){
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public void setOpacity(float o)
	{
		this.opacity = o;
	}

	public float getOpacity()
	{
		return opacity;
	}
}
