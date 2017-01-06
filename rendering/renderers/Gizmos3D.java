package rendering.renderers;

import models.StaticModel;
import rendering.Color;
import rendering.RenderData;
import rendering.StaticRenderObject;
import resources.ResourceManager;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.AxisAngle;
import utils.math.linear.rotation.Euler;
import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 9/19/2016.
 */
public class Gizmos3D
{
	private static final Vector3f modelNormal = new Vector3f(0.00001f, 1, 0.00001f);
	private static final StaticModel BOX = ResourceManager.getStaticModel("defualtModel").getModel();
	private static final StaticModel SPHERE = ResourceManager.getStaticModel("isoSphereModel").getModel();

	private static MasterRenderer renderer;
	public static Color COLOR;

	public static void init(MasterRenderer _renderer)
	{
		renderer = _renderer;
		COLOR = Color.WHITE;
	}

	public static void drawBox(Vector3f center, Vector3f size)
	{
		RenderData rd = new RenderData(center);
		rd.setScale(size);
		rd.updateMatrix();

		rd.tempColor = new Color(COLOR);
		renderer.processStaticModel(new StaticRenderObject(BOX, rd));
	}

	public static void drawBox(Vector3f center, Vector3f size, Vector3f erot)
	{
		RenderData rd = new RenderData(center);
		rd.setScale(size);
		rd.setRotation(new Euler(erot));
		rd.updateMatrix();

		rd.tempColor = new Color(COLOR);
		renderer.processStaticModel(new StaticRenderObject(BOX, rd));
	}

	public static void drawAABB(Vector3f min, Vector3f max)
	{
		Vector3f sub = Vector3f.sub(max, min, null);
		sub.scale(.5f);
		Vector3f center = Vector3f.add(min, sub, null);

		RenderData rd = new RenderData(center);
		rd.setScale(new Vector3f(Math.abs(sub.x() * 2), Math.abs(sub.y() * 2), Math.abs(sub.z() * 2)));
		rd.updateMatrix();

		rd.tempColor = new Color(COLOR);
		renderer.processStaticModel(new StaticRenderObject(BOX, rd));
	}

	public static void drawWireAABB(Vector3f min, Vector3f max, float width)
	{
		drawLine(new Vector3f(min.x(), min.y(), min.z()), new Vector3f(max.x(), min.y(), min.z()), width);
		drawLine(new Vector3f(min.x(), min.y(), min.z()), new Vector3f(min.x(), max.y(), min.z()), width);
		drawLine(new Vector3f(min.x(), min.y(), min.z()), new Vector3f(min.x(), min.y(), max.z()), width);

		drawLine(new Vector3f(max.x(), max.y(), max.z()), new Vector3f(min.x(), max.y(), max.z()), width);
		drawLine(new Vector3f(max.x(), max.y(), max.z()), new Vector3f(max.x(), min.y(), max.z()), width);
		drawLine(new Vector3f(max.x(), max.y(), max.z()), new Vector3f(max.x(), max.y(), min.z()), width);

		drawLine(new Vector3f(min.x(), min.y(), max.z()), new Vector3f(max.x(), min.y(), max.z()), width);
		drawLine(new Vector3f(max.x(), min.y(), max.z()), new Vector3f(max.x(), min.y(), min.z()), width);
		drawLine(new Vector3f(max.x(), min.y(), min.z()), new Vector3f(max.x(), max.y(), min.z()), width);
		drawLine(new Vector3f(max.x(), max.y(), min.z()), new Vector3f(min.x(), max.y(), min.z()), width);
		drawLine(new Vector3f(min.x(), max.y(), min.z()), new Vector3f(min.x(), max.y(), max.z()), width);
		drawLine(new Vector3f(min.x(), max.y(), max.z()), new Vector3f(min.x(), min.y(), max.z()), width);
	}

	public static void drawSphere(Vector3f pos, float radius)
	{
		RenderData rd = new RenderData(pos);
		rd.setScale(new Vector3f(radius, radius, radius));
		rd.tempColor = COLOR;
		rd.frustumCheck = false;
		rd.updateMatrix();

		renderer.processStaticModel(new StaticRenderObject(SPHERE, rd));
	}

	public static void drawLine(Vector3f p1, Vector3f p2, float width)
	{
		Vector3f origin = new Vector3f(0, 1, 0.0000001f);
		Vector3f sub = Vector3f.sub(p2, p1, null);

		if (sub.length() == 0)
			return;

		Matrix4f translate = new Matrix4f();
		translate.setIdentity();
		Vector3f axis = Vector3f.cross(origin, sub, null);
		axis.normalize();
		float angle = Vector3f.angle(origin, sub);

		translate.translate(Vector3f.add(sub.scale(.5f), p1, null));
		translate.rotate(angle, axis);
		translate.scale(new Vector3f(width, sub.length() * 2, width));

		RenderData rd = new RenderData(translate);
		rd.tempColor = new Color(COLOR);
		renderer.processStaticModel(new StaticRenderObject(BOX, rd));
	}

	public static void drawPlane(Vector3f normal, float height, float extension)
	{
		RenderData rd = new RenderData(new Vector3f(0, height, 0));
		rd.setRotation(new AxisAngle(Vector3f.cross(normal, modelNormal, null), -Vector3f.angle(normal, modelNormal)));
		rd.setScale(new Vector3f(extension, 0.15f, extension));
		rd.updateMatrix();
		rd.tempColor = COLOR;
		renderer.processStaticModel(new StaticRenderObject(BOX, rd));
	}

	public static void setGizmosColor(Color c)
	{
		COLOR = c;
	}

	public static void renderAxis()
	{
		RenderData xdata = new RenderData(new Vector3f());
		xdata.setScale(new Vector3f(.15f, 1000, .15f));
		xdata.setRotation(new Euler(0, 0, (float) Math.PI / 2));
		xdata.tempColor = Color.RED;
		xdata.updateMatrix();
		xdata.frustumCheck = false;
		renderer.processStaticModel(new StaticRenderObject(BOX, xdata));

		RenderData ydata = new RenderData(new Vector3f());
		ydata.setScale(new Vector3f(.15f, 1000, .15f));
		ydata.setRotation(new Euler(0, 0, 0));
		ydata.tempColor = Color.GREEN;
		ydata.updateMatrix();
		ydata.frustumCheck = false;
		renderer.processStaticModel(new StaticRenderObject(BOX, ydata));

		RenderData zdata = new RenderData(new Vector3f());
		zdata.setScale(new Vector3f(.15f, 1000, .15f));
		zdata.setRotation(new Euler((float) Math.PI / 2f, 0f, 0f));
		zdata.tempColor = Color.BLUE;
		zdata.updateMatrix();
		zdata.frustumCheck = false;
		renderer.processStaticModel(new StaticRenderObject(BOX, zdata));
	}
}
