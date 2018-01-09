package LibPurple.actuator;

import edu.wpi.first.wpilibj.SpeedController;
/**
 *  						*** 3075 ***
 * This class is an improvment to the regular speed controller including:
 * * Slave
 * * MotorBound
 * * Invert
 */
public interface SpeedController3075 extends SpeedController
{
	public void setInverted(boolean isInverted);
	
	public void setMotorBound(double newMotorBound);
	
	public void setSlave(SpeedController3075 slave);

}