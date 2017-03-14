package parsing.lime;

import animation.Bone;
import animation.Keyframe;
import animation.ModelAnimation;
import parsing.utils.ParsingUtils;
import parsing.utils.Validator;
import utils.math.Maths;
import utils.math.geom.AABBmm;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.Quaternion;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mjmcc on 10/29/2016.
 */
public class LimeData
{
	public static final String DEF_INFO_LOC = "defualt_info";
	public static final String DEF_TEX_LOC = "defualt_texture";
	public static final String DEF_NORM_LOC = "defualt_normal";

	public List<Vector3f> vertices;
	public List<Vector2f> texture;
	public List<Vector3f> tangent;
	public List<Vector3f> normal;
	public List<Vector3f> faces;
	public List<Bone> bones;
	public List<Vector4f> boneSkinIndex;
	public List<Vector4f> boneSkinWeight;

	public List<LimeVertex> lvertices;

	public HashMap<String, ModelAnimation> animationsList;
	public float[] materialData;
	public File textureMapFile;
	public File normalMapFile;
	public File specMapFile;
	public String modelName;

	public AABBmm modelBounds;

	public LimeData()
	{
		lvertices = new ArrayList<>();

		vertices = new ArrayList<>();
		texture = new ArrayList<>();
		tangent = new ArrayList<>();
		normal = new ArrayList<>();
		faces = new ArrayList<>();
		bones = new ArrayList<>();
		boneSkinIndex = new ArrayList<>();
		boneSkinWeight = new ArrayList<>();
		animationsList = new HashMap<>();

		modelName = "Model@" + hashCode();

		specMapFile = new File(DEF_INFO_LOC);
		normalMapFile = new File(DEF_NORM_LOC);
		textureMapFile = new File(DEF_TEX_LOC);

		this.modelBounds = new AABBmm(AABBmm.BOUNDS_MIN, AABBmm.BOUNDS_MAX);
	}

	public void addTextureCoord(String content)
	{
		String[] elements = content.split(",");
		Vector2f v = (Vector2f) ParsingUtils.toVector(elements);
		v.setY(1 - v.y());
		texture.add(v);
	}

	public void addMaterial(String content)
	{
		String[] elements = content.split(",");
		materialData = ParsingUtils.toFloatArray(elements);
	}

	public void addKeyframe(String content)
	{
		String[] data = content.split(";");
		String name = data[0].toLowerCase().trim();
		int boneSid = Validator.isNumber(data[1]) ? Integer.parseInt(data[1].trim()) : -1;
		float timeAt = Validator.isNumber(data[2]) ? Float.parseFloat(data[2].trim()) : -1;
		float[] rotFloats = ParsingUtils.toFloatArray(data[3].trim().split(","));
		float[] posFloats = ParsingUtils.toFloatArray(data[4].trim().split(","));
		float[] scaleFloats = ParsingUtils.toFloatArray(data[5].trim().split(","));

		if (!animationsList.containsKey(name))
			animationsList.put(name, new ModelAnimation(name));

		ModelAnimation animationData = animationsList.get(name);

		animationData.addKeyframe(new Keyframe(timeAt,
				new Quaternion(rotFloats[0], rotFloats[1], rotFloats[2], rotFloats[3]),
				new Vector3f(posFloats[0], posFloats[1], posFloats[2]),
				new Vector3f(scaleFloats[0], scaleFloats[1], scaleFloats[2]),
				getBoneFromSid(boneSid)));
	}

	public void addVertex(String content)
	{
		String[] elements = content.split(",");
		Vector3f position = (Vector3f) ParsingUtils.toVector(elements);
		vertices.add(position);
		tangent.add(new Vector3f());

		// check min and max
		modelBounds.checkBoundsUpdate(position);
	}

	public void addNormal(String content)
	{
		String[] elements = content.split(",");
		normal.add((Vector3f) ParsingUtils.toVector(elements).normalize());
	}

	public void addBone(String content)
	{
		String[] data = content.split(";");
		int sid = Integer.parseInt(data[0].trim());
		int parentSid = Integer.parseInt(data[1].trim());
		Matrix4f initMatrix = ParsingUtils.toMatrix(data[2].split(","), false);

		Bone b = new Bone(sid, parentSid, initMatrix);
		bones.add(b);
	}

