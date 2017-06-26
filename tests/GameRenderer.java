package tests;

import engine.Engine;
import gui.components.VPanel;
import rendering.Color;
import rendering.renderers.MasterRenderer;
import rendering.renderers.Trinket2D;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 3/3/2017.
 */
public class GameRenderer
{
	private static final float CELL_CHANGE_SPD = 3.0f;
	private static final float BORDER_SCALE = 0.95f;
	private static final float DOT_SCALE = 0.10f;

	private static float lastRender;

	public GameRenderer()
	{

	}

	public void render(MasterRenderer renderer, GOLADPlayInstance gameState)
	{
		CellGrid grid = gameState.getGrid();
		GameGui gameGui = gameState.getGui();
		GridData gridData = gameState.getGridData();

		VPanel gridPanel = gameGui.getGridPanel();
		Vector2f gridSize = gridPanel.absoluteSize;
		Vector2f gridPosition = gridPanel.absolutePos;

		// Render Background

		Trinket2D.setRenderLevel(-100);
		Trinket2D.setDrawColor(Color.BLACK);
		Trinket2D.drawRectangle(gridPosition, gridSize);

		Trinket2D.setRenderLevel(-101);
		Trinket2D.setDrawColor(new Color(0.847f, 0.811f, 0.898f, 1.0f));
		Trinket2D.drawRectangle(new Vector2f(), new Vector2f(2));

		float delta = (CELL_CHANGE_SPD * (Engine.getTime() - lastRender));
		lastRender = Engine.getTime();

		// Render Cells
		Trinket2D.setRenderLevel(1);
		for (int i = 0; i < grid.getWidth(); i++)
		{
			for (int j = 0; j < grid.getHeight(); j++)
			{
				Cell c = grid.getCell(i, j);
				CellEnvironmentData cellData = gridData.getCellEnvironmentMap().get(c);
				renderCell(c, cellData, delta);
			}
		}

		Trinket2D.setRenderLevel(0);
	}

	public void renderCell(Cell cell, CellEnvironmentData cellData, float delta)
	{
		Vector3f pos = cell.getPosition();
		Vector3f scale = cell.getScale();
		int state = ConwayRules.resolveConwayRuling(cell, cellData);

		float dc = cell.getDeltaC();
		Vector3f oldColor = cell.getOldColor();
		Vector3f newColor = cell.getNewColor();

		if (dc < 1.0f)
		{
			Trinket2D.setRenderLevel(0);
			Trinket2D.setDrawColor(new Color(oldColor.x(), oldColor.y(), oldColor.z(), 0.7f));
			Trinket2D.drawRectangle(new Vector2f(pos.x(), pos.z()), new Vector2f(scale.x() * BORDER_SCALE, scale.z() * BORDER_SCALE));
			Trinket2D.setRenderLevel(1);
			Trinket2D.setDrawColor(new Color(newColor.x(), newColor.y(), newColor.z(), 1.0f));
			Trinket2D.drawRectangle(new Vector2f(pos.x(), pos.z()), new Vector2f(scale.x() * BORDER_SCALE * dc, scale.z() * BORDER_SCALE * dc));
			cell.setDeltaC(dc + delta);
		} else
		{
			Trinket2D.setRenderLevel(0);
			Trinket2D.setDrawColor(new Color(newColor.x(), newColor.y(), newColor.z(), 0.7f));
			Trinket2D.drawRectangle(new Vector2f(pos.x(), pos.z()), new Vector2f(scale.x() * BORDER_SCALE, scale.z() * BORDER_SCALE));
			cell.setOldColor(newColor);
		}

		if (state == ConwayRules.DIED)
		{
			Trinket2D.setRenderLevel(2);
			Trinket2D.setDrawColor(Color.BLACK);
			Trinket2D.drawRectangle(new Vector2f(pos.x(), pos.z()), new Vector2f(scale.x() * DOT_SCALE, scale.z() * DOT_SCALE));
		}

		if (state == ConwayRules.BORN)
		{
			Trinket2D.setRenderLevel(2);
			Trinket2D.setDrawColor(new Color(cellData.getMajPlayer().getColor(), 1.0f));
			Trinket2D.drawRectangle(new Vector2f(pos.x(), pos.z()), new Vector2f(scale.x() * DOT_SCALE, scale.z() * DOT_SCALE));
		}
	}
}
