package tests;

import serialization.SerialArray;
import serialization.SerialDatabase;
import serialization.SerialField;
import serialization.SerialObject;
import tests.states.GOLADSavedGame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mjmcc on 4/24/2017.
 */
public class GOLADMetaSerializer
{
	public static final String GAME_SAVES_STR = "gameSaves";
	public static final String GAME_SAVES_AMT_STR = "savesAmt";

	public static final String GAME_SAVE_PREF_STR = "gameSave_";
	public static final String GAME_SAVE_NAME_STR = "name";
	public static final String GAME_SAVE_PATH_STR = "path";
	public static final String GAME_SAVE_ID_STR = "id";

	public void metaVarsToFile(GOLADGame metaVars, String file)
	{
		SerialDatabase database = new SerialDatabase("MV");

		SerialObject gameSaves = new SerialObject(GAME_SAVES_STR);
		gameSaves.addField(SerialField.Integer(GAME_SAVES_AMT_STR, metaVars.gameSaves.size()));

		int counter = 0;
		for (String path : metaVars.gameSaves.keySet())
		{
			GOLADSavedGame save = metaVars.gameSaves.get(path);
			SerialObject saveObj = new SerialObject(GAME_SAVE_PREF_STR + counter);
			saveObj.addArray(SerialArray.String(GAME_SAVE_NAME_STR, save.getGameName()));
			saveObj.addArray(SerialArray.String(GAME_SAVE_PATH_STR, save.getSavePath()));
			saveObj.addField(SerialField.Integer(GAME_SAVE_ID_STR, save.getGameId()));
			gameSaves.addObject(saveObj);
			counter++;
		}

		database.addObject(gameSaves);

		ByteBuffer buffer = ByteBuffer.allocate(database.calcLength());
		database.serialize(buffer);

		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buffer.array());
			fos.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void metaVarsFromFile(String filePath, GOLADGame vars)
	{
		SerialDatabase dataBase = null;

		try
		{
			Path path = Paths.get(filePath);
			byte[] data = Files.readAllBytes(path);

			ByteBuffer buffer = ByteBuffer.wrap(data);
			buffer.position(buffer.limit());

			dataBase = new SerialDatabase(filePath);
			dataBase.deserialize(buffer);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		SerialObject savesObject = dataBase.getObject(GAME_SAVES_STR);
		int savesAmt = savesObject.getField(GAME_SAVES_AMT_STR).asInteger();

		for (int i = 0; i < savesAmt; i++)
		{
			SerialObject saveObj = savesObject.getObject(GAME_SAVE_PREF_STR + i);
			int gameId = saveObj.getField(GAME_SAVE_ID_STR).asInteger();
			String gameName = saveObj.getArray(GAME_SAVE_NAME_STR).asString();
			String gamePath = saveObj.getArray(GAME_SAVE_PATH_STR).asString();

			GOLADSavedGame save = new GOLADSavedGame(gameId, gameName, gamePath);
			vars.gameSaves.put(gamePath, save);
		}
	}
}
