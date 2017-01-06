package parsing.utils;

import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

import java.util.List;

/**
 * Created by mjmcc on 10/29/2016.
 */
public class ParsingUtils
{
	public static Vector toVector(String[] str)
	{
		// Validate input here
		Vector result = new Vector(toFloatArray(str));
		if (str.length == 2)
			result = new Vector2f(result.elements[0], result.elements[1]);
		if (str.length == 3)
			result = new Vector3f(result.elements[0], result.elements[1], result.elements[2]);
		if (str.length == 4)
			result = new Vector4f(result.elements[0], result.elements[1], result.elements[2], result.elements[3]);

		return result;
	}

	public static Vector toVector(float[] str)
	{
		// Validate input here
		Vector result = new Vector(str);

		return result;
	}

	public static float[] toFloatArray(String[] stringArray)
	{
		float[] farray = new float[stringArray.length];

		for (int i = 0; i < stringArray.length; i++)
			farray[i] = Float.parseFloat(stringArray[i]);

		return farray;
	}

	public static float[] vec3ToFloatArray(List<Vector3f> vecList)
	{
		float[] farray = new float[vecList.size() * 3];

		for (int i = 0; i < vecList.size(); i++)
		{
			farray[i * 3 + 0] = vecList.get(i).x();
			farray[i * 3 + 1] = vecList.get(i).y();
			farray[i * 3 + 2] = vecList.get(i).z();
		}

		return farray;
	}

	public static float[] vec2ToFloatArray(List<Vector2f> vecList)
	{
		float[] farray = new float[vecList.size() * 2];

		for (int i = 0; i < vecList.size(); i++)
		{
			farray[i * 2 + 0] = vecList.get(i).x();
			farray[i * 2 + 1] = vecList.get(i).y();
		}

		return farray;
	}

	public static float[] vec4ToFloatArray(List<Vector4f> vecList)
	{
		float[] farray = new float[vecList.size() * 4];

		for (int i = 0; i < vecList.size(); i++)
		{
			farray[i * 4 + 0] = vecList.get(i).x();
			farray[i * 4 + 1] = vecList.get(i).y();
			farray[i * 4 + 2] = vecList.get(i).z();
			farray[i * 4 + 3] = vecList.get(i).w();
		}

		return farray;
	}

	public static int[] vec3ToIntArray(List<Vector3f> vecList)
	{
		int[] iarray = new int[vecList.size() * 3];

		for (int i = 0; i < vecList.size(); i++)
		{
			iarray[i * 3 + 0] = (int) vecList.get(i).x();
			iarray[i * 3 + 1] = (int) vecList.get(i).y();
			iarray[i * 3 + 2] = (int) vecList.get(i).z();
		}

		return iarray;
	}

	public static int[] toIntArray(List<Float> flist)
	{
		return null;
	}

	public static Matrix4f toMatrix(String[] str, boolean rowWise)
	{
		float[] floatArray = toFloatArray(str);

		return toMatrix(floatArray, rowWise);
	}

	public static Matrix4f toMatrix(float[] floatArray, boolean rowWise)
	{
		Matrix4f mat4 = new Matrix4f();

		if (rowWise)
			for (int i = 0; i < 16; i++)
				mat4.elements[i / 4][i % 4] = floatArray[i];
		else
			for (int i = 0; i < 16; i++)
				mat4.elements[i % 4][i / 4] = floatArray[i];
/*
		if (rowWise)
		{
			mat4.m00 = floatArray[0];
			mat4.m01 = floatArray[1];
			mat4.m02 = floatArray[2];
			mat4.m03 = floatArray[3];
			mat4.m10 = floatArray[4];
			mat4.m11 = floatArray[5];
			mat4.m12 = floatArray[6];
			mat4.m13 = floatArray[7];
			mat4.m20 = floatArray[8];
			mat4.m21 = floatArray[9];
			mat4.m22 = floatArray[10];
			mat4.m23 = floatArray[11];
			mat4.m30 = floatArray[12];
			mat4.m31 = floatArray[13];
			mat4.m32 = floatArray[14];
			mat4.m33 = floatArray[15];
		} else
		{
			mat4.m00 = floatArray[0];
			mat4.m10 = floatArray[1];
			mat4.m20 = floatArray[2];
			mat4.m30 = floatArray[3];
			mat4.m01 = floatArray[4];
			mat4.m11 = floatArray[5];
			mat4.m21 = floatArray[6];
			mat4.m31 = floatArray[7];
			mat4.m02 = floatArray[8];
			mat4.m12 = floatArray[9];
			mat4.m22 = floatArray[10];
			mat4.m32 = floatArray[11];
			mat4.m03 = floatArray[12];
			mat4.m13 = floatArray[13];
			mat4.m23 = floatArray[14];
			mat4.m33 = floatArray[15];
		}
		*/
		return mat4;
	}

	public static float[][] splitFloatArray(float[] fullArray, int stride)
	{
		float[][] arrays = new float[fullArray.length / stride][stride];

		for (int i = 0; i < fullArray.length; i++)
			arrays[i / stride][i % stride] = fullArray[i];

		return arrays;
	}
}