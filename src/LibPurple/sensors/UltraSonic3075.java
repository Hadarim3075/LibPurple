package LibPurple.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltraSonic3075 {
	
	private AnalogInput analog;
	
	private static double[] Values = new double[3];
	
	/**
	 * UltraSonic module for MaxBotix LV-MaxSonar-EZ
	 * @param analogPin the analog pin in the roboRIO the sensor is connected to.
	 */
	public UltraSonic3075(int analogPin)
	{
		analog = new AnalogInput(analogPin);
	}
	
	/**
	 * 
	 * @return The distance of the closest object to the sensor in millimeter
	 */
	public int getDistance()
	{
		int volt = analog.getValue();
    	int mm = volt * 5;
    	return mm;
	}

}
