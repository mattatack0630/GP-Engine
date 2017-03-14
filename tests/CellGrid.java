package tests;

import engine.Engine;
import rendering.DisplayManager;
import utils.math.linear.vector.Vector2f;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 2/13/2017.
 */
public class CellGrid
{
	private int width;
	private int height;
	private GridData gridData;
	private Vector2f cellSize;
	private Vector2f gridPos;
	private Cell[][] cells;

	public CellGrid(int w, int h)
	{
		this.height = h;
		this.width = w;
		this.cells = new Cell[w][h];
		this.gridPos = new Vector2f();
		this.gridData = new GridData();
	}

	public void initGrid()
	{
		this.cellSize = new Vector2f(1.0f / (float) width * DisplayManager.getAspect() * 2, 1.0f / (float) height * 2);

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				cells[i][j] = new Cell();
				Cell c = cells[i][j];

				c.setScale(new Vector3f(cellSize.x(), 1.0f, cellSize.y()));
				c.setPosition(new Vector3f(
						(i * cellSize.x() + gridPos.x() + cellSize.x() / 2.0f) - 1.0f, 0,
						1.0f - (j * cellSize.y() + gridPos.y() + cellSize.y() / 2.0f)));

				Engine.getEntityManager().addEntity(c);
			}
		}
	}

	public GridData getGridData()
	{
		gridData.surveyGrid(this);
		return gridData;
	}

	public void setCellState(int i, int j, boolean state, Player owner, Vector3f color)
	{
		cells[i][j].setVisualColor(color);
		cells[i][j].setOwner(owner);
		cells[i][j].setState(state);
	}

	public void setCellState(Cell c, boolean state, Player owner, Vector3f color)
	{
		c.setVisualColor(color);
		c.setOwner(owner);
		c.setState(state);
	}

	public Cell getCell(int i, int j)
	{
		return cells[i][j];
	}

	public Vector2f getCellSize()
	{
		return cellSize;
	}

	public Vector2f getPosition()
	{
		return gridPos;
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

}
