package rendering;

import utils.math.Transform;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Euler;
import utils.math.linear.rotation.Quaternion;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

public class RenderData
{
	// Transformation data
	public Transform transform;

	// Picking data
	public Vector2f pickingId;

	// Boolean flags
	public boolean frustumCheck;

	// Debug data
	public Color tempColor = Color.NONE;

	public RenderData(Vector3f pos)
	{
		this(pos, new Euler(), new Vector3f(1, 1, 1));
	}

	public RenderData(Vector3f pos, Rotation rot, Vector3f s)
	{
		this.transform = new Transform(new Vector3f(pos), rot.copy(), new Vector3f(s));
		this.pickingId = Color.BLACK.rg();
		this.frustumCheck = true;
	}

	public RenderData(Matrix4f transformMatrix)
	{
		Vector[] vecs = Matrix4f.decompose(transformMatrix);

		this.transform = new Transform((Vector3f) vecs[0], new Quaternion((Vector4f) vecs[1]), (Vector3f) vecs[2]);

		this.pickingId = Color.BLACK.rg();
		this.frustumCheck = true;
	}

	public Matrix4f updateMatrix()
	{
		transform.update();
		return transform.getMatrix();
	}

	public RenderData copy()
	{
		RenderData copy = new RenderData(transform.getPosition(), transform.getRotation(), transform.getScale());
		return copy;
	}

	public Color getDebugColor()
	{
		return tempColor;
	}

	public Matrix4f getTransformMatrix()
	{
		return transform.getMatrix();
	}

	public Vector2f getPickingId()
	{
		return pickingId;
	}

	public void setPickingId(Vector2f p)
	{
		this.pickingId = p;
	}

	public boolean shouldCheckBounds()
	{
		return frustumCheck;
	}

	public Rotation getRotation()
	{
		return transform.getRotation();
	}

	public Vector3f getPosition()
	{
		return transform.getPosition();
	}

	public Vector3f getScale()
	{
		return transform.getScale();
	}

	public void setScale(Vector3f scale)
	{
		this.transform.setScale(scale);
	}

	public void setRotation(Rotation rotation)
	{
		this.transform.setRotation(rotation);
	}

	public void setPosition(Vector3f position)
	{
		this.transform.setPosition(position);
	}
}
