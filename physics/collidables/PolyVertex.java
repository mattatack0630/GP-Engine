package physics.collidables;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 9/16/2016.
 */
public class PolyVertex
{
	public int id;
	public Vector3f originalPos;
	public Vector3f updatedPos;

	public PolyVertex(Vector3f originalPos)
	{
		this.originalPos = new Vector3f(originalPos);
		this.updatedPos = new Vector3f(originalPos);
		this.id = (int) (Math.random() * 1000000); //Assign random id
	}

	public PolyVertex(PolyVertex src)
	{
		this.originalPos = new Vector3f(src.originalPos);
		this.updatedPos = new Vector3f(src.updatedPos);
		this.id = (int) (Math.random() * 1000000); //Assign random id
	}

	public void updatePos(Vector3f newPos)
	{
		this.updatedPos = new Vector3f(newPos);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o.getClass() != this.getClass())
			return false;

		PolyVertex vertex = (PolyVertex) o;
		return (this.originalPos.equals(vertex.originalPos)) && (this.updatedPos.equals(vertex.updatedPos));
	}

	@Override
	public String toString()
	{
		String string = "Vertex " + this.hashCode() + "  --  Position : " + updatedPos + "\n";
		return string;
	}
}
