package gui_m4.text;

import engine.Engine;
import gui_m4.GuiRenderable;
import rendering.RenderData;
import rendering.VaoObject;
import resources.FontResource;
import shaders.GuiShader;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * GuiText Class
 * <p>
 * The GuiText is the interface that is be used to render
 * text to the screen. Use attributes to affect how the text is rendered
 */
public class GuiText extends GuiRenderable
{
	private Font font;
	private String text;
	private VaoObject mesh;
	private RenderData transform;
	private TextAttributes attribs;
	private Vector2f size;
	private float opacity;

	public GuiText(String text, String fontName, Vector2f pos, TextAttributes attribs)
	{
		super(GuiShader.TEXT_TYPE);
		FontResource fontResource = Engine.getResourceManager().getResource(fontName);
		this.font = fontResource.getFont();
		this.text = text;
		this.attribs = attribs;
		this.transform = new RenderData(new Vector3f(pos.x(), pos.y(), 0));
		this.mesh = TextMeshGenerator.generateTextVao(this.text, this.attribs, this.font, TextMeshGenerator.NEW_VAO);
		this.size = new Vector2f(mesh.maxPoint.x(), mesh.maxPoint.y());
		this.opacity = 1.0f;
		this.renderLevel = 100;
	}

	public GuiText(String text, Vector2f pos, TextAttributes attribs)
	{
		super(GuiShader.TEXT_TYPE);
		FontResource fontResource = Engine.getResourceManager().getResource(attribs.getFont());
		this.font = fontResource.getFont();
		this.text = text;
		this.attribs = attribs;
		this.transform = new RenderData(new Vector3f(pos.x(), pos.y(), 0));
		this.mesh = TextMeshGenerator.generateTextVao(this.text, this.attribs, this.font, TextMeshGenerator.NEW_VAO);
		this.size = new Vector2f(mesh.maxPoint.x(), mesh.maxPoint.y());
		this.opacity = 1.0f;
		this.renderLevel = 100;
	}

	/**
	 * Blank attributes return a black text with no outline or shadow
	 */
	public GuiText(String text, String fontName, Vector2f pos)
	{
		this(text, fontName, pos, new TextAttributes());

	}

	/**
	 * Must be called to checkSpawn the mesh when the text is changed
	 * VAO is reloaded with a new mesh every time checkSpawn is called
	 */
	public void update()
	{
		setFont(attribs.getFont());
		TextMeshGenerator.generateTextVao(this.text, this.attribs, this.font, this.mesh);
		size = new Vector2f(mesh.maxPoint.x(), mesh.maxPoint.y());
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getText()
	{

		return text;
	}

	public void setPosition(Vector2f pos)
	{
		float xoff = (getWidth()) * getAttribs().getAlignment().x();
		float yoff = (getHeight()) * getAttribs().getAlignment().y();
		transform.setPosition(new Vector3f(pos.x() + xoff, pos.y() + yoff, 0));
		transform.updateMatrix();
	}

	public VaoObject getMesh()
	{

		return mesh;
	}

	public RenderData getTransform()
	{

		return transform;
	}

	public Font getFont()
	{

		return font;
	}

	public TextAttributes getAttribs()
	{

		return attribs;
	}

	public void setAttribs(TextAttributes attribs)
	{

		this.attribs = attribs;
	}

	public void setRotation(float f)
	{

		transform.setRotation(new Euler(0, 0, f));
	}

	public void setFont(String font)
	{
		String thisFont = this.font.getName().toLowerCase();
		String otherFont = font.toLowerCase();

		if (!thisFont.equals(otherFont))
		{
			FontResource fontResource = Engine.getResourceManager().getResource(font);
			this.font = fontResource.getFont();
		}
	}

	public float getWidth()
	{
		if (size == null)
			return 0;
		return size.x() * attribs.getFontSize();
	}

	public float getHeight()
	{
		if (size == null)
			return 0;
		return size.y() * attribs.getFontSize();
	}

	public Vector2f getSize()
	{

		return new Vector2f(size).scale(attribs.getFontSize());
	}

	public Vector2f getCenterPosition()
	{
		Vector3f pos = transform.getPosition();
		return new Vector2f(pos.x() + getWidth() / 2f, pos.y() - getHeight() / 2f);
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
