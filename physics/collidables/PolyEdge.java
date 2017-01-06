package physics.collidables;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 9/17/2016.
 */
public class PolyEdge
{
	public PolyVertex vertexOne;
	public PolyVertex vertexTwo;
	public Vector3f subLine;

	public PolyEdge(PolyVertex v1, PolyVertex v2)
	{
		this.vertexOne = v1;
		this.vertexTwo = v2;
		this.subLine = new Vector3f();//Vector3f.sub(v1.updatedPos, v2.updatedPos, null);
	}

	public Vector3f calcSubLine()
	{
		Vector3f.sub(vertexOne.updatedPos, vertexTwo.updatedPos, subLine);
		return subLine;
	}

	@Override
	public boolean equals(Object o)
	{
		if(o.getClass() != this.getClass())
			return false;

		PolyEdge edge = (PolyEdge) o;
		return  (vertexOne.equals(edge.vertexOne) || vertexOne.equals(edge.vertexTwo)) &&
				(vertexTwo.equals(edge.vertexTwo) || vertexTwo.equals(edge.vertexOne));
	}

	@Override
	public String toString()
	{
		String string = "\t\tEdge "+this.hashCode()+"\n";
		string+="\t\t\tVertexOne : "+vertexOne+" \t\t\tVertexTwo : "+vertexTwo;
		string+="\t\t\tSub-line : "+subLine+"\n";
		return string;
	}

}
