package utils.math.geom;

/**
 * Created by mjmcc on 1/20/2017.
 */
public class IntersectParams
{
	public static final IntersectParams CALC_ALL = new IntersectParams(true, true, true);
	public static final IntersectParams CALC_INTERSECT_ONLY = new IntersectParams(true, false, false);

	public boolean calculateIsIntersecting;
	public boolean calculateIntersectPoint;
	public boolean calculateIntersectNormal;

	public IntersectParams(boolean isColliding, boolean cp, boolean cn)
	{
		this.calculateIsIntersecting = isColliding;
		this.calculateIntersectPoint = cp;
		this.calculateIntersectNormal = cn;
	}

	public IntersectParams calcIsIntersecting(boolean b)
	{
		this.calculateIsIntersecting = b;
		return this;
	}

	public IntersectParams calcIntersectPoint(boolean b)
	{
		this.calculateIntersectPoint = b;
		return this;
	}

	public IntersectParams calcIntersectNorm(boolean b)
	{
		this.calculateIntersectNormal = b;
		return this;
	}

	public boolean isCalculateIsIntersecting()
	{
		return calculateIsIntersecting;
	}

	public boolean isCalculateIntersectPoint()
	{
		return calculateIntersectPoint;
	}

	public boolean isCalculateIntersectNormal()
	{
		return calculateIntersectNormal;
	}
}
