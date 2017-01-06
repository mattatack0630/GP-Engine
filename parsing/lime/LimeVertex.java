package parsing.lime;


import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

/**
 * Created by mjmcc on 11/23/2016.
 */
public class LimeVertex
{
	private int index;
	private Vector3f position;
	private Vector3f normal;
	private Vector3f tangent;
	private Vector2f textureCoord;
	private Vector4f boneIndex;
	private Vector4f boneWeight;

	public LimeVertex(Vector3f p)
	{
		position = p;
		normal = new Vector3f();
		tangent = new Vector3f();
		textureCoord = new Vector2f();
		boneIndex = new Vector4f(-1, -1, -1, -1);
		boneWeight = new Vector4f(0, 0, 0, 0);
	}

	public void addTangent(Vector3f v)
	{
		Vector3f.add(tangent, v, tangent);
		tangent.normalize();
	}

	public void setNormal(Vector3f n)
	{
		normal = n;
	}

	public void setTextureCoord(Vector2f t)
	{
		textureCoord = t;
	}

	public void addBone(float i, float w)
	{
		if (boneIndex.x() != -1)
		{
			boneIndex.setX(i);
			boneWeight.setX(w);
		} else if (boneIndex.y() != -1)
		{
			boneIndex.setY(i);
			boneWeight.setY(w);
		} else if (boneIndex.z() != -1)
		{
			boneIndex.setZ(i);
			boneWeight.setZ(w);
		} else if (boneIndex.w() != -1)
		{
			boneIndex.setW(i);
			boneWeight.setW(w);
		}
	}
}
