package particles;

import rendering.RenderData;
import utils.math.linear.rotation.Euler;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 12/27/2016.
 */
public class ParticleRenderData extends RenderData
{
	private Vector4f currStage;
	private Vector4f postStage;
	private float stageProgression;

	public ParticleRenderData(Vector3f pos, Rotation rot, Vector3f s)
	{
		super(pos, rot, s);
		currStage = new Vector4f();
		postStage = new Vector4f();
	}

	public ParticleRenderData copy()
	{
		RenderData copy = super.copy();
		ParticleRenderData pcopy = new ParticleRenderData(new Vector3f(), new Euler(), new Vector3f());
		pcopy.transform = copy.transform;
		pcopy.postStage = this.postStage;
		pcopy.currStage = this.currStage;
		return pcopy;
	}

	public void setCurrStage(Vector4f currStage)
	{
		this.currStage = currStage;
	}

	public void setPostStage(Vector4f postStage)
	{
		this.postStage = postStage;
	}

	public void setStageProgression(float stageProgression)
	{
		this.stageProgression = stageProgression;
	}

	public float getStageProgression()
	{
		return stageProgression;
	}

	public Vector4f getCurrStage()
	{
		return currStage;
	}

	public Vector4f getPostStage()
	{
		return postStage;
	}

}
