package particles;

import rendering.Color;
import rendering.RenderData;
import rendering.camera.Camera;
import utils.math.linear.rotation.Euler;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 12/27/2016.
 *
 * The particle renderData is used to render a particle within the ParticleRenderer.
 * This class also contains the data needed to sort the parent particle into the ParticlesRenderer's
 * distance List.
 */
public class ParticleRenderData extends RenderData implements Comparable
{
	private Vector4f currStage;
	private Vector4f postStage;
	private Color particleColor;
	private float stageProgression;
	private float camDist;

	public ParticleRenderData(Vector3f pos, Rotation rot, Vector3f s)
	{
		super(pos, rot, s);
		currStage = new Vector4f();
		postStage = new Vector4f();
		particleColor = new Color();
		camDist = Float.MAX_VALUE;
	}

	/**
	 * Copy this render data and return the new object
	 * */
	public ParticleRenderData copy()
	{
		RenderData copy = super.copy();
		ParticleRenderData pcopy = new ParticleRenderData(new Vector3f(), new Euler(), new Vector3f());
		pcopy.transform = copy.transform;
		pcopy.postStage = this.postStage;
		pcopy.currStage = this.currStage;
		pcopy.particleColor = this.particleColor;
		return pcopy;
	}

	/**
	 * Set the current particle stages texture coordinates
	 * */
	public void setCurrStage(Vector4f currStage)
	{
		this.currStage = currStage;
	}

	/**
	 * Set the post particle stages texture coordinates
	 * */
	public void setPostStage(Vector4f postStage)
	{
		this.postStage = postStage;
	}

	/**
	 * Set the progression into the current particle stage, based normally on the particles
	 * life progression
	 * */
	public void setStageProgression(float stageProgression)
	{
		this.stageProgression = stageProgression;
	}

	/**
	 * Set the particles tint color
	 * */
	public void setColor(Color color)
	{
		if(color != null)
		{
			this.particleColor.setA(color.getA());
			this.particleColor.setR(color.getR());
			this.particleColor.setG(color.getG());
			this.particleColor.setB(color.getB());
		}
	}

	/**
	 * Sets the particles general opacity
	 * */
	public void setOpacity(float opacity)
	{
		this.particleColor.setA(opacity);
	}

	/* Getters */
	public float getStageProgression()
	{
		return stageProgression;
	}

	public Color getParticleColor()
	{
		return particleColor;
	}

	public Vector4f getCurrStage()
	{
		return currStage;
	}

	public Vector4f getPostStage()
	{
		return postStage;
	}

	/**
	 * Recalculate the particles distance to the camera
	 * */
	public void recalculateCamDist(Camera camera)
	{
		this.camDist = Vector3f.sub(camera.getPosition(), getPosition(), null).lengthSquared();
	}

	/**
	 * Compare based on the particles distance to the camera
	 * */
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof ParticleRenderData)
		{
			ParticleRenderData other = (ParticleRenderData) o;
			if (other.camDist > this.camDist)
				return -1;
			if (other.camDist < this.camDist)
				return 1;
		}
		return 0;
	}

}
