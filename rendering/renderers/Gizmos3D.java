package rendering.renderers;

import engine.Engine;
import models.StaticModel;
import rendering.Color;
import rendering.RenderData;
import rendering.StaticRenderObject;
import resources.StaticModelResource;
import utils.math.linear.matrix.Matrix4f;
import utils.math.linear.rotation.AxisAngle;
import utils.math.linear.rotation.Euler;
import utils.math.linear.rotation.Rotation;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 9/19/2016.
 */
public class Gizmos3D
{
	private static final Vector3f MODEL_NORMAL = new Vector3f(0.00001f, 1, 0.00001f);
	private static final int MAX_RENDERABLES = 5000;

	private static MasterRenderer renderer;

	private static List<StaticRenderObject> renderObjCache;
	private static int objRenderedCount;

	public static Color COLOR;

	private static StaticModel SPHERE;
	private static StaticModel CUBE;
	private static StaticModel QUAD;

	public static void init(MasterRenderer _renderer)
	{
		renderObjCache = new ArrayList<>(MAX_RENDERABLES);
		objRenderedCount = 0;

		renderer = _renderer;
		COLOR = Color.WHITE;
		QUAD = ((StaticModelResource) Engine.getResourceManager().getResource("quadModel")).getModel();
		CUBE = ((StaticModelResource) Engine.getResourceManager().getResource("defualtModel")).getModel();
		SPHERE = ((StaticModelResource) Engine.getResourceManager().getResource("isoSphereModel")).getModel();

		// Init cache
		for (int i = 0; i < MAX_RENDERABLES; i++)
			renderObjCache.add(new StaticRenderObject(new RenderData(new Vector3f())));
	}

	public static void drawBox(Vector3f center, Vector3f size)
	{
		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		rd.setPosition(center);
		rd.setScale(size);
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(CUBE);
		renderer.processStaticModel(ro);
	}

	public static void drawBox(Vector3f center, Vector3f size, Rotation erot)
	{
		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		rd.setPosition(center);
		rd.setRotation(erot);
		rd.setScale(size);
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(CUBE);
		renderer.processStaticModel(ro);
	}

	public static void drawAABB(Vector3f min, Vector3f max)
	{
		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		Vector3f sub = Vector3f.sub(max, min, null).scale(0.5f);
		rd.setPosition(Vector3f.add(min, sub, null));
		rd.setScale(new Vector3f(Math.abs(sub.x() * 2), Math.abs(sub.y() * 2), Math.abs(sub.z() * 2)));
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(CUBE);
		renderer.processStaticModel(ro);
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
		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		rd.setPosition(pos);
		rd.setScale(new Vector3f(radius, radius, radius));
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(SPHERE);
		renderer.processStaticModel(ro);
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

		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		rd.setScale(new Vector3f(width, sub.length() * 2, width));
		rd.setPosition(Vector3f.add(sub.scale(.5f), p1, null));
		rd.setRotation(new AxisAngle(axis, angle));
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(CUBE);
		renderer.processStaticModel(ro);
	}

	public static void drawPlane(Vector3f normal, float height, float extension)
	{
		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		rd.setPosition(new Vector3f(0, height, 0));
		rd.setScale(new Vector3f(extension, 0.15f, extension));
		rd.setRotation(new AxisAngle(Vector3f.cross(normal, MODEL_NORMAL, null), -Vector3f.angle(normal, MODEL_NORMAL)));
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(CUBE);
		renderer.processStaticModel(ro);
	}

	public static void drawQuad(Vector3f pos, Rotation rotation, Vector3f scale)
	{
		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		rd.setRotation(rotation);
		rd.setPosition(pos);
		rd.setScale(scale);
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(QUAD);
		renderer.processStaticModel(ro);
	}

	public static void drawModel(StaticModel model, Vector3f pos)
	{
		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		rd.setScale(new Vector3f(1));
		rd.setRotation(new Euler());
		rd.setPosition(pos);
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(model);
		renderer.processStaticModel(ro);
	}

	public static void drawModel(StaticModel model, Vector3f pos, Rotation rot, Vector3f scale)
	{
		StaticRenderObject ro = renderObjCache.get(objRenderedCount++);

		RenderData rd = ro.getRenderData();
		rd.setPosition(pos);
		rd.setRotation(rot);
		rd.setScale(scale);
		rd.updateMatrix();
		rd.frustumCheck = false;
		rd.tempColor = new Color(COLOR);

		ro.setStaticModel(model);
		renderer.processStaticModel(ro);
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
		renderer.processStaticModel(new StaticRenderObject(CUBE, xdata));

		RenderData ydata = new RenderData(new Vector3f());
		ydata.setScale(new Vector3f(.15f, 1000, .15f));
		ydata.setRotation(new Euler(0, 0, 0));
		ydata.tempColor = Color.GREEN;
		ydata.updateMatrix();
		ydata.frustumCheck = false;
		renderer.processStaticModel(new StaticRenderObject(CUBE, ydata));

		RenderData zdata = new RenderData(new Vector3f());
		zdata.setScale(new Vector3f(.15f, 1000, .15f));
		zdata.setRotation(new Euler((float) Math.PI / 2f, 0f, 0f));
		zdata.tempColor = Color.BLUE;
		zdata.updateMatrix();
		zdata.frustumCheck = false;
		renderer.processStaticModel(new StaticRenderObject(CUBE, zdata));
	}

	public static void releaseRenderedObjs()
	{

		objRenderedCount = 0;
	}
}
