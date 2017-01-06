package physics;

/**
 * Created by mjmcc on 9/26/2016.
 */
public class PhysicsTester
{
	public static void tick()
	{
		/////////////////////// **PHYSICS TESTING ** //////////////////////////////////////////////
		PhysicsEnvironment environment = new PhysicsEnvironment();
		environment.addForce(PhysicsEnvironment.gravity);
		PhysicsManager.addEnvironment(environment);

		/*Plane p1 = new Plane(new Vector3f(0f, 1f, 0), -1);
		PhysicsObject o1 = new PhysicsObject(p1, new Vector3f(0, 0, 0), new Vector3f(), new Vector3f(), Integer.MAX_VALUE, 1, 1);
		p1.data.tempColor = Color.random();
		environment.addPhysicsObject(o1);

		ArrayList<RenderData> rds = new ArrayList<>();
		for (int i = 1; i < 100; i++)
			for (int j = 1; j < 100; j++)
			{*/
				/*Polygon p2 = ObjLoader.loadPolyObj("defualts/defualtModel");
				p2.scale = new Vector3f(5, 5, 5);
				p2.data.tempColor = CssColor.random();
				PhysicsObject o2 = new PhysicsObject(p2, new Vector3f(10 * i, 10, 10 * j), new Vector3f(-0.1f, 0, 0), new Vector3f(), 1, 100, .91f);
				environment.addPhysicsObject(o2);*/
		//RenderData rd = new RenderData(new Vector3f(i * 10, j * 10, 0));
		//	rd.scale = new Vector3f(10, 10, 10);
		//	rd.updateMatrix();
		//	rds.add(rd);
		//}*/
		//PhysicsManager.tick();
	}

}
