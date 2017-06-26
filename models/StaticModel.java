package models;

import resources.TextureResource;
import rendering.VaoObject;
import utils.math.geom.AABBmm;

/**
 * The Textured Model Class
 * <p>
 * Stores all the data required to render a static vaoObject
 * (Raw Model, Texture, Normal Map)
 */
public class StaticModel
{

	public VaoObject vaoObject;
	private TextureResource normalMap;
	private TextureResource infoMap;
	private TextureResource texture;
	private RenderMaterial material;
	private AABBmm boundingBox;

	public StaticModel(VaoObject model, RenderMaterial material, AABBmm bounds, TextureResource texture,
					   TextureResource normalMap, TextureResource infoMap)
	{
		this.vaoObject = model;
		this.material = material;
		this.infoMap = infoMap;
		this.texture = texture;
		this.normalMap = normalMap;
		this.boundingBox = bounds;
	}

	public VaoObject getVaoObject()
	{
		return vaoObject;
	}

	public RenderMaterial getMaterial()
	{
		return material;
	}

	public TextureResource getTexture()
	{
		return texture;
	}

	public TextureResource getInfoMap()
	{
		return infoMap;
	}

	public TextureResource getNormalMap()
	{
		return normalMap;
	}

	public AABBmm getBoundingBox()
	{
		return boundingBox;
	}
}
