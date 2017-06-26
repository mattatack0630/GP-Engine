package shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import shaders.uniforms.Uniform;
import utils.math.linear.matrix.Matrix;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;
import utils.math.linear.vector.Vector4f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class ShaderProgram
{
	private static final String SHADER_FOLDER = "res/shaders/";

	private static int NO_ID = -1;
	private static int activeShader = 0;

	private int programID = NO_ID;
	private int vertexShaderID = NO_ID;
	private int fragmentShaderID = NO_ID;
	private int geometryShaderID = NO_ID;

	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	private Map<String, Integer> uniformVariables = new HashMap<>();

	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		vertexShaderID = loadShader(SHADER_FOLDER + vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(SHADER_FOLDER + fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();

		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);

		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
	}

	public ShaderProgram(String vertexFile, String geometryFile, String fragmentFile)
	{
		vertexShaderID = loadShader(SHADER_FOLDER + vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(SHADER_FOLDER + fragmentFile, GL20.GL_FRAGMENT_SHADER);
		geometryShaderID = loadShader(SHADER_FOLDER + geometryFile, GL32.GL_GEOMETRY_SHADER);
		programID = GL20.glCreateProgram();

		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		GL20.glAttachShader(programID, geometryShaderID);

		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
	}

	public void start()
	{
		if(activeShader != programID)
			GL20.glUseProgram(programID);
		activeShader = programID;
	}

	public void stop()
	{
		if(activeShader != 0)
			GL20.glUseProgram(0);
		activeShader = 0;
	}

	public void cleanUp()
	{
		stop();
		uniformVariables.clear();

		if (vertexShaderID != NO_ID)
		{
			GL20.glDetachShader(programID, vertexShaderID);
			GL20.glDeleteShader(vertexShaderID);
		}

		if (geometryShaderID != NO_ID)
		{
			GL20.glDetachShader(programID, geometryShaderID);
			GL20.glDeleteShader(geometryShaderID);
		}

		if (fragmentShaderID != NO_ID)
		{
			GL20.glDetachShader(programID, fragmentShaderID);
			GL20.glDeleteShader(fragmentShaderID);
		}

		GL20.glDeleteProgram(programID);
	}

	protected abstract void bindAttributes();

	protected void bindAttribute(int attribute, String variableName)
	{
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}

	protected int getUniformLocation(String uniformName)
	{
		if (!uniformVariables.containsKey(uniformName))
			uniformVariables.put(uniformName, GL20.glGetUniformLocation(programID, uniformName));
		int location = uniformVariables.get(uniformName);

		return location;
	}

	public void loadFloat(String variableName, float value)
	{
		int location = getUniformLocation(variableName);

		GL20.glUniform1f(location, value);
	}

	public void loadInteger(String variableName, int value)
	{
		int location = getUniformLocation(variableName);

		GL20.glUniform1i(location, value);
	}

	public void loadBoolean(String variableName, boolean value)
	{
		int location = getUniformLocation(variableName);

		GL20.glUniform1i(location, (value ? 1 : 0));
	}

	public void loadVector2(String variableName, Vector2f value)
	{
		int location = getUniformLocation(variableName);

		GL20.glUniform2f(location, value.x(), value.y());
	}

	public void loadVector3(String variableName, Vector3f value)
	{
		int location = getUniformLocation(variableName);

		GL20.glUniform3f(location, value.x(), value.y(), value.z());
	}

	public void loadVector4(String variableName, Vector4f value)
	{
		int location = getUniformLocation(variableName);

		GL20.glUniform4f(location, value.x(), value.y(), value.z(), value.w());
	}

	protected void loadVectorArray(String variableName, Vector3f[] vectors)
	{
		int location = getUniformLocation(variableName);
		float[] vectorsArray = new float[vectors.length * 3];

		for (int i = 0; i < vectors.length; i++)
		{
			vectorsArray[i * 3] = vectors[i].x();
			vectorsArray[i * 3 + 1] = vectors[i].y();
			vectorsArray[i * 3 + 2] = vectors[i].z();
		}
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(vectors.length * 3 + 10);
		floatBuffer.put(vectorsArray);
		floatBuffer.flip();
		GL20.glUniform3(location, floatBuffer);
	}

	protected void loadFloatArray(String variableName, float[] floats)
	{
		int location = getUniformLocation(variableName);
		float[] floatsArray = new float[floats.length];
		for (int i = 0; i < floats.length; i++)
		{
			floatsArray[i] = floats[i];
		}
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(floats.length);
		floatBuffer.put(floatsArray);
		floatBuffer.flip();
		GL20.glUniform1(location, floatBuffer);
	}

	public void loadMatrix(String variableName, Matrix4f matrix)
	{
		int location = getUniformLocation(variableName);

		Matrix.store(matrix, matrixBuffer);
		matrixBuffer.flip();

		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}

	public static int loadShader(String file, int type)
	{
		StringBuilder shaderSource = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e)
		{
			System.err.println("could not read file! ->" + file);
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("could not compile! ->" + file);
			System.exit(-1);
		}

		return shaderID;
	}

	public void loadMatrixArray(String variableName, Matrix4f[] matArray)
	{
		for (int i = 0; i < matArray.length; i++)
		{
			loadMatrix(variableName + "[" + i + "]", matArray[i]);
		}
	}

	public void loadTexture(String name, int texId, int place)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + place);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		GL20.glUniform1i(getUniformLocation(name), place);
	}

	public void loadTextureCube(String name, int texId, int place)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + place);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texId);
		GL20.glUniform1i(getUniformLocation(name), place);
	}

	public void loadUniform(Uniform uniform)
	{
		uniform.loadToShader();
	}

	public int getId()
	{
		return programID;
	}


	public static void stopActiveShaders()
	{
		if(activeShader != 0)
			GL20.glUseProgram(0);
		activeShader = 0;
	}



}
