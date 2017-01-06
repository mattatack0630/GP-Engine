package models;

/**
 * Created by mjmcc on 11/30/2016.
 */
public class RenderMaterial
{
	public static final RenderMaterial FLAT_SURFACE = new RenderMaterial(0.0f, 0.0f, 0.0f, 12.0f, 0.3f);
	public static final RenderMaterial CHROME = new RenderMaterial(0.6f, 0.0f, 0.0f, 12.0f, 0.8f);
	public static final RenderMaterial DULL_METAL = new RenderMaterial(0.3f, 0.0f, 0.0f, 12.0f, 0.8f);
	public static final RenderMaterial GLASS = new RenderMaterial(0.4f, 0.6f, 1.33f, 12.0f, 0.8f);

	private float reflectiveFactor;

	private float refractiveFactor;
	private float refractiveIndex;

	private float specularDampening;
	private float specularFactor;

	public RenderMaterial(float refFactor, float refrFactor, float refrIndex, float specDampening, float specFactor)
	{
		this.reflectiveFactor = refFactor;
		this.refractiveFactor = refrFactor;
		this.refractiveIndex = refrIndex;
		this.specularDampening = specDampening;
		this.specularFactor = specFactor;
	}

	public float getReflectiveFactor()
	{
		return reflectiveFactor;
	}

	public float getRefractiveFactor()
	{
		return refractiveFactor;
	}

	public float getRefractiveIndex()
	{
		return refractiveIndex;
	}

	public float getSpecularDampening()
	{
		return specularDampening;
	}

	public float getSpecularFactor()
	{
		return specularFactor;
	}
}