	public void addSkin(String content)
	{
		if (!Validator.containsRegex(content, ";", ","))
			return;

		String[] boneSkins = content.split(";");
		String[] index = new String[4];
		String[] weight = new String[4];

		for (int i = 0; i < 4; i++)
		{
			String[] s = boneSkins[i].split(",");
			String boneSid = Validator.isNumber(s[0]) ? s[0].trim() : "-1";
			String boneWeight = Validator.isNumber(s[1]) ? s[1].trim() : "0";
			index[i] = boneSid;
			weight[i] = boneWeight;
		}

		// Stored as SID's for now
		Vector4f i = (Vector4f) ParsingUtils.toVector(index);
		Vector4f w = (Vector4f) ParsingUtils.toVector(weight);
		if (w.lengthSquared() != 0) w.scale(1.0f / (w.x() + w.y() + w.z() + w.w()));

		boneSkinIndex.add(i);
		boneSkinWeight.add(w);
	}

	public void buildFace(String content)
	{
		String[] elements = content.split(";");
		faces.add((Vector3f) ParsingUtils.toVector(elements));
	}

	public Bone getBoneFromSid(int parentSid)
	{
		for (Bone b : bones)
			if (b.getSid() == parentSid)
				return b;
		return null;
	}

	public int getBoneIndexFromSid(int sid)
	{
		for (int i = 0; i < bones.size(); i++)
			if (bones.get(i).getSid() == sid)
				return i;

		return -1;
	}

	public void calculateTangents()
	{
		for (Vector3f face : faces)
		{
			Vector3f vert0 = vertices.get((int) face.x());
			Vector3f vert1 = vertices.get((int) face.y());
			Vector3f vert2 = vertices.get((int) face.z());

			Vector2f tex0 = texture.get((int) face.x());
			Vector2f tex1 = texture.get((int) face.y());
			Vector2f tex2 = texture.get((int) face.z());

			Vector3f tang0 = Maths.calculateTangent(vert0, vert1, vert2, tex0, tex1, tex2);

			tangent.set((int) face.x(), Vector3f.add(tangent.get((int) face.x()), tang0, null));
			tangent.set((int) face.y(), Vector3f.add(tangent.get((int) face.y()), tang0, null));
			tangent.set((int) face.z(), Vector3f.add(tangent.get((int) face.z()), tang0, null));
		}

		for (int i = 0; i < tangent.size(); i++)
		{
			Vector3f tang = tangent.get(i);
			if (tang.lengthSquared() != 0)
				tang.normalize();
		}
	}

	public void fillInBlanks()
	{
		int neededLength = vertices.size();
		boolean unfilledFlag = false;

		for (int i = texture.size(); i < neededLength; i++, unfilledFlag = true)
			texture.add(new Vector2f(0, 0));// Default text coord
		for (int i = normal.size(); i < neededLength; i++, unfilledFlag = true)
			normal.add(new Vector3f(0, 0, 1));// Default normal
		for (int i = boneSkinIndex.size(); i < neededLength; i++, unfilledFlag = true)
			boneSkinIndex.add(new Vector4f(-1, -1, -1, -1));// Default bone index
		for (int i = boneSkinWeight.size(); i < neededLength; i++, unfilledFlag = true)
			boneSkinWeight.add(new Vector4f(0, 0, 0, 0));// Default bone weight

		if (unfilledFlag)
		{
			System.out.println("Error in LIME parsing, unfilled data : ");
			printData();
		}
	}

	public void printData()
	{
		System.out.println();
		System.out.println("VERTICES " + vertices);
		System.out.println("NORMAL " + normal);
		System.out.println("TANGENTS " + tangent);
		System.out.println("TEXTURE " + texture);
		System.out.println("FACES " + faces);
		System.out.println("BONES " + bones);
		System.out.println("BONES INDEX " + boneSkinIndex);
		System.out.println("BONES WEIGHT " + boneSkinWeight);
		System.out.println("ANIMATIONS " + animationsList);
	}

	// Setters
	public void setTextureFile(String loc)
	{
		textureMapFile = new File(loc);
	}

	public void setNormalFile(String loc)
	{
		normalMapFile = new File(loc);
	}

	public void setSpecFile(String loc)
	{
		specMapFile = new File(loc);
	}

	// Getters
	public List<Vector4f> getBoneSkinWeight()
	{
		return boneSkinWeight;
	}

	public List<Vector4f> getBoneSkinIndex()
	{
		return boneSkinIndex;
	}

	public void setBones(List<Bone> bones)
	{
		this.bones = bones;
	}

	public float[] getMaterialData()
	{
		return materialData;
	}

	public List<Vector3f> getVertices()
	{
		return vertices;
	}

	public List<Vector2f> getTexture()
	{
		return texture;
	}

	public List<Vector3f> getTangent()
	{
		return tangent;
	}

	public List<Vector3f> getNormal()
	{
		return normal;
	}

	public List<Vector3f> getFaces()
	{
		return faces;
	}

	public List<Bone> getBones()
	{
		return bones;
	}

	public AABBmm getBoundingBox()
	{
		return modelBounds;
	}
}