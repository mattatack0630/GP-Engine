package utils.math;

import java.util.Random;

/**
 * Created by mjmcc on 9/29/2016. With help from ThinMatrix :)
 */
public class PseudoNoise
{
	private float amplitude;
	private int octaves;
	private float frequency;
	private float persistence;

	private Random random = new Random();
	private int seed;

	public PseudoNoise()
	{
		this.seed = random.nextInt(1000000000);
		amplitude = 75f;
		octaves = 3;
		frequency = 0.3f;
		persistence = 1;
	}

	public PseudoNoise(int seed)
	{
		this.seed = seed;
		amplitude = 400;
		octaves = 3;
		frequency = 0.3f;
		persistence = 1;
	}

	public float generateNoise(float x, float z)
	{
		float total = getInterpolatedNoise(x * frequency, z * frequency) * amplitude;
		for (int i = 1; i < octaves; i++)
		{
			total += getInterpolatedNoise(x / (((i + 1) * persistence)), z / ((i + 1) * persistence)) * amplitude / ((i + 1) * 2);
		}
		return total;
	}

	private float getInterpolatedNoise(float x, float z)
	{
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x - intX;
		float fracZ = z - intZ;

		float v1 = getAverageNoise(intX, intZ);
		float v2 = getAverageNoise(intX + 1, intZ);
		float v3 = getAverageNoise(intX, intZ + 1);
		float v4 = getAverageNoise(intX + 1, intZ + 1);
		float i1 = cosInterpolate(v1, v2, fracX);
		float i2 = cosInterpolate(v3, v4, fracX);
		return cosInterpolate(i1, i2, fracZ);
	}

	private float cosInterpolate(float a, float b, float blend)
	{
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}

	private float getAverageNoise(int x, int z)
	{
		float corners = (getRandom(x - 1, z - 1) + getRandom(x + 1, z - 1) + getRandom(x - 1, z + 1)
				+ getRandom(x + 1, z + 1));// / 16f;
		float sides = (getRandom(x - 1, z) + getRandom(x + 1, z) + getRandom(x, z - 1)
				+ getRandom(x, z + 1));// / 8f;
		float center = getRandom(x, z);// / 4f;
		return (corners + sides + center) / 9;
	}

	private float getRandom(int x, int z)
	{
		random.setSeed(x * 49632 + z * 325176 + seed);
		return random.nextFloat() * 2f - 1f;
	}

	public void setAmplitude(float amplitude)
	{
		this.amplitude = amplitude;
	}

	public void setOctaves(int octaves)
	{
		this.octaves = octaves;
	}

	public void setFrequency(float frequency)
	{
		this.frequency = frequency;
	}

	public void setPersistence(float persistence)
	{
		this.persistence = persistence;
	}
}
