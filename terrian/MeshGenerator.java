package terrian;

import org.lwjgl.opengl.GL15;
import rendering.VaoObject;
import utils.math.Maths;
import utils.math.PseudoNoise;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 9/29/2016.
 */
public class MeshGenerator
{
	private int seed;

	public MeshGenerator()
	{
		seed = (int) (Math.random() * 1000000);
	}

	public VaoObject generateMesh(int w, int h, float n, VaoObject test, float amp, float freq, int oct, float per)
	{
		int w1 = w + 1;
		int[] indices = new int[w * h * 6];
		Vertex[][] vertices = new Vertex[w + 1][h + 1];
		PseudoNoise noise = new PseudoNoise(seed);
		noise.setAmplitude(amp);
		noise.setOctaves(oct);
		noise.setFrequency(freq);
		noise.setPersistence(per);
		int tileSize = 10;

		for (int i = 0; i < w + 1; i++)
		{
			for (int j = 0; j < h + 1; j++)
			{
				float f = noise.generateNoise(i * n / tileSize, j * n / tileSize);
				vertices[i][j] = new Vertex(new Vector3f(i * n, f, j * n));
				vertices[i][j].setTextureCoord(new Vector2f((float) i / w, (float) j / h));
			}
		}

		for (int i = 0; i < w; i++)
		{
			for (int j = 0; j < h; j++)
			{
				Vertex v1 = vertices[i][j];
				Vertex v2 = vertices[i + 1][j];
				Vertex v3 = vertices[i][j + 1];
				Vertex v4 = vertices[i + 1][j + 1];

				// Triangle 1
				generateTriangle(v1, v4, v2);
				indices[(j * w + i) * 6 + 0] = (j * w1 + i);
				indices[(j * w + i) * 6 + 1] = ((j + 1) * w1 + (i + 1));
				indices[(j * w + i) * 6 + 2] = (j * w1 + (i + 1));

				// Triangle 2
				generateTriangle(v1, v3, v4);
				indices[((j * w + i)) * 6 + 3] = (j * w1 + i);
				indices[((j * w + i)) * 6 + 4] = ((j + 1) * w1 + i);
				indices[((j * w + i)) * 6 + 5] = ((j + 1) * w1 + (i + 1));
			}
		}

		for (int i = 0; i < w + 1; i++)
			for (int j = 0; j < h + 1; j++)
				vertices[i][j].averageNormals();

		float normals[] = new float[(h + 1) * (w + 1) * 3];
		float tangents[] = new float[(h + 1) * (w + 1) * 3];
		float positions[] = new float[(h + 1) * (w + 1) * 3];
		float textureCoords[] = new float[(h + 1) * (w + 1) * 2];
		loadArrays(vertices, positions, normals, tangents, textureCoords);

		test.bind();
		test.update(positions, VaoObject.POSITIONS, GL15.GL_STATIC_DRAW);
		test.update(normals, VaoObject.NORMALS, GL15.GL_STATIC_DRAW);
		test.update(tangents, VaoObject.TANGENTS, GL15.GL_STATIC_DRAW);
		test.update(textureCoords, VaoObject.TEXTURE_COORDS, GL15.GL_STATIC_DRAW);
		test.updateIndexArray(indices, GL15.GL_STATIC_DRAW);
		test.unbind();

		return test;//VaoLoader.loadModel(positions, textureCoords, normals, tangents, indices);
	}

	private void generateTriangle(Vertex v1, Vertex v2, Vertex v3)
	{
		Vector3f n1 = calcNormal(v1.getPosition(), v2.getPosition(), v3.getPosition());
		Vector3f t1 = Maths.calculateTangent(v1.getPosition(), v2.getPosition(), v3.getPosition(),
				v1.getTextureCoord(), v2.getTextureCoord(), v3.getTextureCoord());

		v1.addNormal(n1);
		v2.addNormal(n1);
		v3.addNormal(n1);
		v1.addTangents(t1);
		v2.addTangents(t1);
		v3.addTangents(t1);
	}

	private void loadArrays(Vertex[][] vertices, float[] positions, float[] normals, float[] tangents, float[] textureCoords)
	{
		int w = vertices.length;
		int h = vertices[0].length;

		for (int i = 0; i < w; i++)
		{
			for (int j = 0; j < h; j++)
			{
				positions[(j * (w) + i) * 3 + 0] = vertices[i][j].getPosition().x();
				positions[(j * (w) + i) * 3 + 1] = vertices[i][j].getPosition().y();
				positions[(j * (w) + i) * 3 + 2] = vertices[i][j].getPosition().z();

				normals[(j * (w) + i) * 3 + 0] = vertices[i][j].getNormal().x();
				normals[(j * (w) + i) * 3 + 1] = vertices[i][j].getNormal().y();
				normals[(j * (w) + i) * 3 + 2] = vertices[i][j].getNormal().z();

				tangents[(j * (w) + i) * 3 + 0] = vertices[i][j].getTangent().x();
				tangents[(j * (w) + i) * 3 + 1] = vertices[i][j].getTangent().y();
				tangents[(j * (w) + i) * 3 + 2] = vertices[i][j].getTangent().z();

				textureCoords[(j * (w) + i) * 2 + 0] = vertices[i][j].getTextureCoord().x();
				textureCoords[(j * (w) + i) * 2 + 1] = vertices[i][j].getTextureCoord().y();
			}
		}
	}

	public Vector3f calcNormal(Vector3f v1, Vector3f v2, Vector3f v3)
	{
		Vector3f s1 = Vector3f.sub(v1, v2, null);
		Vector3f s2 = Vector3f.sub(v1, v3, null);
		return Vector3f.cross(s1, s2, null);
	}
}
