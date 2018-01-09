package LibPurple.actuator;

import LibPurple.utils.Utils;
import edu.wpi.first.wpilibj.Victor;
/*
 *  						*** 3075 ***
 * This class is an improvement to the regular victor including:
 * * Slave
 * * MotorBound
 * * Invert
 */
public class Victor3075 extends Victor implements SpeedController3075{
	
	private double inverted = 1;
	private double motorBound = 0.2;
	private Victor3075 slave; //This is the slave of the victor.
	
	/*
	 * This constructor does not do anything.
	 */
	public Victor3075(int channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Sets the value of the motor.
	 */
	public void set(double speed)
	{
		// TODO Auto-generated method stub
    	double finalSpeed = Utils.motorBound(speed, motorBound) * inverted;
    	super.set(finalSpeed);
    	if(slave != null)
    		slave.set(speed);
	}

	@Override
	public void setInverted(boolean isInverted) {
		// TODO Auto-generated method stub
		this.inverted = isInverted ? -1 : 1;
	}

	
	public void setMotorBound(double newMotorBound) {
		// TODO Auto-generated method stub
		motorBound = newMotorBound;
	}

	
	public void setSlave(SpeedController3075 slave) {
		this.slave = (Victor3075) slave;
	}
	
	public void setSlave(SpeedController3075 slave, boolean inverted)
	{
		this.slave = (Victor3075) slave;
		this.slave.setInverted(inverted);
	}
	
	public void setSlaveInverted(boolean inverted)
	{
		this.slave.setInverted(inverted);
	}
	
	
    
   
	
	
	
}
