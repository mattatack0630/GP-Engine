package parsing;

import parsing.lime.LimeData;
import utils.math.geom.Polygon;
import utils.math.geom.PolygonEdge;
import utils.math.geom.PolygonFace;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 12/20/2016.
 */
public class PolygonBuilder
{
	public static Polygon buildPoly(LimeData data)
	{
		List<PolygonFace> polyFaces = new ArrayList<>();
		List<Vector3f> faces = data.getFaces();
		List<Vector3f> vertices = data.getVertices();

		for (Vector3f face : faces)
		{
			Vector3f v0 = vertices.get((int) face.x());
			Vector3f v1 = vertices.get((int) face.y());
			Vector3f v2 = vertices.get((int) face.z());

			List<PolygonEdge> edges = new ArrayList<>();
			edges.add(new PolygonEdge(new Vector3f(v0), new Vector3f(v1)));
			edges.add(new PolygonEdge(new Vector3f(v1), new Vector3f(v2)));
			edges.add(new PolygonEdge(new Vector3f(v2), new Vector3f(v0)));
			polyFaces.add(new PolygonFace(edges));
		}

		return new Polygon(polyFaces);
	}
}
