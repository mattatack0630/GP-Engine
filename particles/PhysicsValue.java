package particles;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/27/2016.
 */
public class PhysicsValue
{
	public Vector3f staticVal;
	public Vector3f velocityVal;
	public Vector3f accelerationVal;

	public PhysicsValue(PhysicsValue src)
	{
		this.staticVal = new Vector3f(src.staticVal);
		this.velocityVal = new Vector3f(src.velocityVal);
		this.accelerationVal = new Vector3f(src.accelerationVal);
	}

	public PhysicsValue(Vector3f staticVal)
	{
		this.staticVal = new Vector3f(staticVal);
		this.velocityVal = new Vector3f();
		this.accelerationVal = new Vector3f();
	}

	public PhysicsValue(Vector3f staticVal, Vector3f velocityVal, Vector3f accelerationVal)
	{
		this.staticVal = staticVal;
		this.velocityVal = velocityVal;
		this.accelerationVal = accelerationVal;
	}

	public void update()
	{
		staticVal.add(velocityVal);
		velocityVal.add(accelerationVal);
	}

	public Vector3f getStaticVal()
	{
		return staticVal;
	}

	public void setStaticVal(Vector3f staticVal)
	{
		this.staticVal = staticVal;
	}

	public Vector3f getVelocityVal()
	{
		return velocityVal;
	}

	public void setVelocityVal(Vector3f velocityVal)
	{
		this.velocityVal = velocityVal;
	}

	public Vector3f getAccelerationVal()
	{
		return accelerationVal;
	}

	public void setAccelerationVal(Vector3f accelerationVal)
	{
		this.accelerationVal = accelerationVal;
	}
}
