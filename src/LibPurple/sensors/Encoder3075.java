package LibPurple.sensors;

import edu.wpi.first.wpilibj.PIDSource;

public interface Encoder3075 extends PIDSource
{

	/** 
	 * 
	 * @param reverseDirection Reverse the direction if true
	 */
	public double getRate();
	
	public double getDistance();
	
	public void setReverseDirection(boolean reverseDirection);
	
	public void setDistancePerPulse(double dpp);
	
	public void reset();
	
}
