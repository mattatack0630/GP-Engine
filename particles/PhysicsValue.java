package particles;

import utils.math.linear.vector.Vector3f;

/**
 * Created by mjmcc on 12/27/2016.
 *
 * The Physics value is used to simulate physics, using acceleration, velocity, and a static value.
 */
public class PhysicsValue
{
	public Vector3f staticVal;
	public Vector3f velocityVal;
	public Vector3f accelerationVal;

	public PhysicsValue(PhysicsValue src)
	{
		this.staticVal = new Vector3f();
		this.velocityVal = new Vector3f();
		this.accelerationVal = new Vector3f();
		setStaticVal(src.staticVal);
		setVelocityVal(src.velocityVal);
		setAccelerationVal(src.accelerationVal);
	}

	public PhysicsValue(Vector3f staticVal)
	{
		this.staticVal = new Vector3f();
		this.velocityVal = new Vector3f();
		this.accelerationVal = new Vector3f();
		setStaticVal(staticVal);
	}

	public PhysicsValue(Vector3f staticVal, Vector3f velocityVal, Vector3f accelerationVal)
	{
		this.staticVal = new Vector3f();
		this.velocityVal = new Vector3f();
		this.accelerationVal = new Vector3f();
		setStaticVal(staticVal);
		setVelocityVal(velocityVal);
		setAccelerationVal(accelerationVal);
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
		this.staticVal.setX(staticVal.x());
		this.staticVal.setY(staticVal.y());
		this.staticVal.setZ(staticVal.z());
	}

	public Vector3f getVelocityVal()
	{
		return velocityVal;
	}

	public void setVelocityVal(Vector3f velocityVal)
	{
		this.velocityVal.setX(velocityVal.x());
		this.velocityVal.setY(velocityVal.y());
		this.velocityVal.setZ(velocityVal.z());
	}

	public Vector3f getAccelerationVal()
	{
		return accelerationVal;
	}

	public void setAccelerationVal(Vector3f accelerationVal)
	{
		this.accelerationVal.setX(accelerationVal.x());
		this.accelerationVal.setY(accelerationVal.y());
		this.accelerationVal.setZ(accelerationVal.z());
	}
}
